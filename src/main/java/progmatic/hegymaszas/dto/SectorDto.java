package progmatic.hegymaszas.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SectorDto {

    private long id;

    @NotNull
    @NotBlank
    private String name;

    private int numOfRoutes;

    private int numOfFeedbacks;


    public SectorDto() {
    }


    public SectorDto(long id, String sectorName, int numOfRoutes, int numOfFeedbacks) {
        this.id = id;
        this.name = sectorName;
        this.numOfRoutes = numOfRoutes;
        this.numOfFeedbacks = numOfFeedbacks;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public int getNumOfRoutes() {
        return numOfRoutes;
    }


    public void setNumOfRoutes(int numOfRoutes) {
        this.numOfRoutes = numOfRoutes;
    }


    public int getNumOfFeedbacks() {
        return numOfFeedbacks;
    }


    public void setNumOfFeedbacks(int numOfFeedbacks) {
        this.numOfFeedbacks = numOfFeedbacks;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }
}
