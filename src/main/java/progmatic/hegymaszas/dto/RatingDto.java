package progmatic.hegymaszas.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

public class RatingDto {


    private long id;

    @Size(min = 1, max = 10)
    private int ratingByBeauty;

    @Size(min = 1, max = 10)
    private int ratingByDifficulty;

    @Size(min = 1, max = 10)
    private int ratingBySafety;

    public RatingDto(long id, @Size(min = 1, max = 10) int ratingByBeauty, @Size(min = 1, max = 10)
            int ratingByDifficulty, @Size(min = 1, max = 10) int ratingBySafety) {
        this.id = id;
        this.ratingByBeauty = ratingByBeauty;
        this.ratingByDifficulty = ratingByDifficulty;
        this.ratingBySafety = ratingBySafety;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
