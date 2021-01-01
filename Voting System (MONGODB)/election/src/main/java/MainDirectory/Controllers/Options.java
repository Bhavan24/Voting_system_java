package MainDirectory.Controllers;

import MainDirectory.Classes.DbConnection;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;

public class Options {
    @FXML
    private JFXButton candidatesOptionBtn;
    @FXML
    private JFXButton startVotingBtn;
    @FXML
    private JFXButton endVotingBtn;
    @FXML
    private JFXButton votersOptionBtn;
    @FXML
    private Button adminLoginBtn;
    @FXML
    private JFXTextField txtUsername;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private BorderPane borderpane1;
    @FXML
    private BorderPane borderpaneF;
    @FXML
    private JFXTextField txtVoterId;
    @FXML
    private Button voteBtn;

    // ArrayList to store voters who are already voted
    ArrayList<Integer> voterIds = new ArrayList<>();

    // Button count of login button
    int buttonCount = 0;

    @FXML
    private void initialize() {
        // Disabling buttons until admin login
        candidatesOptionBtn.setDisable(true);
        votersOptionBtn.setDisable(true);
        startVotingBtn.setDisable(true);
        endVotingBtn.setDisable(true);
        voteBtn.setDisable(true);
        txtVoterId.setDisable(true);
    }

    // Method to verify the admin
    @FXML
    public void checkLoginAdmin() {
        String username = txtUsername.getText();
        String password = txtPassword.getText();

        // Checking if the text fields are empty
        if (username.isEmpty() || password.isEmpty()) {
            // Alert box showing error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error occurred while performing action");
            alert.setContentText("Required fields are empty!");
            alert.showAndWait();

        } else {
            // DbConnection.validateAdmin() returns a boolean variable
            if (DbConnection.validateAdmin(username, password)) {
                buttonCount += 1; // incrementing button click

                // Alert box showing action completed successfully
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Successful login!");
                alert.setContentText("You logged in as " + username);
                alert.showAndWait();

                /*
                 * for the first login, all admin options are enabled
                 * except 'End Voting', for the second login only
                 * 'End Voting' is enabled
                 */
                if (buttonCount == 1) {
                    // Clearing text fields
                    txtUsername.clear();
                    txtPassword.clear();
                    // Disabling Login button & text fields
                    adminLoginBtn.setDisable(true);
                    txtUsername.setDisable(true);
                    txtPassword.setDisable(true);
                    // Enabling Admin option buttons
                    candidatesOptionBtn.setDisable(false);
                    votersOptionBtn.setDisable(false);
                    startVotingBtn.setDisable(false);
                } else {
                    endVotingBtn.setDisable(false);
                }
            } else {
                // Alert box showing error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Login!");
                alert.setContentText("Username or Password is wrong!");
                alert.showAndWait();
            }
        }
    }

    // Method to verify voter id
    @FXML
    private void checkVoterLogin() {
        try {
            int voter_id = Integer.parseInt(txtVoterId.getText());

            // checking if voter already voted
            if (voterIds.contains(voter_id)) {
                // Alert box showing error
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Invalid Attempt!");
                alert.setContentText("You have already voted!");
                alert.showAndWait();
            } else {
                // DbConnection.validateVoter() returns a boolean variable
                if (DbConnection.validateVoter(voter_id)) {
                    // adding the voter id to ArrayList
                    voterIds.add(voter_id);

                    // Alert box showing action completed successfully
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("Your VoterID is validated");
                    alert.setContentText("Wait until the voting ballot opens");
                    alert.showAndWait();

                    // Opening the voting ballot window
                    Parent root = FXMLLoader.load(getClass().getResource("/FXML/votingBallot.fxml"));
                    borderpane1.setCenter(root);

                    // Clearing Text field
                    txtVoterId.clear();
                } else {
                    // Alert box showing error
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Error!");
                    alert.setContentText("Invalid Voter ID!");
                    alert.showAndWait();
                }
            }
        } catch (Exception exception) {
            // Alert box showing error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Login!");
            alert.setContentText("Entered details are wrong!");
            alert.showAndWait();
        }
    }

    // Method to load candidates stage
    @FXML
    void candidates() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/candidates.fxml"));
        borderpane1.setCenter(root);
    }

    // Method to load voters stage
    @FXML
    void voters() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/voters.fxml"));
        borderpane1.setCenter(root);
    }

    // Method to load results stage
    @FXML
    void endVoting() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/results.fxml"));
        borderpaneF.setCenter(root);
    }

    // Method for start voting button
    @FXML
    void startVoting() throws IOException {
        // DbConnection.dataExits() returns a boolean variable
        if (DbConnection.dataExists()) {
            // Alert box showing information
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Voting Started!");
            alert.setContentText("Please proceed to 'Voter Options'");
            alert.showAndWait();

            // Loading a FXML stage
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/empty.fxml"));
            borderpane1.setCenter(root);

            // Disabling admin options buttons
            candidatesOptionBtn.setDisable(true);
            votersOptionBtn.setDisable(true);
            startVotingBtn.setDisable(true);
            endVotingBtn.setDisable(true);

            // Enabling voter options & admin login
            voteBtn.setDisable(false);
            txtVoterId.setDisable(false);
            adminLoginBtn.setDisable(false);
            txtUsername.setDisable(false);
            txtPassword.setDisable(false);

            // Focusing on voter id field
            txtVoterId.requestFocus();

        } else {
            // Alert box showing error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error occurred while performing action");
            alert.setContentText("Voting cannot start with 0 candidates or 0 voters registered!");
            alert.showAndWait();
        }
    }
}