package progmatic.hegymaszas.modell;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import progmatic.hegymaszas.modell.messages.ClimbingLog;
import progmatic.hegymaszas.modell.messages.DirectMessage;
import progmatic.hegymaszas.modell.messages.Feedback;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class MyUser implements UserDetails {

    @Id
    @Column(unique = true)
    @NotNull
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9áéóöőúüűÁÉÓÖŐÚÜŰ_$]*")
    private String name;

    @NotNull
    @NotBlank
    private String password;

    @Email
    @Column(unique = true)
    private String email;

    private LocalDateTime registrationDate;
    private Date dateOfBirth;
    private Date dateOfFirstClimb;


    @ManyToMany(targetEntity = MyAuthority.class, mappedBy = "users")
    private Set<GrantedAuthority> roles = new HashSet<>();


    @OneToMany(mappedBy = "user")
    private List<ClimbingLog> climbingLogs = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbacks = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<DirectMessage> sentDirectMsgs = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<DirectMessage> receivedDirectMsgs = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<Rating> ratings = new ArrayList<>();


    @OneToMany(mappedBy = "user")
    private List<ImageOfRoute> images = new ArrayList<>();


    @Lob
    private byte[] profilePicture;


    private String profilePictureContentType;


    public MyUser() {
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }


    public String getPassword() {
        return password;
    }


    @Override
    public String getUsername() {
        return name;
    }


    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }


    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }


    @Override
    public boolean isAccountNonLocked() {
        return true;
    }


    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        return true;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public Set<GrantedAuthority> getRoles() {
        return roles;
    }


    public void setRoles(Set<GrantedAuthority> roles) {
        this.roles = roles;
    }


    public List<ClimbingLog> getClimbingLogs() {
        return climbingLogs;
    }


    public void setClimbingLogs(List<ClimbingLog> climbingLogs) {
        this.climbingLogs = climbingLogs;
    }


    public void addAuthority(MyAuthority authority) {
        roles.add(authority);
    }


    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }


    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }


    public List<DirectMessage> getSentDirectMsgs() {
        return sentDirectMsgs;
    }


    public void setSentDirectMsgs(List<DirectMessage> sentDirectMsgs) {
        this.sentDirectMsgs = sentDirectMsgs;
    }


    public List<DirectMessage> getReceivedDirectMsgs() {
        return receivedDirectMsgs;
    }


    public void setReceivedDirectMsgs(List<DirectMessage> receivedDirectMsgs) {
        this.receivedDirectMsgs = receivedDirectMsgs;
    }


    public List<Rating> getRatings() {
        return ratings;
    }


    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }


    public byte[] getProfilePicture() {
        return profilePicture;
    }


    public void setProfilePicture(byte[] profilePicture) {
        this.profilePicture = profilePicture;
    }


    public String getProfilePictureContentType() {
        return profilePictureContentType;
    }


    public void setProfilePictureContentType(String profilePictureContentType) {
        this.profilePictureContentType = profilePictureContentType;
    }


    public List<ImageOfRoute> getImages() {
        return images;
    }


    public void setImages(List<ImageOfRoute> images) {
        this.images = images;
    }


    public Date getDateOfBirth() {
        return dateOfBirth;
    }


    public void setDateOfBirth(Date dateOfBorn) {
        this.dateOfBirth = dateOfBorn;
    }


    public Date getDateOfFirstClimb() {
        return dateOfFirstClimb;
    }


    public void setDateOfFirstClimb(Date dateOfFirstClimb) {
        this.dateOfFirstClimb = dateOfFirstClimb;
    }
}
