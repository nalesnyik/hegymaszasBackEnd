package progmatic.hegymaszas.dto;

public class RatingModifyDto extends RatingDto {
    private long ratingId;


    public RatingModifyDto() {
    }


    public long getRatingId() {
        return ratingId;
    }


    public void setRatingId(long ratingId) {
        this.ratingId = ratingId;
    }
}
