package progmatic.hegymaszas.dto;

import progmatic.hegymaszas.modell.Route;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RoutesShowDto {
    private long id;

    @NotNull
    @NotBlank
    private String name;

    private int numOfFeedbacks;


    public RoutesShowDto(Route route) {
        this.id = route.getId();
        this.name = route.getName();
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


    public int getNumOfFeedbacks() {
        return numOfFeedbacks;
    }


    public void setNumOfFeedbacks(int numOfFeedbacks) {
        this.numOfFeedbacks = numOfFeedbacks;
    }
}
