package Signup;

import Core.Member;
import Core.User;
import Database.Database;
import SceneManager.SceneManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import static Utility.AlertHelper.showAlert;
import static Utility.RandomGenerator.generateVerificationCode;
import static Signup.SignupVerificationEmail.sendVerificationEmail;
import static Database.UserManager.*;

public class SignupController {

    @FXML
    private TextField fullName;
    @FXML
    private TextField email;
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField rePassword;

    private static String verificationCode;

    public static String getVerificationCode() {
        return verificationCode;
    }

    private static User user;

    public static User getUser() {
        return user;
    }

    @FXML
    public void onMouseClickedOnLogin() {
        SceneManager.getInstance().switchTo(SceneManager.LOGIN);
    }

    @FXML
    public void onMouseClickedOnSignup() {
        if (fullName.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Empty Field", "Full name cannot be empty.");
            return;
        }

        if (email.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Empty Field", "Email cannot be empty.");
            return;
        }

        if (userName.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Empty Field", "Username cannot be empty.");
            return;
        }

        if (password.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Empty Field", "Password cannot be empty.");
            return;
        }

        if (rePassword.getText().isEmpty()) {
            showAlert(AlertType.ERROR, "Empty Field", "Please re-type the password.");
            return;
        }

        if (!password.getText().equals(rePassword.getText())) {
            showAlert(AlertType.ERROR, "Password Mismatch", "Passwords do not match.");
            return;
        }

        if (isUsernameTaken(userName.getText())) {
            showAlert(AlertType.ERROR, "Username Taken", "The username you entered is already taken. Please choose a different username.");
            return;
        }

        if (isEmailTaken(email.getText())) {
            showAlert(AlertType.ERROR, "Email Already Registered", "The email you entered is already registered. Please use a different email.");
            return;
        }

        verificationCode = generateVerificationCode();
        new Thread(() -> sendVerificationEmail(email.getText(), fullName.getText(), verificationCode)).start();

        user = new Member(newUserId(), fullName.getText(), userName.getText(), password.getText(), email.getText());

        SceneManager.getInstance().switchTo(SceneManager.VERIFICATION);
    }
}
