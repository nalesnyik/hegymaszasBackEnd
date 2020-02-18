package progmatic.hegymaszas.dto;

public class DistanceCheckerDto {
    private Integer dist;
    private double userLong;
    private double userLat;


    public DistanceCheckerDto() {
    }


    public Integer getDist() {
        return dist;
    }


    public void setDist(Integer dist) {
        this.dist = dist;
    }


    public double getUserLong() {
        return userLong;
    }


    public void setUserLong(double userLong) {
        this.userLong = userLong;
    }


    public double getUserLat() {
        return userLat;
    }


    public void setUserLat(double userLat) {
        this.userLat = userLat;
    }
}
