package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import progmatic.hegymaszas.modell.Sector;
import progmatic.hegymaszas.services.SectorService;

import java.util.List;

@RestController
@RequestMapping("/rest/sector")
public class RestSectorController {

    private SectorService sectorService;

    @Autowired
    public void MapController(SectorService mapService) {
        this.sectorService = mapService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Sector> allSectors(){
        List<Sector> sectors = sectorService.getAllSector();
        return sectors;
    }




}
