package progmatic.hegymaszas.modell.messages;

import progmatic.hegymaszas.modell.Route;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
public class Feedback extends Message {

    @NotNull
    @ManyToOne
    private Route route;

    @Min(1)
    @Max(5)
    private int rating;


    public Feedback() {
    }


    public Route getRoute() {
        return route;
    }


    public void setRoute(Route route) {
        this.route = route;
    }


    public int getRating() {
        return rating;
    }


    public void setRating(int rating) {
        this.rating = rating;
    }
}
