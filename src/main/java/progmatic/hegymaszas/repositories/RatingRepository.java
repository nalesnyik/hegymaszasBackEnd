package progmatic.hegymaszas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import progmatic.hegymaszas.modell.Rating;

public interface RatingRepository extends JpaRepository<Rating,Long>, RatingRepositoryCustom {
}
