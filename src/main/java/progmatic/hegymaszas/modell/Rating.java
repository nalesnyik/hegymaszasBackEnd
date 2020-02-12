package progmatic.hegymaszas.modell;

import progmatic.hegymaszas.dto.RatingCreateDto;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Min(1)
    @Max(10)
    private int ratingByBeauty;

    @Min(1)
    @Max(10)
    private int ratingByDifficulty;

    @Min(1)
    @Max(10)
    private int ratingBySafety;

    @ManyToOne
    private Route route;

    @ManyToOne
    private MyUser user;


    public Rating() {
    }


    public Rating(RatingCreateDto dto) {
        ratingByBeauty = dto.getRatingByBeauty();
        ratingByDifficulty = dto.getRatingByDifficulty();
        ratingBySafety = dto.getRatingBySafety();
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public void setRoute(Route route) {
        this.route = route;
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


    public Route getRoute() {
        return route;
    }


    public MyUser getUser() {
        return user;
    }


    public void setUser(MyUser user) {
        this.user = user;
    }
}
