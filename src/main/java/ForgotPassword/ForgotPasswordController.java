package ForgotPassword;

import Core.User;
import Database.UserManager;
import SceneManager.SceneManager;
import Utility.RandomGenerator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import static Database.UserManager.*;
import static Utility.AlertHelper.*;

public class ForgotPasswordController {
    @FXML
    private TextField userName;

    public void handleEnterKeyPress() {
    }

    public void onMouseClickedOnSendEmail() {
        if (!isUsernameTaken(userName.getText())) {
            showAlert(AlertType.ERROR, "Username Not Found", "The username you entered does not exist. Please try again or sign up for a new account.");
            return;
        }

        User user = UserManager.getUser(userName.getText());

        String newPassword = RandomGenerator.generateNewPassword();
        user.setPasswordHash(newPassword);
        UserManager.updateUser(user);

        showAlert(AlertType.INFORMATION, "Email sent", "The password reset email has been sent successfully.");
        SceneManager.getInstance().switchTo(SceneManager.LOGIN);
        NewPasswordEmail.sendPasswordResetEmail(user.getEmail(), user.getFullName(), newPassword);
    }
}
