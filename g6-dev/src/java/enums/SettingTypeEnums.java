package enums;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author macbook
 */
public enum SettingTypeEnums {
    ROLE,
    PERMISSION,
    SEMESTER,
    SYSTEM,
    ROOM,
    SLOT,
    CATEGORY,
    ALL;

    public static List<String> getValues() {
        List<String> values = new ArrayList<>();

        for (SettingTypeEnums type : SettingTypeEnums.values()) {
            values.add(type.name());
        }

        return values;
    }
}
