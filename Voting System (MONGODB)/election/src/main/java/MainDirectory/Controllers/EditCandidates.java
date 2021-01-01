package MainDirectory.Controllers;

import MainDirectory.Classes.DbConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditCandidates {
    @FXML
    private JFXTextField txtName;
    @FXML
    private JFXTextField txtParty;
    @FXML
    private JFXButton editBtn;

    int candidateID;

    /*
     * Method to edit candidate in the database
     * gets the changes from textFields and
     * updates them in the database
     */
    @FXML
    private void edit() {
        try {
            String name = txtName.getText();
            String party = txtParty.getText();

            // validating user input
            if (validate(name) && validate(party)) {
                DbConnection.editCandidate(candidateID,name,party);

                // Alert box showing action completed successfully
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Candidate list updated!");
                alert.setContentText("Candidate has been edited, Please refresh!");
                alert.showAndWait();

                // Closing the edit candidate stage
                Stage stage = (Stage) editBtn.getScene().getWindow();
                stage.close();
            } else{
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
        txtName.clear();
        txtParty.clear();
    }

    // Setter for text fields & id
    public void setTextField(int id, String name, String party) {
        candidateID = id;
        txtName.setText(name);
        txtParty.setText(party);
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