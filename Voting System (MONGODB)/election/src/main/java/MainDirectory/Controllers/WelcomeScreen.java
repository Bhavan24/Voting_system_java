package MainDirectory.Controllers;

import MainDirectory.Classes.DbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;

public class WelcomeScreen {
    @FXML
    private BorderPane borderpane;
    @FXML
    private Button startBtn;
    @FXML
    private Button closeBtn;
    @FXML
    private Button infoBtn;

    @FXML
    public void initialize() {
        startBtn.setDisable(true);
    }

    // Method to open options menu
    @FXML
    public void startProgram() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/options.fxml"));
        borderpane.setCenter(root);
        startBtn.setVisible(false);
    }

    // Method to open information page
    @FXML
    public void information() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/information.fxml"));
        borderpane.setCenter(root);
        infoBtn.setDisable(true);
        startBtn.setDisable(false);
    }

    /*
     * Method to close the program
     * if user selects OK button in alert
     * votes will be set to 0(zero) and
     * window will be closed
     */
    @FXML
    public void exitProgram() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure you want to exit?");
        alert.setContentText("if you exit all existing voting data will be lost!");
        alert.showAndWait();

        if (alert.getResult().equals(ButtonType.OK)) {
            DbConnection.resetVotes();
            Stage stage = (Stage) closeBtn.getScene().getWindow();
            stage.close();
        }
    }
}