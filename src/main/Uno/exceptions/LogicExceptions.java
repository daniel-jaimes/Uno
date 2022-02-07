package exceptions;

import java.util.Arrays;
import java.util.List;

public class LogicExceptions extends Exception{
    public static final int INCORRECT_CREDENTIALS = 0;
    public static final int JUMP_OR_CHANGE = 1;
    private int value;
    private List<String> message = Arrays.asList(
            "<< Credenciales incorrectas >>",
            "<< Se ha lanzado un carta de cambio o de salto para que no juegues. >>"
    );
    public LogicExceptions(int value){
        this.value = value;
    }

    @Override
    public String getMessage() {
        return message.get(value);
    }
}
