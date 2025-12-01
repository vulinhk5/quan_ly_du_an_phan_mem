package Dashboard;

import Core.Document;
import Database.DocumentManager;
import Database.TransactionManager;
import SceneManager.SceneManager;
import SessionManager.SessionManager;
import java.util.List;
import java.util.Optional;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class DashboardController {
    public static Document currentChoosingDocument;
    public AnchorPane documentDetails;
    public ImageView documentCover;
    public Label documentTitle;
    public Label documentAuthor;
    public Label documentPublisher;
    public Label documentIsbn;
    public Label documentLanguage;
    public Label documentRating;
    public Label documentPageCount;
    public Label documentAmount;
    public Button borrowButton;
    public VBox documentList;

    private void createButtons(List<Document> documents, int start, int end) {
        for (int i = start; i <= end && i < documents.size(); i++) {
            Document document = documents.get(i);

            // Tạo button
            Button documentButton = new Button();
            documentButton.setStyle("-fx-min-width: 200px;");
            documentButton.getStyleClass().add("document-button");

            VBox vbox = new VBox(5);
            ImageView coverImageView = new ImageView();
            coverImageView.setFitHeight(100);
            coverImageView.setFitWidth(70);
            coverImageView.setImage(new Image(document.getCoverImageUrl()));

            Label titleLabel = new Label(document.getTitle());
            Label authorLabel = new Label(document.getAuthor());
            Label ratingLabel = new Label("Rating: " + document.getAverageRating());

            vbox.getChildren().addAll(coverImageView, titleLabel, authorLabel, ratingLabel);
            documentButton.setGraphic(vbox);

            documentButton.setOnAction(e -> displayDocumentDetails(document));

            Platform.runLater(() -> documentList.getChildren().add(documentButton));
        }
    }

    @FXML
    public void initialize() {
        List<Document> documents = DocumentManager.getDocumentsBorrowedByUser(SessionManager.currentUser);
        borrowButton.setVisible(false);
        currentChoosingDocument = null;

        //createButtons(documents, 0, 4);

        for(int i = 0; i < 200; i++) {
            int finalI = i;
            new Thread(() -> createButtons(documents, finalI * 5, finalI * 5 + 4)).start();
        }
    }

    private void displayDocumentDetails(Document document) {
        currentChoosingDocument = document;
        documentTitle.setText(document.getTitle());
        documentAuthor.setText(document.getAuthor());
        documentPublisher.setText(document.getPublisher());
        documentIsbn.setText("ISBN: " + document.getIsbn());
        documentLanguage.setText("Language: " + document.getLanguage());
        documentRating.setText("Rating: " + document.getAverageRating());
        documentPageCount.setText("Pages: " + document.getPageCount());
        documentAmount.setText("Amount: " + document.getAmount());
        documentCover.setImage(new Image(document.getCoverImageUrl()));

        borrowButton.setVisible(true);
    }
    public void onUpdateUserInfoButtonClicked() {
        SceneManager.getInstance().switchTo(SceneManager.UPDATEUSERINFO);
    }
    public void onUserManagerButtonClicked() {
        SceneManager.getInstance().switchTo(SceneManager.USERMANAGER);
    }

    public void onDocumentManagerButtonClicked() {
        SceneManager.getInstance().switchTo(SceneManager.DOCUMENTMANAGER);
    }
    public void onLogoutButtonClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Logout");
        alert.setHeaderText("Are you sure you want to logout?");
        alert.setContentText("All unsaved progress will be lost.");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.out.println("User confirmed logout.");
            SceneManager.getInstance().switchTo(SceneManager.LOGIN);
        } else {
            System.out.println("User canceled logout.");
        }
    }

    public void onReturnButtonClicked() {
        TransactionManager.returnDocument(SessionManager.currentUser, currentChoosingDocument);
        SceneManager.getInstance().switchTo(SceneManager.DASHBOARD);
    }
}
