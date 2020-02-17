package progmatic.hegymaszas.repositories;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

public class SectorRepositoryCustomImpl implements SectorRepositoryCustom {

    @PersistenceContext
    EntityManager em;


    public long getMiniProfileId(long sectorId) {
        long id;
        try {
            id = em.createNamedQuery("getMiniProfileId", Long.class)
                    .setParameter("sectorId", sectorId)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return 0;
        }
        return id;
    }


    public long getProfileId(long sectorId) {
        long id;
        try {
            id = em.createNamedQuery("getProfileId", Long.class)
                    .setParameter("sectorId", sectorId)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return 0;
        }
        return id;
    }


    public List<Long> get9idOfMiniImagesOfSector(long sectorId) {
        List<Long> idList;
        try {
            idList = em.createQuery("SELECT i.id FROM Sector s LEFT JOIN s.routes r LEFT JOIN r.images i WHERE s.id=:sectorId AND i.originalImgId>0 ORDER BY i.originalImgId DESC", Long.class)
                    .setMaxResults(9)
                    .setParameter("sectorId", sectorId)
                    .getResultList();
        } catch (NoResultException ex) {
            return null;
        }
        return idList;
    }


    public List<Long> idOfMiniImagesOfSector(long sectorId) {
        return em.createQuery("SELECT i.id FROM Sector s LEFT JOIN s.routes r LEFT JOIN r.images i WHERE s.id=:sectorId AND i.originalImgId>0 ORDER BY i.originalImgId DESC", Long.class)
                .setParameter("sectorId", sectorId)
                .getResultList();
    }
}
