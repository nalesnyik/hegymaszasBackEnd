package progmatic.hegymaszas.repositories;

import java.util.List;

public interface SectorRepositoryCustom {
    long getMiniProfileId(long sectorId);

    long getProfileId(long sectorId);

    List<Long> get9idOfMiniImagesOfSector(long sectorId);

    List<Long> idOfMiniImagesOfSector(long sectorId);
}
