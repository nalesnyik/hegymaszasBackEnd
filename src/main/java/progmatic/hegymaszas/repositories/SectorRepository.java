package progmatic.hegymaszas.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import progmatic.hegymaszas.modell.Sector;

public interface SectorRepository extends JpaRepository<Sector, Long>, SectorRepositoryCustom {
    Sector findByName(String name);
    Sector findById(long sectorId);
    Sector findBy();
    boolean existsSectorById(long id);

    @EntityGraph(attributePaths = "routes")
    Sector findWithRoutesById(long id);
}
