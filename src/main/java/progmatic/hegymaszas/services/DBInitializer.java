package progmatic.hegymaszas.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progmatic.hegymaszas.modell.MyAuthority;
import progmatic.hegymaszas.modell.MyUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;

@Service
public class DBInitializer {
    private static final Logger logger = LoggerFactory.getLogger(DBInitializer.class);
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager em;


    public DBInitializer(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    @EventListener(ContextRefreshedEvent.class)
    public void onAppStartup(ContextRefreshedEvent ev) throws ServletException {
        DBInitializer me = ev.getApplicationContext().getBean(DBInitializer.class);
        me.settings();
    }


    @Transactional
    public void settings() {
        if (em.createQuery("select u from MyUser u", MyUser.class).getResultList().isEmpty()) {

            MyUser admin = new MyUser();
            admin.setName("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setEmail("admin@admin.hu");
            MyUser user = new MyUser();
            user.setName("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setEmail("user@user.hu");
            MyAuthority myadmin = new MyAuthority("ROLE_ADMIN");
            MyAuthority myuser = new MyAuthority("ROLE_USER");
            em.persist(myadmin);
            em.persist(myuser);
            myadmin.getUsers().add(admin);
            myuser.getUsers().add(user);

            em.persist(admin);
            em.persist(user);
        }
    }
}
