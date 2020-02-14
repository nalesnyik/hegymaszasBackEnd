package progmatic.hegymaszas.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progmatic.hegymaszas.dto.RatingCreateDto;
import progmatic.hegymaszas.dto.RatingDto;
import progmatic.hegymaszas.dto.RatingShowDto;
import progmatic.hegymaszas.exceptions.NotAppropriateNumberOfStarsForRatingException;
import progmatic.hegymaszas.exceptions.RatingNotFoundException;
import progmatic.hegymaszas.exceptions.RouteNotFoundException;
import progmatic.hegymaszas.exceptions.RouteRatingByUserExistsException;
import progmatic.hegymaszas.modell.MyUser;
import progmatic.hegymaszas.modell.Rating;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.repositories.RatingRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class RatingService {

    private static final Logger logger = LoggerFactory.getLogger(RatingService.class);
    @PersistenceContext
    EntityManager em;
    RatingRepository ratingRepository;


    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
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
    public RatingShowDto addRating(RatingCreateDto dto) throws RouteNotFoundException, NotAppropriateNumberOfStarsForRatingException, RouteRatingByUserExistsException {
        checkRatingCreateDto(dto, 1, 10);
        Route route = em.find(Route.class, dto.getRouteId());
        if (route == null) throw new RouteNotFoundException();
        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ratingRepository.existsRatingForRouteByUser(user.getName(), route.getId())) {
            throw new RouteRatingByUserExistsException();
        }
        Rating rating = new Rating(dto);
        rating.setUser(user);
        rating.setRoute(route);

        em.persist(rating);

        setAvgRatingsOfRoute(rating.getRoute());

        return new RatingShowDto(rating);
    }


    @Transactional
    public RatingShowDto modifyRating(RatingCreateDto dto) throws RatingNotFoundException, NotAppropriateNumberOfStarsForRatingException {
        Rating rating = getRating(dto);
        rating.setRatingByBeauty(dto.getRatingByBeauty());
        rating.setRatingByDifficulty(dto.getRatingByDifficulty());
        rating.setRatingBySafety(dto.getRatingBySafety());

        setAvgRatingsOfRoute(rating.getRoute());

        return new RatingShowDto(rating);
    }


    private Rating getRating(RatingCreateDto dto) throws NotAppropriateNumberOfStarsForRatingException, RatingNotFoundException {
        checkRatingCreateDto(dto, 1, 10);

        MyUser user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Rating rating = ratingRepository.findByRouteIdAndUserName(dto.getRouteId(), user.getName());

        checkRatingBeforeModify(rating);
        return rating;
    }


    private void setAvgRatingsOfRoute(Route route) {
        route.setAvgRatingByBeauty(ratingRepository.getAverageBeautyRating(route.getId()));
        route.setAvgRatingByDifficulty(ratingRepository.getAverageDifficultyRating(route.getId()));
        route.setAvgRatingBySafety(ratingRepository.getAverageSafetyRating(route.getId()));
    }


    private void checkRatingBeforeModify(Rating rating) throws RatingNotFoundException {
        if (rating == null) {
            throw new RatingNotFoundException();
        }
    }


    public static void checkRatingOfCreateDto(int rating, int min, int max) throws NotAppropriateNumberOfStarsForRatingException {
        if (rating > max || rating < min) throw new NotAppropriateNumberOfStarsForRatingException();
    }


    private void checkRatingCreateDto(RatingDto dto, int min, int max) throws NotAppropriateNumberOfStarsForRatingException {
        checkRatingOfCreateDto(dto.getRatingByBeauty(), min, max);
        checkRatingOfCreateDto(dto.getRatingByDifficulty(), min, max);
        checkRatingOfCreateDto(dto.getRatingBySafety(), min, max);
    }


}
