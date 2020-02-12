package progmatic.hegymaszas.dto;

public class RatingDto {

    private int ratingByBeauty;
    private int ratingByDifficulty;
    private int ratingBySafety;


    public RatingDto() {
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
}
