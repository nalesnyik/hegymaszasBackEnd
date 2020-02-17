package progmatic.hegymaszas.repositories;

import progmatic.hegymaszas.modell.Route;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RouteRepositoryCustomImpl implements RouteRepositoryCustom {
    @PersistenceContext
    EntityManager em;


    public Route routeWithEverything(long routeId) {
        return em.createQuery("SELECT r FROM Route r LEFT JOIN FETCH r.feedbacks LEFT JOIN FETCH r.climbingLogs WHERE r.id=:id", Route.class)
                .setParameter("id", routeId)
                .getSingleResult();
    }


    public List<Long> idOfMiniImagesOfRoute(long routeId) {
        return em.createQuery("SELECT i.id FROM Route r JOIN r.images i WHERE r.id=:id AND i.originalImgId>0 ORDER BY i.originalImgId DESC ", Long.class)
                .setParameter("id", routeId)
                .getResultList();
    }


    public List<Long> get9idOfMiniImagesOfRoute(long routeId) {
        List<Long> idList;
        idList = em.createQuery("SELECT i.id FROM Route r LEFT JOIN r.images i WHERE r.id=:id AND i.originalImgId>0 ORDER BY i.originalImgId DESC", Long.class)
                .setMaxResults(9)
                .setParameter("id", routeId)
                .getResultList();
        return idList;
    }

}
