package pl.com.tt.restapp.utils.generators;

import java.util.HashMap;
import java.util.Map;

public enum MovieType {

    ACTION(1),
    SCIFI(2),
    HORROR(3),
    ROMANS(4),
    DRAMA(5),
    ANIMATION(6),
    THRILLER(7),
    WESTERN(8),
    BIOGRAPHY(9),
    EROTIC(10),
    CATASTROPHIC(11),
    COMEDY(12),
    ADVENTURE(13),
    MORAL(14),
    MUSICAL(15),
    DOCUMENT(16),
    FAMILY(17),
    FANTASY(18),
    GORE(19),
    CRIMINAL(20),
    PSYCHOLOGICAL(21),
    ROMANTIC(22);

    private int type;
    private static Map map = new HashMap<>();

    MovieType(int type) {
        this.type = type;
    }

    static {
        for (MovieType movieType : MovieType.values()) {
            map.put(movieType.type, movieType);
        }
    }

    public static MovieType valueOf(int movieType) {
        return (MovieType) map.get(movieType);
    }

    public int getValue() {
        return type;
    }

}
