package progmatic.hegymaszas.modell;

import progmatic.hegymaszas.modell.enums.ClimbingPlaceType;

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

    @NotNull
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "climbingPlace")
    private List<Sector> sectors = new ArrayList<>();

    @NotNull
    @NotBlank
    private ClimbingPlaceType type;

    private String approachGuide;


    public ClimbingPlace() {
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public List<Sector> getSectors() {
        return sectors;
    }


    public void setSectors(List<Sector> sectors) {
        this.sectors = sectors;
    }

    public ClimbingPlaceType getType() {
        return type;
    }


    public void setType(ClimbingPlaceType type) {
        this.type = type;
    }


    public String getApproachGuide() {
        return approachGuide;
    }


    public void setApproachGuide(String approachGuide) {
        this.approachGuide = approachGuide;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
}
