package progmatic.hegymaszas.repositories;

import progmatic.hegymaszas.modell.Route;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RouteRepositoryCustomImpl implements RouteRepositoryCustom {
    @PersistenceContext
    EntityManager em;


    public Route routeWithEverything(long routeId) {
        return em.createQuery("SELECT r FROM Route r JOIN FETCH r.feedbacks JOIN FETCH r.climbingLogs", Route.class)
                .getSingleResult();
    }


    public List<Long> idOfMiniImagesOfRoute(long routeId) {
        return em.createQuery("SELECT i.originalImgId FROM Route r JOIN  r.images i WHERE i.originalImgId>0", Long.class).getResultList();
    }
}
