package progmatic.hegymaszas.dto;

public class LocationDto {
    private double lon;
    private double lat;
    private double dist;


    @Override
    public String toString() {
        return "LocationDto{" +
                "lon=" + lon +
                ", lat=" + lat +
                ", dist=" + dist +
                '}';
    }


    public LocationDto() {
    }


    public double getLon() {
        return lon;
    }


    public void setLon(double lon) {
        this.lon = lon;
    }


    public double getLat() {
        return lat;
    }


    public void setLat(double lat) {
        this.lat = lat;
    }


    public double getDist() {
        return dist;
    }


    public void setDist(double dist) {
        this.dist = dist;
    }
}
