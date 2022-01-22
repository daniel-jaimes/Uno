package utils;

public class Queries {
    public static final String GET_LAST_ID_CARD =
            "SELECT MAX(id) 'ID' FROM carta";
    public static final String GET_AUTH_USER =
            "SELECT id, nombre, partidas, ganadas FROM jugador WHERE usuario = ? AND password = ?";
    public static final String INSERT_PLAYER =
            "INSERT INTO jugador (usuario, password, nombre, partidas, ganadas) VALUES (?, ?, ?, 0, 0)";
    public static final String GET_ALL_USERS =
            "SELECT id, nombre, partidas, ganadas FROM jugador";
    public static final String INSERT_CARD =
            "INSERT INTO carta (id_jugador, numero, color) VALUES (?, ?, ?)";
}
