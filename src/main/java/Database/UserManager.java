package Database;

import Core.Librarian;
import Core.Member;
import Core.User;
import Utility.RandomGenerator;
import Utility.SHA;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserManager {
    public static boolean isUserIdTaken(String userId) {
        String sql = "SELECT COUNT(*) FROM users WHERE userId = ?";

        try (Connection conn = Database.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isUsernameTaken(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        try {
            Connection conn = Database.getInstance().getConnection();

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, username);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isEmailTaken(String email) {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        try {
            Connection conn = Database.getInstance().getConnection();

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, email);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt(1) > 0;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void addUser(User user) {
        String sql = "INSERT INTO users (userId, fullName, username, passwordHash, email, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getUserId());
            stmt.setString(2, user.getFullName());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, SHA.convertToSHA512(user.getPasswordHash()));
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getRole());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User added successfully.");
            } else {
                System.out.println("Failed to add user.");
            }
        } catch (Exception e) {
            System.err.println("Error adding user: " + e.getMessage());
        }
    }

    public static String newUserId() {
        String result = RandomGenerator.generateRandomUserId();
        while (isUserIdTaken(result)) {
            result = RandomGenerator.generateRandomUserId();
        }

        return result;
    }

    private static User resultSetToUser(ResultSet resultSet) {
        try {
            String userId = resultSet.getString("userId");
            String fullName = resultSet.getString("fullName");
            String username = resultSet.getString("username");
            String passwordHash = resultSet.getString("passwordHash");
            String email = resultSet.getString("email");
            String role = resultSet.getString("role");

            if (role.equals(Member.MEMBER)) {
                return new Member(userId, fullName, username, passwordHash, email);
            } else {
                return new Librarian(userId, fullName, username, passwordHash, email);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static User getUser(String username) {
        User user = null;
        String query = "SELECT * FROM users WHERE username = ?";

        try (Connection connection = Database.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = resultSetToUser(resultSet);
                    return user;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static ObservableList<User> getAllUsers() {
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            Connection connection = Database.getInstance().getConnection();
            String query = "SELECT * FROM users";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                userList.add(resultSetToUser(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userList;
    }

    public static void updateUser(User user) {
        String query = "UPDATE users SET fullName = ?, passwordHash = ?, email = ?, role = ? WHERE userId = ?";

        try (Connection connection = Database.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getPasswordHash());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setString(5, user.getUserId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(String userId) {
        String query = "DELETE FROM users WHERE userId = ?";

        try (Connection connection = Database.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userId);
        } catch (SQLException e) {
            System.err.println("Error occurred while deleting user with userId: " + userId);
            e.printStackTrace();
        }
    }
}
