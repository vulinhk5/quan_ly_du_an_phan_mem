package Launcher;

import Database.Database;
import SessionManager.SessionManager;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import SceneManager.SceneManager;

public class Launcher extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        SceneManager.getInstance().setPrimaryStage(primaryStage);
        SceneManager.getInstance().switchTo(SceneManager.LOGIN);
        Database.getInstance();

        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/assets/images/Common/Logo.ico")));
        primaryStage.setTitle("Library Management System");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
