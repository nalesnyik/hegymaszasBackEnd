package progmatic.hegymaszas.modell.messages;

import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.modell.enums.AscentType;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class ClimbingLog extends Message {
    @NotNull
    @ManyToOne
    private Route route;

    private AscentType type;


    public ClimbingLog() {
    }


    public Route getRoute() {
        return route;
    }


    public void setRoute(Route route) {
        this.route = route;
    }


    public AscentType getType() {
        return type;
    }


    public void setType(AscentType type) {
        this.type = type;
    }
}
