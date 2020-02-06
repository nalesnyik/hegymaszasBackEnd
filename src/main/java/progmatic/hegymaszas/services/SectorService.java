package progmatic.hegymaszas.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progmatic.hegymaszas.modell.Sector;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class SectorService {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public List<Sector> getAllSector(){
        return em.createQuery("SELECT s FROM Sector s").getResultList();
    }
}
