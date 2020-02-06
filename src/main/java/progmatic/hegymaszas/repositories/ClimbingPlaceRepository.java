package progmatic.hegymaszas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import progmatic.hegymaszas.modell.ClimbingPlace;

public interface ClimbingPlaceRepository extends JpaRepository<ClimbingPlace, Long> {
    ClimbingPlace findByName(String name);
}
