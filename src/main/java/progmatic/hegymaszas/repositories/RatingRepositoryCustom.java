package progmatic.hegymaszas.repositories;

public interface RatingRepositoryCustom {
    boolean areUsersMatchForModifyingRating(String username, long id);

    boolean existsRatingForRouteByUser(String username, long routeId);

    double getAverageBeautyRating(long routeId);

    double getAverageDifficultyRating(long routeId);

    double getAverageSafetyRating(long routeId);
}
