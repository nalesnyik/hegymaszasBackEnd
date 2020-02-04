package progmatic.hegymaszas.modell;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "sector")
    private List<Route> routes = new ArrayList<>();

    @ManyToOne
    private ClimbingPlace climbingPlace;
}
