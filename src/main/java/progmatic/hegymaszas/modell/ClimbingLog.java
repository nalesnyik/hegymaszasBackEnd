package progmatic.hegymaszas.modell;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class ClimbingLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    private MyUser user;

    @NotNull
    @ManyToOne
    private Route route;

    @NotNull
    private LocalDateTime creationDate;


    public ClimbingLog() {
        creationDate = LocalDateTime.now();
    }
}
