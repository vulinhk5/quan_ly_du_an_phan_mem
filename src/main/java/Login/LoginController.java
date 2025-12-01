package Login;

import Core.User;
import SceneManager.SceneManager;
import SessionManager.SessionManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import static Database.UserManager.*;
import static Utility.AlertHelper.showAlert;
import static Utility.SHA.convertToSHA512;

public class LoginController {

    public TextField userName;
    public PasswordField password;

    @FXML
    public void onMouseClickedOnSignup() {
        SceneManager.getInstance().switchTo(SceneManager.SIGNUP);
    }

    @FXML
    public void handleEnterKeyPress() {
        onMouseClickedOnLogin();
    }

    @FXML
    public void onMouseClickedOnForgotPassWord() {
        SceneManager.getInstance().switchTo(SceneManager.FORGOTPASSWORD);
    }

    @FXML
    public void onMouseClickedOnLogin() {
        if (!isUsernameTaken(userName.getText())) {
            showAlert(AlertType.ERROR, "Username Not Found", "The username you entered does not exist. Please try again or sign up for a new account.");
            return;
        }

        User tempUser = getUser(userName.getText());

        System.out.println(tempUser);

        if (!(tempUser.getPasswordHash().equals(convertToSHA512(password.getText())))) {
            showAlert(AlertType.ERROR, "Incorrect Password", "The password you entered is incorrect. Please try again.");
            return;
        }

        SessionManager.currentUser = tempUser;

        System.out.println(SessionManager.currentUser);
        SceneManager.getInstance().switchTo(SceneManager.DASHBOARD);
    }
}
