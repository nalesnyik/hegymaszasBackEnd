package progmatic.hegymaszas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import progmatic.hegymaszas.modell.ClimbingPlace;

@Repository
public interface ClimbingRepository extends JpaRepository<ClimbingPlace, Long>, ClimbingRepositoryCustom {
    ClimbingPlace findByName(String name);
}
