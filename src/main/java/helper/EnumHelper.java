package helper;

public class EnumHelper {
    private EnumHelper() {
    }

    public static <T extends Enum<T>> T fromString(Class<T> clazz, String strValue, T defaultEnumValue) {
        if (strValue == null) {
            return defaultEnumValue;
        }
        for (T enumVal : clazz.getEnumConstants()) {
            if (enumVal.name().equalsIgnoreCase(strValue)) {
                return enumVal;
            }
        }

        return defaultEnumValue;
    }
}
