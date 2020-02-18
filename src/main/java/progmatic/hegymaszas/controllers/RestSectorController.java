package progmatic.hegymaszas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import progmatic.hegymaszas.modell.Sector;
import progmatic.hegymaszas.services.SectorService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/sector")
public class RestSectorController {

    private SectorService sectorService;

    @Autowired
    public RestSectorController(SectorService sectorServiceService) {
        this.sectorService = sectorServiceService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Sector> allSectors(){
        List<Sector> sectors = sectorService.getAllSector();
        return sectors;
    }




}
