package utils;

import static utils.Color.NEGRO;
import static utils.Value.CAMBIO_COLOR;
import static utils.Value.MAS_CUATRO;

public class CardIdentifier {
    private Color color;
    private Value number;
    public CardIdentifier(Color color, Value number){
        this.color = color;
        this.number = number;
    }
    public static CardIdentifier generateSpecs() {
        Color color;
        Value value;
        do {
            value = Value.randomValue();
            color = Color.randomColor();
        } while(!checkCorrectCard(color, value));
        return new CardIdentifier(color, value);
    }
    private static boolean checkCorrectCard(Color color, Value value){
        return color != NEGRO || value == MAS_CUATRO || value == CAMBIO_COLOR;
    }

    public Color getColor() {
        return color;
    }

    public Value getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "color=" + color +
                ", number=" + number;
    }
}
