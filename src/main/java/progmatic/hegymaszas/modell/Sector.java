package progmatic.hegymaszas.modell;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotNull
    @NotBlank
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
}
