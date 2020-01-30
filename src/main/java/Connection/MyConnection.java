package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

    private static String url = new StringBuilder()
            .append("jdbc:")
            .append("postgresql")
            .append("://")
            .append("localhost")
            .append(":")
            .append(5432)
            .append("/")
            .append("postgres")
            .append("?sciHomeWork=")
            .append("sci")
            .append("&user=")
            .append("admin")
            .append("&password=")
            .append("admin").toString();


    public static Connection getConnection() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Connection connection = null;
        try {
            try {
                Class.forName("org.postgresql.Driver").newInstance();
                DriverManager.setLoginTimeout(60);
            } catch (Exception e) {
                System.out.println(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection = DriverManager.getConnection(url);
        return connection;
    }
}
