package progmatic.hegymaszas.modell;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 1, max = 10)
    private int ratingByBeauty;

    @Size(min = 1, max = 10)
    private int ratingByDifficulty;

    @Size(min = 1, max = 10)
    private int ratingBySafety;

    @ManyToOne
    private Route route;

    public Rating() {
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
}
