package progmatic.hegymaszas.dto;

public class RouteCreateDto {
    private String routeName;
    private long sectorId;
    private long climbingPlaceId;
    private String grade;
    private int height;
    private int numOfBolts;


    public RouteCreateDto() {
    }


    public String getRouteName() {
        return routeName;
    }


    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }


    public long getSectorId() {
        return sectorId;
    }


    public void setSectorId(long sectorId) {
        this.sectorId = sectorId;
    }


    public long getClimbingPlaceId() {
        return climbingPlaceId;
    }


    public void setClimbingPlaceId(long climbingPlaceId) {
        this.climbingPlaceId = climbingPlaceId;
    }


    public String getGrade() {
        return grade;
    }


    public void setGrade(String grade) {
        this.grade = grade;
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
}
