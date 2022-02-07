package utils;

public class Queries {
    public static final String GET_LAST_ID_CARD =
            "SELECT MAX(id) 'ID' FROM carta";
    public static final String GET_AUTH_USER =
            "SELECT id, nombre FROM jugador WHERE usuario = ? AND password = ?";
    public static final String INSERT_PLAYER =
            "INSERT INTO jugador (usuario, password, nombre, partidas, ganadas) VALUES (?, ?, ?, 0, 0)";
    public static final String GET_ALL_USERS =
            "SELECT id, nombre, partidas, ganadas FROM jugador";
    public static final String INSERT_CARD_OF_PLAYER =
            "INSERT INTO carta (id_jugador, numero, color) VALUES (?, ?, ?)";
    public static final String GET_DECK_OF_ONE_PLAYER =
            "SELECT c.id, c.numero, c.color " +
                    "FROM carta c " +
                    "LEFT JOIN partida p " +
                    "ON c.id = p.id_carta " +
                    "WHERE c.id_jugador = ? AND p.id_carta IS NULL;";
    public static final String GET_LAST_ID_CARD_PLAYED =
            "SELECT max(id) 'id', id_carta FROM partida";
    public static final String GET_CARD_BY_ID =
            "SELECT id, numero, color FROM carta WHERE id = ?";
    public static final String INSERT_CARD_PLAYED =
            "INSERT INTO partida (id_carta) VALUES (?)";
    public static final String DELETE_CONTENT_CARD_TABLE =
            "DELETE FROM card";
    public static final String DELETE_CONTENT_GAME_TABLE =
            "DELETE FROM partida";
    public static final String DELETE_CARD_BY_ID_FROM_GAME =
            "DELETE FROM partida WHERE id_carta = ?";
    public static final String DELETE_CARD_BY_ID_FROM_CARD =
            "DELETE FROM carta WHERE id = ?";
    public static final String INCREMENT_WIN_GAMES_OF_PLAYER_BY_ID =
            "UPDATE jugador SET ganadas = ganadas + 1 WHERE id = ?";
    public static final String INCREMENT_GAMES_OF_ALL_PLAYERS =
            "UPDATE jugador SET partidas = partidas + 1";

}
