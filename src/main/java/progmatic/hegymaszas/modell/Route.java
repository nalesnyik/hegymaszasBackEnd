package progmatic.hegymaszas.modell;

import org.springframework.lang.NonNull;
import progmatic.hegymaszas.dto.RouteCreateDto;
import progmatic.hegymaszas.modell.enums.Orientation;
import progmatic.hegymaszas.modell.enums.SteepnessType;
import progmatic.hegymaszas.modell.enums.WeatherRain;
import progmatic.hegymaszas.modell.enums.WeatherSun;
import progmatic.hegymaszas.modell.messages.ClimbingLog;
import progmatic.hegymaszas.modell.messages.Feedback;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "getAverageBeautyRating",
                        query = "SELECT avg (r.ratingByBeauty) from Rating r where r.route.id =: id"),
                @NamedQuery(name = "getAverageDifficultyRating",
                        query = "SELECT avg (r.ratingByDifficulty) from Rating r where r.route.id =:id"),
                @NamedQuery(name = "getAverageSafetyRating",
                        query = "SELECT avg (r.ratingBySafety) from Rating r where r.route.id = :id"),
        }
)
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @NotBlank
    private String name;

    private int height;

    @Positive
    private int numOfBolts;

    @ManyToOne
    private Sector sector;

    @OneToMany(mappedBy = "route")
    private Set<ClimbingLog> climbingLogs = new HashSet<>();

    @Lob
    private byte[] photos;

    private int grade;

    @OneToMany(mappedBy = "route")
    private Set<Feedback> feedbacks = new HashSet<>();

    @OneToMany(mappedBy = "route")
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "route")
    private List<ImageOfRoute> images = new ArrayList<>();

    private double avgRatingByBeauty;
    private double avgRatingByDifficulty;
    private double avgRatingBySafety;

    private Orientation orientation;
    private SteepnessType steepnessType;
    private WeatherRain weatherRain;
    private WeatherSun weatherSun;

    private boolean isRouteVerified;

    private int verificationCounter;


    public Route() {
    }


    public Route(RouteCreateDto route, Sector sector) {
        this.name = route.getRouteName();
        this.height = route.getHeight();
        this.numOfBolts = route.getNumOfBolts();
        this.sector = sector;
        switch (String.valueOf(route.getGrade().charAt(1))) {
            case "-":
                this.grade = 3 * (Character.getNumericValue(route.getGrade().charAt(0)) - 4) + 1;
                break;
            case "":
                this.grade = 3 * (Character.getNumericValue(route.getGrade().charAt(0)) - 4) + 2;
                break;
            case "+":
                this.grade = 3 * (Character.getNumericValue(route.getGrade().charAt(0)) - 3);
                break;
            default:
                break;
        }
    }


    public boolean isRouteVerified() {
        return isRouteVerified;
    }


    public void setRouteVerified(boolean routeVerified) {
        isRouteVerified = routeVerified;
    }


    public int getVerificationCounter() {
        return verificationCounter;
    }


    public void setVerificationCounter(int verificationCounter) {
        this.verificationCounter = verificationCounter;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    @NonNull
    public String getName() {
        return name;
    }


    public void setName(@NonNull String name) {
        this.name = name;
    }


    public int getHeight() {
        return height;
    }


    public void setHeight(int height) {
        this.height = height;
    }


    public int getNumOfBolts() {
        return numOfBolts;
    }


    public void setNumOfBolts(int numOfBolts) {
        this.numOfBolts = numOfBolts;
    }


    public Sector getSector() {
        return sector;
    }


    public void setSector(Sector sector) {
        this.sector = sector;
    }


    public byte[] getPhotos() {
        return photos;
    }


    public void setPhotos(byte[] photos) {
        this.photos = photos;
    }


    public int getGrade() {
        return grade;
    }


    public void setGrade(int grade) {
        this.grade = grade;
    }


    public Orientation getOrientation() {
        return orientation;
    }


    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }


    public SteepnessType getSteepnessType() {
        return steepnessType;
    }


    public void setSteepnessType(SteepnessType steepnessType) {
        this.steepnessType = steepnessType;
    }


    public WeatherRain getWeatherRain() {
        return weatherRain;
    }


    public void setWeatherRain(WeatherRain weatherRain) {
        this.weatherRain = weatherRain;
    }


    public WeatherSun getWeatherSun() {
        return weatherSun;
    }


    public void setWeatherSun(WeatherSun weatherSun) {
        this.weatherSun = weatherSun;
    }


    public List<Rating> getRatings() {
        return ratings;
    }


    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }


    public double getAvgRatingByBeauty() {
        return avgRatingByBeauty;
    }


    public void setAvgRatingByBeauty(double avgRatingByBeauty) {
        this.avgRatingByBeauty = avgRatingByBeauty;
    }


    public double getAvgRatingByDifficulty() {
        return avgRatingByDifficulty;
    }


    public void setAvgRatingByDifficulty(double avgRatingByDifficulty) {
        this.avgRatingByDifficulty = avgRatingByDifficulty;
    }


    public double getAvgRatingBySafety() {
        return avgRatingBySafety;
    }


    public void setAvgRatingBySafety(double avgRatingBySafety) {
        this.avgRatingBySafety = avgRatingBySafety;
    }


    public Set<ClimbingLog> getClimbingLogs() {
        return climbingLogs;
    }


    public void setClimbingLogs(Set<ClimbingLog> climbingLogs) {
        this.climbingLogs = climbingLogs;
    }


    public Set<Feedback> getFeedbacks() {
        return feedbacks;
    }


    public void setFeedbacks(Set<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }


    public List<ImageOfRoute> getImages() {
        return images;
    }


    public void setImages(List<ImageOfRoute> images) {
        this.images = images;
    }
}

