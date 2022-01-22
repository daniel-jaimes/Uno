package manager;

import dao.MySqlDB;
import dao.Reader;
import exceptions.ExecutionException;
import model.Card;
import model.Player;

import java.sql.SQLException;

public class Service {
    private MySqlDB mysqlDB;
    public Service(){
        mysqlDB = new MySqlDB();
    }

    protected void stealCard() throws SQLException {
        mysqlDB.connect();
        int lastIdCard = mysqlDB.getLastIdCard();
        Card card = Card.getNewCard(lastIdCard + 1);
        mysqlDB.disconnect();
    }

    protected Player getAuthPlayer(String user, String passwd) throws SQLException {
        Player player = null;
        mysqlDB.connect();
        //
        mysqlDB.disconnect();
        return player;
    }
    protected void insertNewPlayers() throws ExecutionException, SQLException {
        Reader rd = new Reader("creationPlayers");
        mysqlDB.connect();
        String line;
        while(!(line = rd.readLine()).equals("")){
            mysqlDB.insertNewPlayer(line.split(";"));
        }
        rd.close();
        mysqlDB.disconnect();
    }
    protected boolean checkUserExistInDB() throws SQLException {
        mysqlDB.connect();
        boolean userExists = mysqlDB.userExist();
        mysqlDB.disconnect();
        return userExists;
    }
}
