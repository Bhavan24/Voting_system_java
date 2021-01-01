package MainDirectory.Controllers;

import MainDirectory.Classes.DbConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditVoters {
    @FXML
    private JFXTextField txtDOB;
    @FXML
    private JFXTextField txtFirstname;
    @FXML
    private JFXTextField txtLastname;
    @FXML
    private JFXTextField txtAddress;
    @FXML
    private JFXTextField txtGender;
    @FXML
    private JFXButton editBtn;

    int voterID;

    /*
     * Method to edit voters in the database
     * gets the changes from textFields and
     * updates them in the database
     */
    @FXML
    void edit() {
        try {
            String first_name = txtFirstname.getText();
            String last_name = txtLastname.getText();
            String address = txtAddress.getText();
            String gender = txtGender.getText();
            String dob = txtDOB.getText();

            // validating user input
            if (validate(first_name) && validate(last_name) && validate(gender) && !address.trim().equals("")){
                DbConnection.editVoter(voterID, first_name, last_name, address, gender, dob);

                // Alert box showing action completed successfully
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Voter list updated!");
                alert.setContentText("Voter has been edited, Please refresh!");
                alert.showAndWait();

                // Closing the edit voters stage
                Stage stage = (Stage) editBtn.getScene().getWindow();
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
        txtFirstname.clear();
        txtLastname.clear();
        txtAddress.clear();
        txtGender.clear();
        txtDOB.clear();
    }

    // Setter for text fields & id
    public void setTextField(int voter_id, String first_name, String last_name,
                             String address, String gender, String dob) {
        voterID = voter_id;
        txtFirstname.setText(first_name);
        txtLastname.setText(last_name);
        txtAddress.setText(address);
        txtGender.setText(gender);
        txtDOB.setText(dob);
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