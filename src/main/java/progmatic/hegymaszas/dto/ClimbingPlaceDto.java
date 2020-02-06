package progmatic.hegymaszas.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClimbingPlaceDto {
    private long id;

    @NotNull
    @NotBlank
    private String name;

    private int numOfRoutes;

    private int numOfFeedbacks;


    public ClimbingPlaceDto() {
    }


    public ClimbingPlaceDto(long id, String name, int numOfRoutes, int numOfFeedbacks) {
        this.id = id;
        this.name = name;
        this.numOfRoutes = numOfRoutes;
        this.numOfFeedbacks = numOfFeedbacks;
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
}

