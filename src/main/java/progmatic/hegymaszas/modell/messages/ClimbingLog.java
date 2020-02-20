package progmatic.hegymaszas.modell.messages;

import org.springframework.security.core.context.SecurityContextHolder;
import progmatic.hegymaszas.dto.ClimbingLogCreateDto;
import progmatic.hegymaszas.exceptions.WrongAscentTypeException;
import progmatic.hegymaszas.modell.MyUser;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.modell.enums.AscentType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;

@Entity
public class ClimbingLog extends Message {
    @NotNull
    @ManyToOne
    private Route route;
    private LocalDate dateOfClimb;
    private AscentType type;


    public ClimbingLog() {
    }


    public ClimbingLog(ClimbingLogCreateDto log, Route route) throws WrongAscentTypeException {
        this.type = AscentType.getAscentType(log.getType());
        this.user = (MyUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        this.route = route;
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

    public LocalDate getDateOfClimb() {
        return dateOfClimb;
    }

    public void setDateOfClimb(LocalDate dateOfClimb) {
        this.dateOfClimb = dateOfClimb;
    }
}
