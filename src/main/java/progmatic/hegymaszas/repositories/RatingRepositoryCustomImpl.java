package progmatic.hegymaszas.repositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class RatingRepositoryCustomImpl implements RatingRepositoryCustom {

    @PersistenceContext
    EntityManager em;


    public boolean areUsersMatchForModifyingRating(String username, long ratingId) {
        return 1 == em.createQuery("SELECT count(r) FROM Rating r WHERE r.id=:id AND r.user.name=:name", Long.class)
                .setParameter("id", ratingId)
                .setParameter("name", username)
                .getSingleResult();
    }


    public boolean existsRatingForRouteByUser(String username, long routeId) {
        return 0 < em.createQuery("SELECT count(r) FROM Route r JOIN r.ratings rat WHERE r.id=:id AND rat.user.name=:name", Long.class)
                .setParameter("id", routeId)
                .setParameter("name", username)
                .getSingleResult();
    }


    public double getAverageBeautyRating(long routeId) {
        return em.createNamedQuery("getAverageBeautyRating", Double.class)
                .setParameter("id", routeId)
                .getSingleResult();
    }


    public double getAverageDifficultyRating(long routeId) {
        return em.createNamedQuery("getAverageDifficultyRating", Double.class)
                .setParameter("id", routeId)
                .getSingleResult();
    }


    public double getAverageSafetyRating(long routeId) {
        return em.createNamedQuery("getAverageSafetyRating", Double.class)
                .setParameter("id", routeId)
                .getSingleResult();
    }
}
