package progmatic.hegymaszas.dto;

import progmatic.hegymaszas.annotations.UniqueEmailConstraint;
import progmatic.hegymaszas.annotations.UniqueUsernameConstraint;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.util.Date;

public class MyUserDto {
    @Id
    @Column(unique = true)
    @NotNull
    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[a-zA-Z0-9áéóöőúüűÁÉÓÖŐÚÜŰ_$]*")
    @UniqueUsernameConstraint
    private String name;

    @NotNull
    @NotBlank
    private String password;

    @Email
    @Column(unique = true)
    @UniqueEmailConstraint
    private String email;

    private Date dateOfBirth;


    private Date dateOfFirstClimb;


    public MyUserDto() {
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
}
