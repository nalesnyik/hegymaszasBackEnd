package progmatic.hegymaszas.repositories;

import org.springframework.stereotype.Repository;
import progmatic.hegymaszas.modell.Sector;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostGisRepository {
    @PersistenceContext
    EntityManager em;


    public List<Sector> getSectorsWithinDistanceOfLocation(double lon, double lat) {

        return em.createQuery("SELECT s FROM Sector s WHERE ST_Distance_Sphere(s.location, ST_MakePoint(:lon, :lat)) <= radius_mi * 1000", Sector.class)
                .setParameter("lon", lon)
                .setParameter("lat", lat)
                .getResultList();
    }
}
