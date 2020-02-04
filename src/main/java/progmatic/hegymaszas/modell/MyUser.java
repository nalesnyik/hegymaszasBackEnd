package progmatic.hegymaszas.modell;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class MyUser {

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

    @ManyToMany(targetEntity = MyAuthority.class, mappedBy = "users")
    private Set<GrantedAuthority> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private List<ClimbingLog> climbingLogs = new ArrayList<>();


    public MyUser() {
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getPassword() {
        return password;
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
}
