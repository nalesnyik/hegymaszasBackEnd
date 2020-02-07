package progmatic.hegymaszas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progmatic.hegymaszas.dto.MyUserDto;
import progmatic.hegymaszas.modell.MyUser;
import progmatic.hegymaszas.modell.Rating;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class RatingService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    public RatingService() {
    }

    @Transactional
    public List<Rating> getAllRatings() {
        return em.createQuery("select r from Rating r", Rating.class).getResultList();
    }

    @Transactional
    public Double getAvarageBeautyRatings() {
        return em.createQuery("select avg (r.ratingByBeauty) from Rating r where r.ratingByBeauty>0", Double.class).getSingleResult();
    }

    @Transactional
    public Double getAvarageDifficultyRating() {
        return em.createQuery("select avg (r.ratingByDifficulty) from Rating r where r.ratingByDifficulty>0",
                Double.class).getSingleResult();
    }

    @Transactional
    public Double getAvarageSafetyRating() {
        return em.createQuery("select avg (r.ratingBySafety) from Rating r where r.ratingBySafety>0",
                Double.class).getSingleResult();
    }

    @Transactional
    public Rating addRating(Rating rating) {
        MyUser myUser = new MyUser();
        MyUserDto userDto = new MyUserDto();
        Rating newRating = new Rating();
        myUser.setName(userDto.getName());
        newRating.setRatingByBeauty(rating.getRatingByBeauty());
        newRating.setRatingByDifficulty(rating.getRatingByDifficulty());
        newRating.setRatingBySafety(rating.getRatingBySafety());
        em.persist(newRating);
        return newRating;
    }


}
