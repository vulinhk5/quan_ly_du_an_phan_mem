package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static Database.MySQLManager.startMySQL;
import static Database.MySQLManager.stopMySQL;

public class Database {
    private static Database instance;
    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/library_management_system";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Database() {
        try {
            startMySQL();
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Thread.sleep(500);
            System.out.println("Database connected successfully");
        } catch (Exception e) {
            System.out.println("Can not connect to database.");
        }
    }

    public static Database getInstance() {
        if (instance == null) {
            synchronized (Database.class) {
                if (instance == null) {
                    instance = new Database();
                }
            }
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Attempting to reconnect...");
            try {
                Thread.sleep(500);
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            System.out.println("Connection closed.");
            connection.close();
        }
    }

    public static void main(String[] args) {
        try {
            Database db = Database.getInstance();
            Connection connection = db.getConnection();
            if (connection != null && !connection.isClosed()) {
                System.out.println("Successfully connected to the database.");
            } else {
                System.out.println("Failed to connect to the database.");
            }

            db.closeConnection();
            stopMySQL();
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }
}
