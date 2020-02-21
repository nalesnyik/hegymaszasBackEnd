package progmatic.hegymaszas.dto;

import progmatic.hegymaszas.modell.MyUser;
import progmatic.hegymaszas.services.ClimbingService;;

import java.util.*;


public class MyUserChosenShowDto {
    private String name;
    private String email;
    private Date dateOfBirth;
    private Date dateOfFirstClimb;
    private List<FeedbackShowDto> feedbacks = new ArrayList<>();
    private List<ClimbingLogShowDto> climbingLogs = new ArrayList<>();
    private Map<Long, String> userImages;
    private String profilePictureUrl;


    public MyUserChosenShowDto() {
    }

    public MyUserChosenShowDto(MyUser user, ClimbingService climbingService) {
        name = user.getName();
        email = user.getEmail();
        dateOfBirth = user.getDateOfBirth();
        dateOfFirstClimb = user.getDateOfFirstClimb();
        climbingLogs = climbingService.createClimbingLogShow(user.getClimbingLogs());
        feedbacks = climbingService.createFeedbackShow(user.getFeedbacks());
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


    public Map<Long, String> getUserImages() {
        return userImages;
    }

    public void setUserImages(Map<Long, String> userImages) {
        this.userImages = userImages;
    }


    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }


    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public List<FeedbackShowDto> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<FeedbackShowDto> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<ClimbingLogShowDto> getClimbingLogs() {
        return climbingLogs;
    }

    public void setClimbingLogs(List<ClimbingLogShowDto> climbingLogs) {
        this.climbingLogs = climbingLogs;
    }
}
