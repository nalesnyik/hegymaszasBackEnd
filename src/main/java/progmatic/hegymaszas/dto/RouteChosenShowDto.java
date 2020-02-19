package progmatic.hegymaszas.dto;

import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.modell.enums.Orientation;
import progmatic.hegymaszas.modell.enums.SteepnessType;
import progmatic.hegymaszas.modell.enums.WeatherRain;
import progmatic.hegymaszas.modell.enums.WeatherSun;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class RouteChosenShowDto {
    private String name;
    private String sectorName;
    private long sectorId;
    private String climbingPlaceName;
    private long climbingPlaceId;

    private int height;
    private int numOfBolts;
    private String grade;

    private List<ClimbingLogShowDto> climbingLogs;
    private List<FeedbackShowDto> feedbacks;
    private List<RatingShowDto> ratings = new ArrayList<>();
    private Map<Long, String> urlOfImages = new TreeMap();


    private Orientation orientation;
    private SteepnessType steepnessType;
    private WeatherRain weatherRain;
    private WeatherSun weatherSun;

    private double avgRatingByBeauty;
    private double avgRatingByDifficulty;
    private double avgRatingBySafety;


    public RouteChosenShowDto(Route route) {
        this.name = route.getName();

        this.sectorName = route.getSector().getName();
        this.sectorId = route.getSector().getId();
        this.climbingPlaceName = route.getSector().getClimbingPlace().getName();
        this.climbingPlaceId = route.getSector().getClimbingPlace().getId();

        this.height = route.getHeight();
        this.numOfBolts = route.getNumOfBolts();
        switch (route.getGrade() % 3) {
            case 0:
                grade = String.valueOf(route.getGrade() / 3 + 3);
                grade = grade.concat("+");
                break;
            case 1:
                grade = String.valueOf(route.getGrade() / 3 + 4);
                grade = grade.concat("-");
                break;
            case 2:
                grade = String.valueOf(route.getGrade() / 3 + 4);
                break;
            default:
                break;
        }

        this.climbingLogs = route.getClimbingLogs().stream().map(ClimbingLogShowDto::new).sorted((x1, x2) -> (int) (x1.getId() - x2.getId())).collect(Collectors.toList());
        this.feedbacks = route.getFeedbacks().stream().map(FeedbackShowDto::new).sorted((x1, x2) -> (int) (x1.getId() - x2.getId())).collect(Collectors.toList());

        orientation = route.getOrientation();
        steepnessType = route.getSteepnessType();
        weatherRain = route.getWeatherRain();
        weatherSun = route.getWeatherSun();

        avgRatingByBeauty = route.getAvgRatingByBeauty();
        avgRatingByDifficulty = route.getAvgRatingByDifficulty();
        avgRatingBySafety = route.getAvgRatingBySafety();
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


    public List<ClimbingLogShowDto> getClimbingLogs() {
        return climbingLogs;
    }


    public void setClimbingLogs(List<ClimbingLogShowDto> climbingLogs) {
        this.climbingLogs = climbingLogs;
    }


    public List<FeedbackShowDto> getFeedbacks() {
        return feedbacks;
    }


    public void setFeedbacks(List<FeedbackShowDto> feedbacks) {
        this.feedbacks = feedbacks;
    }


    public List<RatingShowDto> getRatings() {
        return ratings;
    }


    public void setRatings(List<RatingShowDto> ratings) {
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


    public Map<Long, String> getUrlOfImages() {
        return urlOfImages;
    }


    public void setUrlOfImages(Map<Long, String> urlOfImages) {
        this.urlOfImages = urlOfImages;
    }
}
