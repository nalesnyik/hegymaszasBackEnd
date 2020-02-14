package progmatic.hegymaszas.dto;

import progmatic.hegymaszas.modell.enums.AscentType;
import progmatic.hegymaszas.modell.messages.ClimbingLog;

public class ClimbingLogShowDto extends MessageShowDto {
    private AscentType type;
    private long routeId;


    public ClimbingLogShowDto() {
    }


    public ClimbingLogShowDto(ClimbingLog climbingLog) {
        id = climbingLog.getId();
        routeId = climbingLog.getRoute().getId();
        text = climbingLog.getText();
        creationDate = climbingLog.getCreationDate();
        username = climbingLog.getUser().getName();
        type = climbingLog.getType();
    }


    public AscentType getType() {
        return type;
    }


    public void setType(AscentType type) {
        this.type = type;
    }


    public long getRouteId() {
        return routeId;
    }


    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }
}
