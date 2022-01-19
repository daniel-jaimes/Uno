package utils;

public class CardIdentifier {
    private Color color;
    private Number number;
    public CardIdentifier(Color color, Number number){
        this.color = color;
        this.number = number;
    }
    public enum Color {
        ROJO, AMARILLO, VERDE, AZUL, NEGRO;
    }
    public enum Number {
        CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO,
        NUEVE, CAMBIO, MASDOS, SALTO, CAMBIOCOLOR, MAS_CUATRO;
    }
}
