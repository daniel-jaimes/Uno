package model;

import utils.Color;
import utils.CardIdentifier;
import utils.Value;

import static utils.Color.*;
import static utils.Value.*;


public class Card {
    private static final Color[] colours = {ROJO, AMARILLO, VERDE, AZUL, NEGRO};
    private static final Value[] values = {
            CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO,
            NUEVE, CAMBIO, MAS_DOS, SALTO, CAMBIO_COLOR, MAS_CUATRO
    };
    private int id;
    private CardIdentifier card;

    private Card(int id, CardIdentifier card){
        this.id = id;
        this.card = card;
    }
    public static Card getNewCard(int id){
        Color color;
        Value value;
        do {
            value = values[(int) (Math.random() * values.length)];
            color = colours[(int) (Math.random() * colours.length)];
        } while(checkCorrectCard(color, value));
        return new Card(id, new CardIdentifier(color, value));
    }

    private static boolean checkCorrectCard(Color color, Value value){
        return color == NEGRO && value != MAS_CUATRO || value != CAMBIO_COLOR;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", card=" + card +
                '}';
    }
}
