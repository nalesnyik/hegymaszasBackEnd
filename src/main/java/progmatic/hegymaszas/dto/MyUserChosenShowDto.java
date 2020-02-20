package progmatic.hegymaszas.dto;

import progmatic.hegymaszas.modell.MyUser;
import progmatic.hegymaszas.modell.messages.ClimbingLog;
import java.util.*;


public class MyUserChosenShowDto {
    private String name;
    private String email;
    private Date dateOfBirth;
    private Date dateOfFirstClimb;

    private List<ClimbingLog> climbingLogs = new ArrayList<>();
    private Map<Long, String> userImages;


    public MyUserChosenShowDto() {
    }

    public MyUserChosenShowDto(MyUser user) {
        name = user.getName();
        email = user.getEmail();
        dateOfBirth = user.getDateOfBirth();
        dateOfFirstClimb = user.getDateOfFirstClimb();
        climbingLogs = user.getClimbingLogs();
    }


    public MyUserChosenShowDto(String name, String email, Date dateOfBirth, Date dateOfFirstClimb) {
        this.name = name;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfFirstClimb() {
        return dateOfFirstClimb;
    }

    public void setDateOfFirstClimb(Date dateOfFirstClimb) {
        this.dateOfFirstClimb = dateOfFirstClimb;
    }

    public List<ClimbingLog> getClimbingLogs() {
        return climbingLogs;
    }

    public void setClimbingLogs(List<ClimbingLog> climbingLogs) {
        this.climbingLogs = climbingLogs;
    }

    public Map<Long, String> getUserImages() {
        return userImages;
    }

    public void setUserImages(Map<Long, String> userImages) {
        this.userImages = userImages;
    }

}
