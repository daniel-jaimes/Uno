package dao;

import model.Card;
import model.Player;
import utils.CardIdentifier;
import utils.Color;
import utils.Value;

import java.sql.*;
import java.util.ArrayList;

import static utils.Queries.*;

public class MySqlDB {
    private Connection connection;
    private static final String SCHEMA_NAME = "dam2tm06uf2p1";
    private static final String CONNECTION =
            "jdbc:mysql://localhost:3306/" +
                    SCHEMA_NAME +
                    "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USER_CONNECTION = "root";
    private static final String PASS_CONNECTION = "DAM2T_M03";

    public void connect() throws SQLException {
        String url = CONNECTION;
        String user = USER_CONNECTION;
        String passwd = PASS_CONNECTION;
        connection = DriverManager.getConnection(url, user, passwd);
    }

    public void disconnect() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public int getLastIdCard() throws SQLException {
        int id = 0;
        try (Statement st = connection.createStatement()){
            try (ResultSet rs = st.executeQuery(GET_LAST_ID_CARD)){
                while(rs.next()){
                    id = rs.getInt("ID");
                }
            }
        }
        return id;
    }
    public void insertNewPlayer(String[] player) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_PLAYER)){
            ps.setString(1, player[0]);
            ps.setString(2, player[1]);
            ps.setString(3, player[2]);
            ps.executeUpdate();
        }
    }
    public boolean usersExist() throws SQLException {
        try (Statement st = connection.createStatement()){
            try (ResultSet rs = st.executeQuery(GET_ALL_USERS)){
                return rs.next();
            }
        }
    }

    public Player getAuthPlayer(String user, String passwd) throws SQLException {
        Player player = null;
        try(PreparedStatement ps = connection.prepareStatement(GET_AUTH_USER)){
            ps.setString(1, user);
            ps.setString(2, passwd);
            try(ResultSet rs = ps.executeQuery()){
                while(rs.next()) {
                    player = new Player(
                            rs.getInt("id"),
                            rs.getString("nombre")
                    );
                }
            }
        }
        return player;
    }
    public ArrayList<Card> getDeckOfOnePlayerById(int id) throws SQLException {
        ArrayList<Card> deck = new ArrayList<>();
        try(PreparedStatement ps = connection.prepareStatement(GET_DECK_OF_ONE_PLAYER)){
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    deck.add(new Card(
                            rs.getInt("id"),
                            new CardIdentifier(
                                    Color.valueOf(rs.getString("color")),
                                    Value.valueOf(rs.getString("numero"))
                            )
                    ));
                }
            }
        }
        return deck;
    }

    public void recordCard(CardIdentifier card, int id_player) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_CARD_OF_PLAYER)){
            ps.setInt(1, id_player);
            ps.setString(2, card.getNumber().name());
            ps.setString(3, card.getColor().name());
            ps.executeUpdate();
        }
    }

    public Card getLastCardPlayed() throws SQLException {
        Card card = null;
        Integer idLastCardPlayed;
        if((idLastCardPlayed = getLastIdCardPlayed()) != null){
            try(PreparedStatement ps = connection.prepareStatement(GET_CARD_BY_ID)){
                ps.setInt(1, idLastCardPlayed);
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()) {
                        card = new Card(
                                rs.getInt("id"),
                                new CardIdentifier(
                                        Color.valueOf(rs.getString("color")),
                                        Value.valueOf(rs.getString("numero"))
                                )
                        );
                    }
                }
            }
        }
        return card;
    }
    private Integer getLastIdCardPlayed() throws SQLException {
        Integer lastIdCardPlayed = null;
        try (Statement st = connection.createStatement()){
            try (ResultSet rs = st.executeQuery(GET_LAST_ID_CARD_PLAYED)){
                if(rs.next()){
                    lastIdCardPlayed = rs.getInt("id_carta");
                }
            }
        }
        return lastIdCardPlayed;
    }

    public void deleteCardPlayedById(int id_card) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(DELETE_CARD_BY_ID_FROM_GAME)){
            ps.setInt(1, id_card);
            ps.executeUpdate();
        }
    }

    public void insertCardToPlay(int id_card) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(INSERT_CARD_PLAYED)){
            ps.setInt(1, id_card);
            ps.executeUpdate();
        }
    }

    public void deleteCardOfDeckById(int id) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(DELETE_CARD_BY_ID_FROM_CARD)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void incrementWinGamesOfPlayerById(int id) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(INCREMENT_WIN_GAMES_OF_PLAYER_BY_ID)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void incrementGamesOfAllPlayers() throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(INCREMENT_GAMES_OF_ALL_PLAYERS)){
            ps.executeUpdate();
        }
    }

    public void resetGameTable() throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(DELETE_CONTENT_GAME_TABLE)){
            ps.executeUpdate();
        }
    }

    public void resetCardTable() throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(DELETE_CONTENT_CARD_TABLE)){
            ps.executeUpdate();
        }
    }

    public void signNewPlayer(Player newPlayer) throws SQLException {
        try(PreparedStatement ps = connection.prepareStatement(INSERT_PLAYER)){
            ps.setString(1, newPlayer.getUser());
            ps.setString(2, newPlayer.getPass());
            ps.setString(3, newPlayer.getName());
            ps.executeUpdate();
        }
    }
}
