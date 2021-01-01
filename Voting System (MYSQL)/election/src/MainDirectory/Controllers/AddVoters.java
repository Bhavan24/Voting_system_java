package MainDirectory.Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import MainDirectory.Classes.Connector;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddVoters {
    @FXML
    private JFXTextField txtDOB;
    @FXML
    private JFXTextField txtVoterId;
    @FXML
    private JFXTextField txtFirstName;
    @FXML
    private JFXTextField txtLastName;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtGender;
    @FXML
    private JFXButton addBtn;

    /*
     * Method to add voters to the database
     * Assigns textField text to variables and
     * adds them to the database
     */
    @FXML
    private void add() {
        try {
            int voter_id = Integer.parseInt(txtVoterId.getText());
            String first_name = txtFirstName.getText();
            String last_name = txtLastName.getText();
            String address = txtAddress.getText();
            String gender = txtGender.getText();
            String dob = txtDOB.getText();

            // validating user input
            if (validate(first_name) && validate(last_name) && validate(gender) && !address.trim().equals("")){
                Connector.addVoter(voter_id, first_name, last_name, address, gender, dob);

                // Alert box showing action completed successfully
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Voter list updated!");
                alert.setContentText("New Voter has been added, Please refresh!");
                alert.showAndWait();

                // Closing the window
                Stage stage = (Stage) addBtn.getScene().getWindow();
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
        txtVoterId.clear();
        txtFirstName.clear();
        txtLastName.clear();
        txtAddress.clear();
        txtGender.clear();
        txtDOB.clear();
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