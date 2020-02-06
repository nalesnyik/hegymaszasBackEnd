package progmatic.hegymaszas.repositoryes;

import progmatic.hegymaszas.modell.ClimbingPlace;
import progmatic.hegymaszas.modell.Sector;

import java.util.List;

public interface ClimbingRepositoryCustom {
    List<ClimbingPlace> getClimbingPlaces();

    int getNumOfRoutesOfClimbingPlace(long climbingPlaceId);

    int getNumOfFeedbacksOfClimbingPlace(long climbingPlaceId);

    List<Sector> getSectorsOfClimbingPlace(long idOfClimbingPlace);

    int getNumOfRoutesOfSector(long sectorId);

    int getNumOfFeedbacksOfSector(long sectorId);
}
