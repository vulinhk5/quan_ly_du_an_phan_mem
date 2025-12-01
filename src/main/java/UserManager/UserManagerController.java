package UserManager;

import Core.Librarian;
import Core.Member;
import Core.User;
import SceneManager.SceneManager;
import Utility.AlertHelper;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import static Database.UserManager.*;
import static Utility.AlertHelper.*;

public class UserManagerController {

    public TableColumn colUserId;
    public TableColumn colFullName;
    public TableColumn colUsername;
    public TableColumn colPasswordHash;
    public TableColumn colEmail;
    public TableColumn colRole;
    public TableView tableView;
    public TextField userName;
    public TextField userId;
    public TextField fullName;
    public TextField passwordHash;
    public TextField email;
    public ComboBox role;

    public void initialize() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colFullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colPasswordHash.setCellValueFactory(new PropertyValueFactory<>("passwordHash"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colRole.setCellValueFactory(new PropertyValueFactory<>("role"));

        ObservableList<User> userList = getAllUsers();
        tableView.setItems(userList);

        tableView.setItems(userList);
    }

    public void onUpdateUserInfoButtonClicked() {
        SceneManager.getInstance().switchTo(SceneManager.UPDATEUSERINFO);
    }

    public void onDashboardButtonClicked() {
        SceneManager.getInstance().switchTo(SceneManager.DASHBOARD);
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

    public void onSearchButtonClicked() {
        if (!isUsernameTaken(userName.getText())) {
            showAlert(AlertType.ERROR, "Username Not Found", "The username you entered does not exist. Please check again.");
        } else {
            User user = getUser(userName.getText());
            userId.setText(user.getUserId());
            fullName.setText(user.getFullName());
            passwordHash.setText(user.getPasswordHash());
            email.setText(user.getEmail());
            role.setValue(user.getRole());
        }
    }

    public void onUpdateButtonClicked() {
        User user;
        if (role.getValue().equals(Member.MEMBER)) {
            user = new Member(
              userId.getText(),
              fullName.getText(),
              "",
              passwordHash.getText(),
              email.getText()
            );
        } else {
            user = new Librarian(
                userId.getText(),
                fullName.getText(),
                "",
                passwordHash.getText(),
                email.getText()
            );
        }

        updateUser(user);
        showAlert(AlertType.INFORMATION, "Update Success", "User information successfully updated.");

        initialize();
    }
}
