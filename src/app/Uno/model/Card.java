package model;

import utils.CardIdentifier;

public class Card {
    private int id;
    private CardIdentifier value;
    public Card(int id, CardIdentifier value){
        this.id = id;
        this.value = value;
    }
}
