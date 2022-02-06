package manager;

import exceptions.ExecutionException;
import model.Player;

import java.sql.SQLException;
import java.util.Scanner;

public class Controller {
    Service service;
    Player player;
    public synchronized void init() {
        service = new Service();
        try {
            uploadPlayersIfNotExist();
            signIn();
        } catch (SQLException | ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void signIn() throws SQLException {
        Scanner sc = new Scanner(System.in);
        boolean correctAuthentication;
        do {
            String user = sc.next();
            String password = sc.next();
            player = service.getAuthPlayer(user, password);
            correctAuthentication = null == player;
            if (!correctAuthentication) {
                System.out.println("INCORRECT CREDENTIALS!!!");
            }
        } while (correctAuthentication);
    }
    private void uploadPlayersDB() throws SQLException, ExecutionException {
        service.insertNewPlayers();
    }

    private void uploadPlayersIfNotExist() throws SQLException, ExecutionException {
        if(!service.checkUserExistInDB()) uploadPlayersDB();
    }
}