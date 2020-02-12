package progmatic.hegymaszas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import progmatic.hegymaszas.modell.*;
import progmatic.hegymaszas.dto.MyUserDto;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @PersistenceContext
    EntityManager em;
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, EmailService emailService) {
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user;
        try {
            user = em.find(MyUser.class, username);
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
        emailService.sendRegistrationEmail(user);
    }

    @Transactional
    public boolean userExists(String username) {
        return em.find(MyUser.class, username) != null;
    }

    @Transactional
    public boolean userEmailExists(String email) {
        try {
            em.createQuery("SELECT c FROM MyUser c where c.email = :email", MyUser.class)
                    .setParameter("email", email)
                    .getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        }
    }


}
