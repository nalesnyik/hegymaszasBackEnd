package progmatic.hegymaszas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progmatic.hegymaszas.modell.ClimbingPlace;

//törlendő field
@Repository
public interface ClimbingPlaceRepository extends JpaRepository<ClimbingPlace, Long> {
    ClimbingPlace findByName(String name);
}
