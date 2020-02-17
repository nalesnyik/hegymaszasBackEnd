package progmatic.hegymaszas.dto;

import progmatic.hegymaszas.modell.Sector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SectorChosenShowDto {
    private long id;
    private String name;
    private long climbingPlaceId;
    private String climbingPlaceName;
    private String urlOfMiniPicture;
    private String urlOfPicture;
    private Map<Long, String> urlOfImages;
    private List<RoutesShowDto> routes = new ArrayList<>();


    public SectorChosenShowDto(Sector sector) {
        id = sector.getId();
        name = sector.getName();
        climbingPlaceId = sector.getClimbingPlace().getId();
        climbingPlaceName = sector.getClimbingPlace().getName();

        routes = sector.getRoutes().stream().map(RoutesShowDto::new).collect(Collectors.toList());
    }


    public SectorChosenShowDto() {
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public long getClimbingPlaceId() {
        return climbingPlaceId;
    }


    public void setClimbingPlaceId(long climbingPlaceId) {
        this.climbingPlaceId = climbingPlaceId;
    }


    public String getClimbingPlaceName() {
        return climbingPlaceName;
    }


    public void setClimbingPlaceName(String climbingPlaceName) {
        this.climbingPlaceName = climbingPlaceName;
    }


    public String getUrlOfPicture() {
        return urlOfPicture;
    }


    public void setUrlOfPicture(String urlOfPicture) {
        this.urlOfPicture = urlOfPicture;
    }


    public Map<Long, String> getUrlOfImages() {
        return urlOfImages;
    }


    public void setUrlOfImages(Map<Long, String> urlOfImages) {
        this.urlOfImages = urlOfImages;
    }


    public List<RoutesShowDto> getRoutes() {
        return routes;
    }


    public void setRoutes(List<RoutesShowDto> routes) {
        this.routes = routes;
    }


    public String getUrlOfMiniPicture() {
        return urlOfMiniPicture;
    }


    public void setUrlOfMiniPicture(String urlOfMiniPicture) {
        this.urlOfMiniPicture = urlOfMiniPicture;
    }
}
