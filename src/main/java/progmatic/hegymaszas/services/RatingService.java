package progmatic.hegymaszas.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progmatic.hegymaszas.dto.RatingDto;
import progmatic.hegymaszas.modell.Rating;
import progmatic.hegymaszas.modell.Route;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class RatingService {

    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);
    @PersistenceContext
    EntityManager em;

    @Autowired
    public RatingService() {
    }

    @Transactional
    public Double getOneRating(long id) {
        return em.createQuery("select r from Rating r", Double.class)
                .setParameter("id", id)
                .getSingleResult();
        //egy adott útra
    }

    @Transactional
    public Double getAverageBeautyRatings(long id) {
          return em.createQuery("select avg (r.ratingByBeauty) from Rating r where r.route.id =: id " +
                    " and r.ratingByBeauty > 0", Double.class)
                  .setParameter("id", id)
                  .getSingleResult();
    }

    @Transactional
    public Double getAverageDifficultyRating(long id) {
        return em.createQuery("select avg (r.ratingByDifficulty) from Rating r where r.route.id =:id " +
                            "and r.ratingByDifficulty > 0", Double.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Transactional
    public Double getAverageSafetyRating(long id) {
        return em.createQuery("select avg (r.ratingBySafety) from Rating r where r.route.id = :id " +
                "and r.ratingBySafety > 0", Double.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public Double getAvarageRatingOfOneRoute(long id) {
        Rating rating = em.find(Rating.class, id);
        Double avgRating = 0.0;
        if (rating.getId() == id) {
            //avgRating = (getAverageDifficultyRating(rating) + getAverageBeautyRatings(rating) + getAverageSafetyRating(rating)) / 3;
        }
        return avgRating;
    }

    @Transactional
    public void addRating(RatingDto ratingDto) {
        //Route route = new Route();
        Rating newRating = new Rating();
        //route.setName(rating.getRoute().getName());
        newRating.setRatingByBeauty(ratingDto.getRatingByBeauty());
        newRating.setRatingByDifficulty(ratingDto.getRatingByDifficulty());
        newRating.setRatingBySafety(ratingDto.getRatingBySafety());
        em.persist(newRating);
    }
}
