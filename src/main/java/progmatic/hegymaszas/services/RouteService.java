package progmatic.hegymaszas.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import progmatic.hegymaszas.modell.*;
import progmatic.hegymaszas.modell.enums.Orientation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public List<Route> getAllRoute() {
        return em.createQuery("SELECT r from Route r", Route.class).getResultList();
        //return routeRepository.findAll();
    }

    public List<Route> loadFilteredRoutes(
            String grade, String name, String climbingPlaceName,
            double rating, Orientation orientation) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Route> cQuery = cb.createQuery(Route.class);
        Root<Route> routes = cQuery.from(Route.class);
       // ListJoin<Route, Rating> joinedRatings = routes.join(Route_.ratings);
        List<Predicate> predicateList = new ArrayList<>();

        if (!StringUtils.isEmpty(name)) {
            String text = "%" + name + "%";
            predicateList.add(cb.like(routes.get(Route_.name), text));
        }
       // if (!StringUtils.isEmpty(height)) {
       //     predicateList.add(cb.equal(routes.get(Route_.height), height));
       // }
        if (!StringUtils.isEmpty(rating)) {
            predicateList.add(cb.greaterThanOrEqualTo(routes.get(Route_.avgRating), (double) rating));
            // predicateList.add(cb.greaterThanOrEqualTo(cb.avg(joinedRatings.get(Rating_.ratingByBeauty)), (double) beautyRating));
        }
        if (!StringUtils.isEmpty(grade)) {
            int gradeValue = Integer.parseInt(String.valueOf(grade.charAt(0)));
            predicateList.add(cb.greaterThanOrEqualTo((routes.get(Route_.grade)), gradeValue));
        }
        if (!StringUtils.isEmpty(climbingPlaceName)) {
            String chosenPlaceName = "%" + climbingPlaceName + "%";
            predicateList.add(cb.like(routes.get(Route_.sector).get(Sector_.climbingPlace).get(ClimbingPlace_.name), chosenPlaceName));
        }
        // cQuery.groupBy(routes.get(Route_.id));
        cQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
       List<Route> listWithoutOrientation = em.createQuery(cQuery).getResultList();
        if (!StringUtils.isEmpty(orientation)) {
            List<Route> listWithOrientation=new ArrayList<>();
            for (Route route : listWithoutOrientation) {
                if(route.getOrientation()==orientation){
                    listWithOrientation.add(route);
                }
            }
            return listWithOrientation;
        }
        return listWithoutOrientation;
    }

}
