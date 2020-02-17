package progmatic.hegymaszas.repositories;

import progmatic.hegymaszas.modell.Route;

import java.util.List;

public interface RouteRepositoryCustom {
    Route routeWithEverything(long routeId);

    List<Long> idOfMiniImagesOfRoute(long routeId);

    List<Long> get9idOfMiniImagesOfRoute(long routeId);
}
