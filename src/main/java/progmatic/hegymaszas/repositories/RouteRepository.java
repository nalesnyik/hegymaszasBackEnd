package progmatic.hegymaszas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import progmatic.hegymaszas.modell.Route;

public interface RouteRepository extends JpaRepository<Route, Long> {

    Route findById(long routeId);

}
