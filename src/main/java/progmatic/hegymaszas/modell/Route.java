package progmatic.hegymaszas.modell;

import org.springframework.lang.NonNull;
import progmatic.hegymaszas.modell.enums.Orientation;
import progmatic.hegymaszas.modell.enums.SteepnessType;
import progmatic.hegymaszas.modell.enums.WeatherRain;
import progmatic.hegymaszas.modell.enums.WeatherSun;
import progmatic.hegymaszas.modell.messages.ClimbingLog;
import progmatic.hegymaszas.modell.messages.Feedback;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @NotBlank
    private String name;

    private int height;

    private int numOfBolts;

    @ManyToOne
    private Sector sector;

    @OneToMany(mappedBy = "route")
    private List<ClimbingLog> climbingLogs = new ArrayList<>();

    @Lob
    private byte[] photos;

    private double grade;

    @OneToMany(mappedBy = "route")
    private List<Feedback> feedbacks;

    private Orientation orientation;
    private SteepnessType steepnessType;
    private WeatherRain weatherRain;
    private WeatherSun weatherSun;


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


    public List<ClimbingLog> getClimbingLogs() {
        return climbingLogs;
    }


    public void setClimbingLogs(List<ClimbingLog> climbingLogs) {
        this.climbingLogs = climbingLogs;
    }


    public byte[] getPhotos() {
        return photos;
    }


    public void setPhotos(byte[] photos) {
        this.photos = photos;
    }


    public double getGrade() {
        return grade;
    }


    public void setGrade(double grade) {
        this.grade = grade;
    }


    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }


    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
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
}
