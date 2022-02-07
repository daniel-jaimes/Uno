package model;

import java.util.ArrayList;

public class Player {
    private int id;
    private String user;
    private String pass;
    private String name;
    public ArrayList<Card> getCards() {
        return cards;
    }

    private ArrayList<Card> cards;
    public Player(int id, String user){
        this.id = id;
        this.user = user;
        cards = new ArrayList<>();
    }
    public Player(String user, String pass, String name){
        this.user = user;
        this.pass= pass;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }
}
