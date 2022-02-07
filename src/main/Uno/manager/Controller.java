package manager;

import dao.ServiceDB;
import exceptions.ExecutionException;
import exceptions.LogicExceptions;
import model.Card;
import model.Player;
import utils.Color;
import utils.Value;


import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller {
    ServiceDB serviceDB;
    Player player;
    Card lastCard;
    public synchronized void init() {
        serviceDB = new ServiceDB();
        try {
            uploadPlayersIfNotExist();
            Scanner sc = new Scanner(System.in);
            optionalSignIn(sc);
            logIn(sc);
            turn();
            sc.close();
        } catch (ExecutionException | LogicExceptions e) {
            System.out.println(e.getMessage());
        }
    }

    private void optionalSignIn(Scanner sc) throws ExecutionException {
        boolean correctSelection;
        do {
            System.out.print("Quieres registrar a un nuevo usuario (s/n): ");
            String select = sc.next();
            switch(select){
                case "s":
                    signIn(sc);
                    correctSelection = true;
                    break;
                case "n":
                    correctSelection = true;
                    break;
                default:
                    correctSelection = false;
                    break;
            }
            if(!correctSelection) {
                System.out.println("INCORRECT SELECTION CARD!\nSELECT ANOTHER TIME!");
            }
        } while (!correctSelection);
    }

    private void signIn(Scanner sc) throws ExecutionException {
        String user, pass, name;
        System.out.print("User: ");
        user = sc.next();
        System.out.print("Password: ");
        pass = sc.next();
        System.out.print("Name: ");
        name = sc.next();
        serviceDB.signIn(new Player(user, pass, name));
    }

    private void turn() throws LogicExceptions, ExecutionException {
        getLastCardPlayed();
        checkLastCard();
        checkDeck();
        selectCardToThrow();
        checkIfEmptyDeck();
    }

    private void checkIfEmptyDeck() throws ExecutionException {
        if(player.getCards().size() == 0){
            incrementWinGamesOfPlayerById(player.getId());
            incrementGamesOfAllPlayers();
            resetGame();
            resetCards();
        }
    }

    private void resetCards() throws ExecutionException {
        serviceDB.resetCards();
    }

    private void resetGame() throws ExecutionException {
        serviceDB.resetGame();
    }

    private void incrementGamesOfAllPlayers() throws ExecutionException {
        serviceDB.incrementGamesOfAllPlayers();
    }

    private void incrementWinGamesOfPlayerById(int id) throws ExecutionException {
        serviceDB.incrementWinGamesOfPlayerById(id);
    }

    private void checkLastCard() throws LogicExceptions, ExecutionException {
        if(lastCard != null) checkNumberCard();
    }

    private void selectCardToThrow() throws ExecutionException {
        Scanner sc = new Scanner(System.in);
        boolean correctCardSelected;
        int selection;
        do {
            do {
                showDeck();
                System.out.print("Que carta quieres lanzar? (-1 : Robar): ");
                selection = sc.nextInt();
                correctCardSelected = selection == -1 ||
                        selection > 0 && selection <= player.getCards().size();
                if(!correctCardSelected) {
                    System.out.println("INCORRECT SELECTION CARD!\nSELECT ANOTHER TIME!");
                }
            }while (!correctCardSelected);
            if(selection > 0){
                correctCardSelected = checkCorrectCardToPlay(selection);
            } else {
                correctCardSelected = false;
                addStealCard();
                System.out.println(
                        "Robaste: " +
                                player.getCards().get(player.getCards().size() - 1).toString()
                );
                checkDeck();
            }
        } while (!correctCardSelected);
    }

    private boolean checkCorrectCardToPlay(int selection) throws ExecutionException {
        Card cardSelected = player.getCards().get(selection - 1);
        if(lastCard == null ||
                lastCard.getCard().getColor() == cardSelected.getCard().getColor() ||
                lastCard.getCard().getColor() == Color.NEGRO ||
                cardSelected.getCard().getColor() == Color.NEGRO
        )
        {
            serviceDB.playCard(cardSelected.getId());
            player.getCards().remove(cardSelected);
            return true;
        } else {
            System.out.println("NO ES CORRECTA LA CARTA!");
            return false;
        }
    }

    private void checkNumberCard() throws LogicExceptions, ExecutionException {
        switch(lastCard.getCard().getNumber()){
            case MAS_DOS:
                for (int i = 0; i < 2; i++) {
                    addStealCard();
                }
                break;
            case MAS_CUATRO:
                for (int i = 0; i < 4; i++) {
                    addStealCard();
                }
                break;
            case SALTO:
            case CAMBIO:
                deleteCardById(lastCard.getId());
                throw new LogicExceptions(LogicExceptions.JUMP_OR_CHANGE);
        }
    }

    private void deleteCardById(int id) throws ExecutionException {
        serviceDB.deleteCardPlayedById(id);
        serviceDB.deleteCardOfDeckById(id);
    }

    private void showDeck() {
        System.out.println("TUS CARTAS: ");
        AtomicInteger i = new AtomicInteger(1);
        player.getCards().forEach(c -> {
            System.out.println(i + " - " + c.toString());
            i.getAndIncrement();
        });
    }

    private void getLastCardPlayed() throws ExecutionException {
        lastCard = serviceDB.getLastCardPlayed();
        if(lastCard != null) System.out.println("LA UTLIMA CARTA JUGADA ES: " + lastCard.toString());
        else System.out.println("NO HAY CARTA EN EL MONTON!");
    }

    private void checkDeck() throws ExecutionException {
        ArrayList<Card> deck;
        if ((deck = serviceDB.getDeckOfOnePlayerById(player.getId())).size() != 0) {
            getDeck(deck);
        } else {
            stealInitalCards();
        }
    }

    private void getDeck(ArrayList<Card> deck) {
        player.getCards().clear();
        deck.forEach(card -> player.getCards().add(card));
    }

    private void stealInitalCards() throws ExecutionException {
        for (int i = 0; i < 7; i++) {
            addStealCard();
        }
        checkDeck();
    }

    private void logIn(Scanner sc) throws LogicExceptions, ExecutionException {
        String[] credentials = askCredentials(sc);
        player = checkCorrectCredentials(credentials[0], credentials[1]);
    }

    private String[] askCredentials(Scanner sc) {
        String user = "", password = "";
        System.out.print("Inicio de sesion\nUser: ");
        user = sc.next();
        System.out.print("Password: ");
        password = sc.next();
        return new String[]{user, password};
    }

    private Player checkCorrectCredentials(String user, String password)
            throws LogicExceptions, ExecutionException {
        Player player;
        if ((player = serviceDB.getAuthPlayer(user, password)) == null) {
            throw new LogicExceptions(LogicExceptions.INCORRECT_CREDENTIALS);
        }
        return player;
    }

    //INSERT PLAYERS
    private void uploadPlayersDB() throws ExecutionException {
        serviceDB.insertNewPlayers();
    }

    private void uploadPlayersIfNotExist() throws ExecutionException {
        if(!serviceDB.checkUserExistInDB()) uploadPlayersDB();
    }

    private void addStealCard() throws ExecutionException {
        serviceDB.setNewCard(player.getId());
    }
}