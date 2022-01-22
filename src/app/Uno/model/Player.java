package model;

import java.util.ArrayList;

public class Player {
    private int id;
    private String name;
    private ArrayList<Card> cards;
    public Player(int id, String name){
        cards = new ArrayList<>();
        this.id = id;
        this.name = name;
    }
}
