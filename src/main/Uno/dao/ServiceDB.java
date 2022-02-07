package dao;

import exceptions.ExecutionException;
import model.Card;
import model.Player;
import utils.CardIdentifier;

import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceDB {
    private MySqlDB mysqlDB;
    public ServiceDB(){
        mysqlDB = new MySqlDB();
    }

    public int getLastIdCard() throws ExecutionException {
        int lastIdCard;
        try {
            mysqlDB.connect();
            lastIdCard = mysqlDB.getLastIdCard();
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }
        return lastIdCard;

    }
    public void setNewCard(int id_player) throws ExecutionException {
        CardIdentifier ci = CardIdentifier.generateSpecs();
        try {
            mysqlDB.connect();
            mysqlDB.recordCard(ci, id_player);
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }

    }

    public ArrayList<Card> getDeckOfOnePlayerById(int id) throws ExecutionException {
        ArrayList<Card> deck;
        try {
            mysqlDB.connect();
            deck = mysqlDB.getDeckOfOnePlayerById(id);
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }
        return deck;
    }


    public Player getAuthPlayer(String user, String passwd) throws ExecutionException {
        Player player;
        try {
            mysqlDB.connect();
            player = mysqlDB.getAuthPlayer(user, passwd);
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }
        return player;
    }
    public void insertNewPlayers() throws ExecutionException {
        Reader rd = new Reader("creationPlayers");
        String line;
        System.out.println("REGISTRANDO JUGADORES...");
        try {
            mysqlDB.connect();
            while(!(line = rd.readLine()).equals("")){
                mysqlDB.insertNewPlayer(line.split(";"));
            }
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }
        rd.close();

    }
    public boolean checkUserExistInDB() throws ExecutionException {
        boolean userExists;
        try {
            mysqlDB.connect();
            userExists = mysqlDB.usersExist();
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }
        return userExists;
    }

    public Card getLastCardPlayed() throws ExecutionException {
        Card card;
        try {
            mysqlDB.connect();
            card = mysqlDB.getLastCardPlayed();
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }
        return card;
    }

    public void playCard(int id_card) throws ExecutionException {
        try {
            mysqlDB.connect();
            mysqlDB.insertCardToPlay(id_card);
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }
    }

    public void deleteCardOfDeckById(int id) throws ExecutionException {
        try {
            mysqlDB.connect();
            mysqlDB.deleteCardOfDeckById(id);
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }
    }
    public void deleteCardPlayedById(int id_card) throws ExecutionException {
        try {
            mysqlDB.connect();
            mysqlDB.deleteCardPlayedById(id_card);
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }

    }

    public void incrementWinGamesOfPlayerById(int id) throws ExecutionException {
        try {
            mysqlDB.connect();
            mysqlDB.incrementWinGamesOfPlayerById(id);
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }
    }

    public void incrementGamesOfAllPlayers() throws ExecutionException {
        try {
            mysqlDB.connect();
            mysqlDB.incrementGamesOfAllPlayers();
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }
    }

    public void resetGame() throws ExecutionException {
        try {
            mysqlDB.connect();
            mysqlDB.resetGameTable();
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }
    }

    public void resetCards() throws ExecutionException {
        try {
            mysqlDB.connect();
            mysqlDB.resetCardTable();
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }
    }

    public void signIn(Player newPlayer) throws ExecutionException {
        try {
            mysqlDB.connect();
            mysqlDB.signNewPlayer(newPlayer);
            mysqlDB.disconnect();
        } catch (SQLException e) {
            throw new ExecutionException(ExecutionException.ERROR_DB);
        }
    }
}
