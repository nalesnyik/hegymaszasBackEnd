package progmatic.hegymaszas.repositories;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.springframework.stereotype.Repository;
import progmatic.hegymaszas.modell.Sector;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class PostGisRepository {
    @PersistenceContext
    EntityManager em;


    public List<Sector> getSectorsWithinDistanceOfLocation(double lon, double lat, double dist) {

        return em.createQuery("SELECT s FROM Sector s WHERE ST_Distance_Sphere(s.location, ST_MakePoint(:lon, :lat)) <= :dist * 1000", Sector.class)
                .setParameter("lon", lon)
                .setParameter("lat", lat)
                .setParameter("dist", dist)
                .getResultList();
    }


    public List<Sector> getSectorsWithinDistanceOfLocation2(Double lon, Double lat, Double dist) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));

        return em.createQuery("SELECT s FROM Sector s WHERE ST_DWithin(s.location,:point,:dist,true)=true", Sector.class)
                .setParameter("point", point)
                .setParameter("dist", dist*1000)
                .getResultList();
    }
}