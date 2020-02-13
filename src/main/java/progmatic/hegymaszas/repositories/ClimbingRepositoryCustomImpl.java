package progmatic.hegymaszas.repositories;

import progmatic.hegymaszas.modell.ClimbingPlace;
import progmatic.hegymaszas.modell.Sector;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class ClimbingRepositoryCustomImpl implements ClimbingRepositoryCustom {

    @PersistenceContext
    EntityManager em;


    public List<ClimbingPlace> getClimbingPlaces() {
        return em.createQuery("SELECT c FROM ClimbingPlace c", ClimbingPlace.class).getResultList();
    }


    public List<Sector> getSectorsOfClimbingPlace(long idOfClimbingPlace) {
//        return em.createQuery("SELECT s.name FROM ClimbingPlace c JOIN c.sectors as s WHERE c.name=:name", String.class)
//                .setParameter("name", nameOfClimbingPlace)
//                .getResultList();
        return em.createNamedQuery("getSectorsOfClimbingPlace", Sector.class).setParameter("id", idOfClimbingPlace).getResultList();
    }


    public int getNumOfRoutesOfSector(long sectorId) {
        long numOfRoutes = em.createNamedQuery("getNumOfRoutesOfSector", Long.class)
                .setParameter("id", sectorId)
                .getSingleResult();
        return (int) numOfRoutes;
    }


    public int getNumOfFeedbacksOfSector(long sectorId) {
        long numOfRoutes = em.createNamedQuery("getNumOfFeedbacksOfSector", Long.class)
                .setParameter("id", sectorId)
                .getSingleResult();
        return (int) numOfRoutes;
    }


    public int getNumOfRoutesOfClimbingPlace(long climbingPlaceId) {
        long numOfRoutes = em.createQuery("SELECT count(c) FROM ClimbingPlace c JOIN c.sectors as s JOIN s.routes as r WHERE c.id=:id", Long.class)
                .setParameter("id", climbingPlaceId)
                .getSingleResult();
        return (int) numOfRoutes;
    }


    public int getNumOfFeedbacksOfClimbingPlace(long climbingPlaceId) {
        long numOfRoutes = em.createQuery("SELECT count(c) FROM ClimbingPlace c JOIN c.sectors as s JOIN s.routes as r JOIN r.feedbacks as f WHERE c.id=:id", Long.class)
                .setParameter("id", climbingPlaceId)
                .getSingleResult();
        return (int) numOfRoutes;
    }
}
