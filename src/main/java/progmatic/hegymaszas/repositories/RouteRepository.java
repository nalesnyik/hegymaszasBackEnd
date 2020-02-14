package progmatic.hegymaszas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.modell.Sector;

public interface RouteRepository extends JpaRepository<Route, Long>, RouteRepositoryCustom {

    Route findById(long routeId);

    boolean existsRouteBySectorAndName(Sector sector, String routeName);

    boolean existsRouteById(long routeId);
}
