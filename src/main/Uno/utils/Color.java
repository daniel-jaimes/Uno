package utils;

import java.util.Arrays;
import java.util.List;

public enum Color {
    ROJO, AMARILLO, VERDE, AZUL, NEGRO;

    static Color randomColor() {
        List<Color> VALUES = Arrays.asList(Color.values());
        return VALUES.get((int) (Math.random() * VALUES.size()));
    }
}
