package progmatic.hegymaszas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import progmatic.hegymaszas.modell.Sector;

public interface SectorRepository extends JpaRepository<Sector, Long> {
    Sector findByName(String name);
    Sector findById(long sectorId);
    Sector findBy();
}
