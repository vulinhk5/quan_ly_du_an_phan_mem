package Database;

import static Utility.AlertHelper.showAlert;

import Core.Document;
import Core.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javafx.scene.control.Alert;

public class TransactionManager {
    public static void borrowDocument(User user, Document document) {
        Connection connection = null;
        PreparedStatement updateDocumentStmt = null;
        PreparedStatement insertTransactionStmt = null;

        try {
            connection = Database.getInstance().getConnection();

            if (document.getAmount() <= 0) {
                showAlert(Alert.AlertType.WARNING, "Unavailable", "Document is not available for borrowing.");
                return;
            }

            String updateDocumentQuery = "UPDATE documents SET amount = amount - 1 WHERE documentId = ?";
            updateDocumentStmt = connection.prepareStatement(updateDocumentQuery);
            updateDocumentStmt.setString(1, document.getDocumentId());
            updateDocumentStmt.executeUpdate();

            String insertTransactionQuery = "INSERT INTO transactions (userId, documentId, status, transactionDate) VALUES (?, ?, ?, ?)";
            insertTransactionStmt = connection.prepareStatement(insertTransactionQuery);

            String status = "borrowed"; // Trạng thái

            insertTransactionStmt.setString(1, user.getUserId());
            insertTransactionStmt.setString(2, document.getDocumentId());
            insertTransactionStmt.setString(3, status);
            insertTransactionStmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            insertTransactionStmt.executeUpdate();

            // Hiển thị thông báo thành công
            showAlert(Alert.AlertType.INFORMATION, "Success", "Document borrowed successfully!");

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while borrowing the document.");
            e.printStackTrace();
        } finally {
            try {
                if (updateDocumentStmt != null) updateDocumentStmt.close();
                if (insertTransactionStmt != null) insertTransactionStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void returnDocument(User user, Document document) {
        Connection connection = null;
        PreparedStatement updateDocumentStmt = null;
        PreparedStatement updateTransactionStmt = null;

        try {
            connection = Database.getInstance().getConnection();

            String checkTransactionQuery = "SELECT * FROM transactions WHERE userId = ? AND documentId = ? AND status = 'Borrowed'";
            PreparedStatement checkTransactionStmt = connection.prepareStatement(checkTransactionQuery);
            checkTransactionStmt.setString(1, user.getUserId());
            checkTransactionStmt.setString(2, document.getDocumentId());

            ResultSet rs = checkTransactionStmt.executeQuery();
            if (!rs.next()) {
                showAlert(Alert.AlertType.WARNING, "Not Borrowed", "This document was not borrowed by the user.");
                return;
            }

            String updateDocumentQuery = "UPDATE documents SET amount = amount + 1 WHERE documentId = ?";
            updateDocumentStmt = connection.prepareStatement(updateDocumentQuery);
            updateDocumentStmt.setString(1, document.getDocumentId());
            updateDocumentStmt.executeUpdate();

            String updateTransactionQuery = "UPDATE transactions SET status = 'Returned' WHERE userId = ? AND documentId = ? AND status = 'Borrowed'";
            updateTransactionStmt = connection.prepareStatement(updateTransactionQuery);
            updateTransactionStmt.setString(1, user.getUserId());
            updateTransactionStmt.setString(2, document.getDocumentId());
            updateTransactionStmt.executeUpdate();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Document returned successfully!");

        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while returning the document.");
            e.printStackTrace();
        } finally {
            try {
                if (updateDocumentStmt != null) updateDocumentStmt.close();
                if (updateTransactionStmt != null) updateTransactionStmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
