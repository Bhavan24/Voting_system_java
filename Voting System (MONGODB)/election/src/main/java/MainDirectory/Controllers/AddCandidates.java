package MainDirectory.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import MainDirectory.Classes.DbConnection;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCandidates {
    @FXML
    private JFXTextField txtId;
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtParty;
    @FXML
    private JFXButton addBtn;

    /*
     * Method to add candidate to the database
     * Assigns textField text to variables and
     * adds them to the database
     */
    @FXML
    private void add() {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            String party = txtParty.getText();

            // validating user input
            if (validate(name) && validate(party)) {
                DbConnection.addCandidates(Integer.parseInt(id), name, party);

                // Alert box showing action completed successfully
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Candidate list updated!");
                alert.setContentText("New Candidate has been added, Please refresh!");
                alert.showAndWait();

                // Closing the window
                Stage stage = (Stage) addBtn.getScene().getWindow();
                stage.close();
            } else {
                // Alert box showing error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error occurred while performing action");
                alert.setContentText("Input was not in the correct format!");
                alert.showAndWait();
            }
        } catch (Exception exception) {
            // Alert box showing error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error occurred while performing action");
            alert.setContentText(exception.getMessage());
            alert.showAndWait();
        }
    }

    // Method to clear text fields
    @FXML
    private void clear() {
        txtId.clear();
        txtName.clear();
        txtParty.clear();
    }

    /*
     * Method to validate user input
     * if user input is empty or any special characters found
     * returns false
     */
    private static boolean validate(String input){
        if (input.trim().equals("")){
            return false;
        } else {
            Pattern p = Pattern.compile("[0-9,%@!()*~^#$&+/-]");
            Matcher m = p.matcher(input);
            return !(m.find());
        }
    }
}