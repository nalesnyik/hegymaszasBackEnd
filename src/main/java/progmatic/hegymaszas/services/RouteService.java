package progmatic.hegymaszas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import progmatic.hegymaszas.dto.RouteDto;
import progmatic.hegymaszas.modell.ClimbingPlace_;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.modell.Route_;
import progmatic.hegymaszas.modell.Sector_;
import progmatic.hegymaszas.repositories.RouteRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    @PersistenceContext
    EntityManager em;

    private final RouteRepository routeRepository;
    private RatingService ratingService;

    @Autowired
    public RouteService(RouteRepository routeRepository, RatingService ratingService) {
        this.routeRepository = routeRepository;
        this.ratingService = ratingService;
    }

    @Transactional
    public List<Route> getAllRoute() {
        return em.createQuery("SELECT r from Route r", Route.class).getResultList();
        //return routeRepository.findAll();

    }

    public List<Route> loadFilteredRoutes(
            String grade, String name, int averageRating,
            int beautyRating, int difficultyRating, int safetyRating, int height, String climbingPlaceName) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Route> cQuery = cb.createQuery(Route.class);
        Root<Route> routes = cQuery.from(Route.class);
        List<Predicate> predicateList = new ArrayList<>();

        if (!StringUtils.isEmpty(name)) {
            String text = "%" + name + "%";
            predicateList.add(cb.like(routes.get(Route_.name), text));
        }
        if (!StringUtils.isEmpty(height)) {
            predicateList.add(cb.equal(routes.get(Route_.height), height));
        }
       /* if (!StringUtils.isEmpty(averageRating)) {
            predicateList.add(cb.greaterThanOrEqualTo(routes.get(ratingService.getAverageRatingOfOneRoute), averageRating));
        }
        if (!StringUtils.isEmpty(averageRating)) {
            predicateList.add(cb.greaterThanOrEqualTo(routes.get(ratingService.getAverageBeautyRating), beautyRating));
        }
        if (!StringUtils.isEmpty(averageRating)) {
            predicateList.add(cb.greaterThanOrEqualTo(routes.get(ratingService.getAverageDifficultyRating), difficultyRating));
        }
        if (!StringUtils.isEmpty(averageRating)) {
            predicateList.add(cb.greaterThanOrEqualTo(routes.get(ratingService.getAverageSafetyRating), safetyRating));
        }*/
        if (!StringUtils.isEmpty(grade)) {
            int gradeValue = Integer.parseInt(String.valueOf(grade.charAt(0)));
            predicateList.add(cb.greaterThanOrEqualTo((routes.get(Route_.grade)), gradeValue));
        }
        if (!StringUtils.isEmpty(climbingPlaceName)) {
            String chosenPlaceName = "%" + climbingPlaceName + "%";
            predicateList.add(cb.like(routes.get(Route_.sector).get(Sector_.climbingPlace).get(ClimbingPlace_.name), chosenPlaceName));
        }
        cQuery.where(predicateList.toArray(new Predicate[predicateList.size()]));
        return em.createQuery(cQuery).getResultList();
    }
}
