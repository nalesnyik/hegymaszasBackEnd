package progmatic.hegymaszas.modell.enums;

import progmatic.hegymaszas.exceptions.WrongAscentTypeException;

public enum AscentType {
    FLASH, ONSIGHT, PROJECT;


    public static AscentType getAscentType(String type) throws WrongAscentTypeException {
        switch (type.toUpperCase()) {
            case "FLASH":
                return AscentType.FLASH;
            case "ONSIGHT":
                return AscentType.ONSIGHT;
            case "PROJECT":
                return AscentType.PROJECT;
            default:
                throw new WrongAscentTypeException();
        }
    }
}
