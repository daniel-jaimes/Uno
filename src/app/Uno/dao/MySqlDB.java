package dao;

import java.sql.*;

import static utils.Queries.*;

public class MySqlDB {
    private Connection conexion;
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
        conexion = DriverManager.getConnection(url, user, passwd);
    }

    public void disconnect() throws SQLException {
        if (conexion != null) {
            conexion.close();
        }
    }

    public int getLastIdCard() throws SQLException {
        try (Statement st = conexion.createStatement()){
            try (ResultSet rs = st.executeQuery(GET_LAST_ID_CARD)){
                return rs != null ? 1 : rs.getInt(1);
            }
        }
    }
    public void insertNewPlayer(String[] player) throws SQLException {
        try (PreparedStatement ps = conexion.prepareStatement(INSERT_PLAYER)){
            ps.setString(1, player[0]);
            ps.setString(2, player[1]);
            ps.setString(3, player[2]);
            ps.executeUpdate();
        }
    }
    public boolean userExist() throws SQLException {
        try (Statement st = conexion.createStatement()){
            try (ResultSet rs = st.executeQuery(GET_ALL_USERS)){
                return rs != null;
            }
        }
    }


}
