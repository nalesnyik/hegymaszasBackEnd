package progmatic.hegymaszas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progmatic.hegymaszas.modell.ClimbingPlace;
import progmatic.hegymaszas.repositories.ClimbingRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class ClimbingPlaceService {

    @PersistenceContext
    EntityManager em;

    private ClimbingRepository climbingRepository;


    @Autowired
    public ClimbingPlaceService(ClimbingRepository climbingRepository) {
        this.climbingRepository = climbingRepository;
    }


    @Transactional
    public List<ClimbingPlace> getAllClimbingPlace() {
        return em.createQuery("SELECT cp from ClimbingPlace cp", ClimbingPlace.class).getResultList();

    }
}
