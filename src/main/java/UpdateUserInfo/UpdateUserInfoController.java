package UpdateUserInfo;

import Core.User;
import SceneManager.SceneManager;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import static SessionManager.SessionManager.currentUser;
import static Utility.AlertHelper.showAlert;
import static Utility.SHA.convertToSHA512;
import static Database.UserManager.*;

public class UpdateUserInfoController {
    @FXML
    public TextField userId;
    @FXML
    public TextField fullName;
    @FXML
    public TextField userName;
    @FXML
    public TextField passwordHash;
    @FXML
    public TextField email;
    @FXML
    public TextField role;
    public PasswordField oldPassword;
    public PasswordField newPassword;
    public PasswordField reNewPassword;

    public void initialize() {
        userId.setText(currentUser.getUserId());
        fullName.setText(currentUser.getFullName());
        userName.setText(currentUser.getUsername());
        passwordHash.setText(currentUser.getPasswordHash());
        email.setText(currentUser.getEmail());
        role.setText(currentUser.getRole());
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

    public void onDashboardButtonClicked() {
        SceneManager.getInstance().switchTo(SceneManager.DASHBOARD);
    }

    public void onUserManagerButtonClicked() {
        SceneManager.getInstance().switchTo(SceneManager.USERMANAGER);
    }

    public void onDocumentManagerButtonClicked() {
        SceneManager.getInstance().switchTo(SceneManager.DOCUMENTMANAGER);
    }

    public void onChangePasswordButtonClicked() {
        if (!(convertToSHA512(oldPassword.getText()).equals(currentUser.getPasswordHash()))) {
            showAlert(AlertType.ERROR, "Password Incorrect", "Incorrect old password.");
            return;
        }

        if (!(newPassword.getText().equals(reNewPassword.getText()))) {
            showAlert(AlertType.ERROR, "Password Mismatch", "New passwords do not match.");
            return;
        }

        currentUser.setPasswordHash(newPassword.getText());
        updateUser(currentUser);
        showAlert(AlertType.INFORMATION, "Update Sucess", "Your password successfully changed.");
        initialize();
    }
}
