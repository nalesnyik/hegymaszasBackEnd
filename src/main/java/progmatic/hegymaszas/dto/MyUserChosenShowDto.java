package progmatic.hegymaszas.dto;

import progmatic.hegymaszas.modell.MyUser;

import javax.persistence.Lob;

public class MyUserChosenShowDto {
    private String name;

    private String email;

//    private List<ClimbingLog> climbingLogs = new ArrayList<>();


    public MyUserChosenShowDto() {
    }


    public MyUserChosenShowDto(MyUser user) {
        name = user.getName();
        email = user.getEmail();
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }
}
