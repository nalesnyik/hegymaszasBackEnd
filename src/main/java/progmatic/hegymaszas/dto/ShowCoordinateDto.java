package progmatic.hegymaszas.dto;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Point;

public class ShowCoordinateDto {
    private double lon;
    private double lat;


    public ShowCoordinateDto() {
    }
    public ShowCoordinateDto(Point point) {
        Coordinate coordinate = point.getCoordinate();
        lon = coordinate.x;
        lat = coordinate.y;
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
}
