package progmatic.hegymaszas.dto;

import progmatic.hegymaszas.modell.Rating;

public class RatingShowDto {
    private long ratingId;
    private int ratingByBeauty;
    private int ratingByDifficulty;
    private int ratingBySafety;
    private long routeId;


    public RatingShowDto() {
    }


    public RatingShowDto(Rating rating) {
        ratingId = rating.getId();
        ratingByBeauty = rating.getRatingByBeauty();
        ratingByDifficulty = rating.getRatingByDifficulty();
        ratingBySafety = rating.getRatingBySafety();
        routeId = rating.getRoute().getId();
    }


    public long getRatingId() {
        return ratingId;
    }


    public void setRatingId(long ratingId) {
        this.ratingId = ratingId;
    }


    public int getRatingByBeauty() {
        return ratingByBeauty;
    }


    public void setRatingByBeauty(int ratingByBeauty) {
        this.ratingByBeauty = ratingByBeauty;
    }


    public int getRatingByDifficulty() {
        return ratingByDifficulty;
    }


    public void setRatingByDifficulty(int ratingByDifficulty) {
        this.ratingByDifficulty = ratingByDifficulty;
    }


    public int getRatingBySafety() {
        return ratingBySafety;
    }


    public void setRatingBySafety(int ratingBySafety) {
        this.ratingBySafety = ratingBySafety;
    }


    public long getRouteId() {
        return routeId;
    }


    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }
}
