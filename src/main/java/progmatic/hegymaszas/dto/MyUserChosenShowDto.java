package progmatic.hegymaszas.dto;

import progmatic.hegymaszas.modell.MyUser;
import java.util.Date;

public class MyUserChosenShowDto {
    private String name;
    private String email;
    private Date dateOfBorn;
    private Date dateOfFirstClimb;

//    private List<ClimbingLog> climbingLogs = new ArrayList<>();


    public MyUserChosenShowDto() {
    }


    public MyUserChosenShowDto(MyUser user) {
        name = user.getName();
        email = user.getEmail();
    }


    public MyUserChosenShowDto(String name, String email, Date dateOfBorn, Date dateOfFirstClimb) {
        this.name = name;
        this.email = email;
        this.dateOfBorn = dateOfBorn;
        this.dateOfFirstClimb = dateOfFirstClimb;
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

    public Date getDateOfBorn() {
        return dateOfBorn;
    }

    public void setDateOfBorn(Date dateOfBorn) {
        this.dateOfBorn = dateOfBorn;
    }

    public Date getDateOfFirstClimb() {
        return dateOfFirstClimb;
    }

    public void setDateOfFirstClimb(Date dateOfFirstClimb) {
        this.dateOfFirstClimb = dateOfFirstClimb;
    }
}
