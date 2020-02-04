package progmatic.hegymaszas.modell;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class MyAuthority implements GrantedAuthority {

    @Id
    private String authority;

    @ManyToMany
    private List<MyUser> users = new ArrayList<>();


    public MyAuthority(String authority) {
        this.authority = authority;
    }


    public MyAuthority() {
    }


    @Override
    public String getAuthority() {
        return authority;
    }


    public List<MyUser> getUsers() {
        return users;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyAuthority authority1 = (MyAuthority) o;
        return Objects.equals(authority, authority1.authority);
    }


    @Override
    public int hashCode() {
        return Objects.hash(authority);
    }


}