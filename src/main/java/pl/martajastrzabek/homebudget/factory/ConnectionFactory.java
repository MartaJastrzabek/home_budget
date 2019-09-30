package pl.martajastrzabek.homebudget.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = ("jdbc:mysql://localhost:3306/home_budget?characterEncoding=utf8&UTC&useSSLfalse");
        String username = "root";
        String password = "********";

        return DriverManager.getConnection(url, username, password);
    }
}
