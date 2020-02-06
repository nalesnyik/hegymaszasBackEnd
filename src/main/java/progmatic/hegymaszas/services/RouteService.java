package progmatic.hegymaszas.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import progmatic.hegymaszas.dto.RouteDto;
import progmatic.hegymaszas.modell.Route;
import progmatic.hegymaszas.repositories.RouteRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class RouteService {

    @PersistenceContext
    EntityManager em;

    private final RouteRepository routeRepository;

    @Autowired
    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }


    @Transactional
    public List<Route> getAllRoute(){
        return em.createQuery("SELECT r from Route r", Route.class).getResultList();
        //return routeRepository.findAll();

    }
}
