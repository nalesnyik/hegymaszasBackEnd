package progmatic.hegymaszas.modell;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Sector sector;

    @OneToMany(mappedBy = "route")
    private List<ClimbingLog> climbingLogs = new ArrayList<>();
}
