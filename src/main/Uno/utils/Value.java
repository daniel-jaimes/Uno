package utils;

import java.util.Arrays;
import java.util.List;

public enum Value {
    CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO,
    NUEVE, CAMBIO, MAS_DOS, SALTO, CAMBIO_COLOR, MAS_CUATRO;


    public static Value randomValue() {
        List<Value> VALUES = Arrays.asList(Value.values());
        return VALUES.get((int) (Math.random() * VALUES.size()));
    }
}
