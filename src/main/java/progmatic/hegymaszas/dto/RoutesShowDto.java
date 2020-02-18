package progmatic.hegymaszas.dto;

import progmatic.hegymaszas.modell.Route;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class RoutesShowDto {
    private long id;

    @NotNull
    @NotBlank
    private String name;
    private int grade;


    public RoutesShowDto(Route route) {
        this.id = route.getId();
        this.name = route.getName();
        this.grade = route.getGrade();
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


    public int getGrade() {
        return grade;
    }


    public void setGrade(int grade) {
        this.grade = grade;
    }
}
