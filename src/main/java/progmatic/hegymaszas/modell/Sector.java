package progmatic.hegymaszas.modell;

import com.vividsolutions.jts.geom.Point;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.Type;

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
                @NamedQuery(name = "getMiniProfileId",
                        query = "SELECT i.id FROM Sector s LEFT JOIN s.images AS i WHERE i.originalImgId>0 AND i.sector.id=:sectorId"),
                @NamedQuery(name = "getProfileId",
                        query = "SELECT i.id FROM Sector s LEFT JOIN s.images AS i WHERE i.originalImgId=0 AND i.sector.id=:sectorId")
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

    private String travelGuide;

    private double longitude;
    private double latitude;

    private Point location;

    @OneToMany(mappedBy = "sector")
    private List<ImageOfSector> images = new ArrayList<>();


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


    public List<ImageOfSector> getImages() {
        return images;
    }


    public void setImages(List<ImageOfSector> images) {
        this.images = images;
    }


    public Point getLocation() {
        return location;
    }


    public void setLocation(Point location) {
        this.location = location;
    }
}

