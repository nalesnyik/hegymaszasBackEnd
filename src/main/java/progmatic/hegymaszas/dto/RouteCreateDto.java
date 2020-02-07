package progmatic.hegymaszas.dto;

public class RouteCreateDto {
    private String routeName;
    private String sectorName;
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


    public String getSectorName() {
        return sectorName;
    }


    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
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
