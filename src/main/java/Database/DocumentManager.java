package Database;

import Core.Document;
import Core.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DocumentManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_management_system";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    public static List<Document> getDocumentsFromDatabase() {
        List<Document> documentList = new ArrayList<>();
        String sql = "SELECT * FROM documents LIMIT 1000";

        try (Connection connection = Database.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                String documentId = resultSet.getString("documentId");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher");
                String description = resultSet.getString("description");
                String isbn = resultSet.getString("isbn");
                String language = resultSet.getString("language");
                int pageCount = resultSet.getInt("pageCount");
                String coverImageUrl = resultSet.getString("coverImageUrl");
                double averageRating = resultSet.getDouble("averageRating");
                int amount = resultSet.getInt("amount");

                Document document = new Document(documentId, title, author, publisher, description, isbn,
                    language, pageCount, coverImageUrl, averageRating, amount);
                documentList.add(document);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return documentList;
    }

    public void updateDocument(Document document) {
        String sql = "UPDATE documents " +
            "SET title = ?, author = ?, publisher = ?, isbn = ?, language = ?, " +
            "    pageCount = ?, coverImageUrl = ?, averageRating = ?, amount = ? " +
            "WHERE documentId = ?";

        try (Connection conn = Database.getInstance().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set the parameters for the SQL query using the Document object
            stmt.setString(1, document.getTitle());
            stmt.setString(2, document.getAuthor());
            stmt.setString(3, document.getPublisher());
            stmt.setString(4, document.getIsbn());
            stmt.setString(5, document.getLanguage());
            stmt.setInt(6, document.getPageCount());
            stmt.setString(7, document.getCoverImageUrl());
            stmt.setDouble(8, document.getAverageRating());
            stmt.setInt(9, document.getAmount());
            stmt.setString(10, document.getDocumentId());

            // Execute the update
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Document updated successfully.");
            } else {
                System.out.println("No document found with the provided documentId.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while updating the document: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        List<Document> documents = getDocumentsFromDatabase();
        System.out.println("Total documents fetched: " + documents.size());

        for (int i = 0; i < Math.min(10, documents.size()); i++) {
            System.out.println(documents.get(i));
        }
    }

    public static List<Document> getDocumentsBorrowedByUser(User user) {
        List<Document> documents = new ArrayList<>();
        String query = "SELECT d.documentId, d.title, d.author, d.publisher, d.description, d.isbn, d.language, " +
            "d.pageCount, d.coverImageUrl, d.averageRating, d.amount " +
            "FROM documents d " +
            "JOIN transactions br ON d.documentId = br.documentId " +
            "WHERE br.userId = ? AND br.status = 'Borrowed'";

        try (Connection conn = Database.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(query)) {

            // Sử dụng userId từ đối tượng User
            ps.setString(1, user.getUserId());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String documentId = rs.getString("documentId");
                    String title = rs.getString("title");
                    String author = rs.getString("author");
                    String publisher = rs.getString("publisher");
                    String description = rs.getString("description");
                    String isbn = rs.getString("isbn");
                    String language = rs.getString("language");
                    int pageCount = rs.getInt("pageCount");
                    String coverImageUrl = rs.getString("coverImageUrl");
                    double averageRating = rs.getDouble("averageRating");
                    int amount = rs.getInt("amount");

                    // Tạo đối tượng Document và thêm vào danh sách
                    Document document = new Document(documentId, title, author, publisher, description, isbn, language,
                        pageCount, coverImageUrl, averageRating, amount);
                    documents.add(document);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return documents;
    }
}
