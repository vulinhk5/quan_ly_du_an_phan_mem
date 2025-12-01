package Signup;

import static Utility.AlertHelper.showAlert;

import Core.User;
import Database.Database;
import SceneManager.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import static Database.UserManager.*;

public class SignupVerificationController {
    @FXML
    private TextField verificationCode;

    @FXML
    public void onVerifyClicked() {
        String enteredCode = verificationCode.getText();

        if (enteredCode.equals(SignupController.getVerificationCode())) {
            addUser(SignupController.getUser());
            showAlert(AlertType.INFORMATION, "Verification Success", "You have successfully signed up.");
            SceneManager.getInstance().switchTo(SceneManager.LOGIN);
        } else {
            showAlert(AlertType.ERROR, "Verification Failed", "Incorrect verification code.");
        }
    }
}
