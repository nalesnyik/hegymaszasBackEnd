package progmatic.hegymaszas.modell;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "getSectorsOfClimbingPlace",
                        query = "SELECT s FROM ClimbingPlace c JOIN c.sectors as s WHERE c.id=:id"),
                @NamedQuery(name = "getNumOfRoutesOfSector",
                        query = "SELECT count(s) FROM Sector s JOIN s.routes as r WHERE s.id=:id"),
                @NamedQuery(name = "getNumOfFeedbacksOfSector",
                        query = "SELECT count(s) FROM Sector s JOIN s.routes as r JOIN r.feedbacks as f WHERE s.id=:id"),
        }
)
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @NotBlank
    private String name;

    @OneToMany(mappedBy = "sector")
    private List<Route> routes = new ArrayList<>();

    @ManyToOne
    private ClimbingPlace climbingPlace;


    //    @Size(min = 50, max = 1000)
    private String travelGuide;

    private double longitude;
    private double latitude;


    public Sector() {
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public List<Route> getRoutes() {
        return routes;
    }


    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }


    public ClimbingPlace getClimbingPlace() {
        return climbingPlace;
    }


    public void setClimbingPlace(ClimbingPlace climbingPlace) {
        this.climbingPlace = climbingPlace;
    }


    public String getTravelGuide() {
        return travelGuide;
    }


    public void setTravelGuide(String travelGuide) {
        this.travelGuide = travelGuide;
    }


    public double getLongitude() {
        return longitude;
    }


    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }


    public double getLatitude() {
        return latitude;
    }


    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }
}

