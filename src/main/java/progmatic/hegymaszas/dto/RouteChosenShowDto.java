package progmatic.hegymaszas.dto;

import progmatic.hegymaszas.modell.Rating;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.modell.enums.Orientation;
import progmatic.hegymaszas.modell.enums.SteepnessType;
import progmatic.hegymaszas.modell.enums.WeatherRain;
import progmatic.hegymaszas.modell.enums.WeatherSun;
import progmatic.hegymaszas.modell.messages.ClimbingLog;
import progmatic.hegymaszas.modell.messages.Feedback;

import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class RouteChosenShowDto {
    private String name;
    private String sectorName;
    private long sectorId;
    private String climbingPlaceName;
    private long climbingPlaceId;

    private int height;
    private int numOfBolts;
    private String grade;

    private byte[] photos;
    @OneToMany(mappedBy = "route")
    private List<ClimbingLog> climbingLogs = new ArrayList<>();

    @OneToMany(mappedBy = "route")
    private List<Feedback> feedbacks = new ArrayList<>();

    @OneToMany(mappedBy = "route")
    private List<Rating> ratings = new ArrayList<>();

    private Orientation orientation;
    private SteepnessType steepnessType;
    private WeatherRain weatherRain;
    private WeatherSun weatherSun;


    public RouteChosenShowDto(Route route) {
        this.name = route.getName();
        this.sectorName = route.getSector().getName();
        this.sectorId = route.getSector().getId();
        this.climbingPlaceName = route.getSector().getClimbingPlace().getName();
        this.climbingPlaceId = route.getSector().getClimbingPlace().getId();

        this.height = route.getHeight();
        this.numOfBolts = route.getNumOfBolts();
        grade = String.valueOf( route.getGrade());

        orientation = route.getOrientation();
        steepnessType = route.getSteepnessType();
        weatherRain = route.getWeatherRain();
        weatherSun = route.getWeatherSun();
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getSectorName() {
        return sectorName;
    }


    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }


    public long getSectorId() {
        return sectorId;
    }


    public void setSectorId(long sectorId) {
        this.sectorId = sectorId;
    }


    public String getClimbingPlaceName() {
        return climbingPlaceName;
    }


    public void setClimbingPlaceName(String climbingPlaceName) {
        this.climbingPlaceName = climbingPlaceName;
    }


    public long getClimbingPlaceId() {
        return climbingPlaceId;
    }


    public void setClimbingPlaceId(long climbingPlaceId) {
        this.climbingPlaceId = climbingPlaceId;
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


    public String getGrade() {
        return grade;
    }


    public void setGrade(String grade) {
        this.grade = grade;
    }


    public byte[] getPhotos() {
        return photos;
    }


    public void setPhotos(byte[] photos) {
        this.photos = photos;
    }


    public List<ClimbingLog> getClimbingLogs() {
        return climbingLogs;
    }


    public void setClimbingLogs(List<ClimbingLog> climbingLogs) {
        this.climbingLogs = climbingLogs;
    }


    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }


    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }


    public List<Rating> getRatings() {
        return ratings;
    }


    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
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
