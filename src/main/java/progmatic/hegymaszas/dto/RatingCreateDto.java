package progmatic.hegymaszas.dto;

public class RatingCreateDto extends RatingDto {

    private long routeId;


    public RatingCreateDto() {
    }


    public long getRouteId() {
        return routeId;
    }


    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }
}
