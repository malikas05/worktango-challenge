package model;

/**
 * This class is used for directions
 */
public enum Direction {
    NORTH("N"),
    SOUTH("S"),
    WEST("W"),
    EAST("E");

    private String direction;

    Direction(String direction) {
        this.direction = direction;
    }

    public String getDirection() {
        return direction;
    }

    public static Direction fromDirection(String strValue) {
        return fromDirection(strValue, null);
    }

    private static Direction fromDirection(String strValue, Direction defaultEnumValue) {
        if (strValue == null) {
            return defaultEnumValue;
        }

        for (Direction d : values()) {
            if (d.direction.equals(strValue)) {
                return d;
            }
        }

        return defaultEnumValue;
    }
}
