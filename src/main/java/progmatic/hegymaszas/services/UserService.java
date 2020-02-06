package progmatic.hegymaszas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progmatic.hegymaszas.modell.MyAuthority;
import progmatic.hegymaszas.modell.MyUser;
import progmatic.hegymaszas.dto.MyUserDto;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    EntityManager em;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user;
        try {
            user = em.createQuery("SELECT c FROM MyUser c where c.name = :username", MyUser.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException ex) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return user;
    }

    @Transactional
    public void createUser(MyUserDto userDto) {
        MyUser user = new MyUser();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        MyAuthority authority = em.find(MyAuthority.class, "ROLE_USER");
        user.addAuthority(authority);
        user.setClimbingLogs(new ArrayList<>());
        em.persist(user);
    }

    @Transactional
    public boolean userExists(String username) {
        try {
            em.createQuery("SELECT c FROM MyUser c where c.name = :username", MyUser.class)
                    .setParameter("username", username)
                    .getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }
}
