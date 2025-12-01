package SceneManager;

import Database.Database;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    public static final String LOGIN = "/Login/LoginView.fxml";
    public static final String SIGNUP = "/Signup/SignupView.fxml";
    public static final String VERIFICATION = "/Signup/SignupVerificationView.fxml";
    public static final String DASHBOARD = "/Dashboard/DashboardView.fxml";
    public static final String USERMANAGER = "/UserManager/UserManagerView.fxml";
    public static final String UPDATEUSERINFO = "/UpdateUserInfo/UpdateUserInfoView.fxml";
    public static final String DOCUMENTMANAGER = "/DocumentManager/DocumentManagerView.fxml";
    public static final String FORGOTPASSWORD = "/ForgotPassword/ForgotPasswordView.fxml";

    private static SceneManager instance;

    private Stage primaryStage;

    private SceneManager() { }

    public static SceneManager getInstance() {
        if (instance == null) {
            synchronized (SceneManager.class) {
                if (instance == null) {
                    instance = new SceneManager();
                }
            }
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void switchTo(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/css/Style.css").toExternalForm());
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
