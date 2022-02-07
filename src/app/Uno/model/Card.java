package model;

import utils.Color;
import utils.CardIdentifier;
import utils.Value;

import static utils.Color.*;
import static utils.Value.*;


public class Card {
    public static final Color[] colours = {ROJO, AMARILLO, VERDE, AZUL, NEGRO};
    private static final Value[] values = {
            CERO, UNO, DOS, TRES, CUATRO, CINCO, SEIS, SIETE, OCHO,
            NUEVE, CAMBIO, MAS_DOS, SALTO, CAMBIO_COLOR, MAS_CUATRO
    };
    private int id;
    private CardIdentifier card;

    public Card(int id, CardIdentifier card){
        this.id = id;
        this.card = card;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", card=" + card +
                '}';
    }

    public int getId() {
        return id;
    }

    public CardIdentifier getCard() {
        return card;
    }
}
