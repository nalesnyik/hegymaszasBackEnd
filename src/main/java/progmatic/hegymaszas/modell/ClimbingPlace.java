package progmatic.hegymaszas.modell;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ClimbingPlace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "climbingPlace")
    private List<Sector> sectors = new ArrayList<>();

    @NotNull
    @NotBlank
    private ClimbingType type;

    @NotNull
    @NotBlank
    private String approachGuide;

//    private Location
}
