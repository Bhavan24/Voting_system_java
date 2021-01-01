package MainDirectory.Controllers;

import MainDirectory.Classes.DbConnection;
import MainDirectory.Classes.Voters;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.bson.Document;

import java.io.File;
import java.io.IOException;

public class VoterController {
    @FXML
    private TableView<Voters> voterTable;
    @FXML
    private TableColumn<Voters, Integer> idColumnVoter;
    @FXML
    private TableColumn<Voters, String> fNameColumnVoter;
    @FXML
    private TableColumn<Voters, String> lNameColumnVoter;
    @FXML
    private TableColumn<Voters, String> genderColumnVoter;
    @FXML
    private TableColumn<Voters, String> dobColumnVoter;
    @FXML
    private TableColumn<Voters, String> addressColumnVoter;

    // ObservableList is used to add the voters to the table
    ObservableList<Voters> voters = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        refreshTable();
    }

    /*
     * Method to show the voters on table.
     * gets the results from database with query
     * adds them to a ObservableList.
     * table view items will be set by this list
     */
    @FXML
    public void refreshTable() {
        try {
            voters.clear(); //clears previous data from the list

            MongoDatabase db = DbConnection.getDbConnection();
            MongoCollection<Document> collection = db.getCollection("voter");
            FindIterable<Document> findIterable = collection.find();

            voters.clear();
            for (Document document : findIterable) {
                voters.add(new Voters(document.getInteger("voter_id"), document.getString("first_name"),
                        document.getString("last_name"), document.getString("address"),
                        document.getString("gender"), document.getString("dob")));
            }

            // Setting the CellValueFactory to columns
            idColumnVoter.setCellValueFactory(new PropertyValueFactory<>("voter_id"));
            fNameColumnVoter.setCellValueFactory(new PropertyValueFactory<>("first_name"));
            lNameColumnVoter.setCellValueFactory(new PropertyValueFactory<>("last_name"));
            addressColumnVoter.setCellValueFactory(new PropertyValueFactory<>("address"));
            genderColumnVoter.setCellValueFactory(new PropertyValueFactory<>("gender"));
            dobColumnVoter.setCellValueFactory(new PropertyValueFactory<>("dob"));

            // Adding data from the ObservableList to table view
            voterTable.setItems(voters);

        } catch (Exception e) {
            // Alert box showing error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error occurred while performing action");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // Method to import voters from csv file to database
    @FXML
    private void importVoter() {
        try {
            // Using FileChooser to choose csv file
            Stage stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(stage);
            String path = selectedFile.getAbsolutePath();

            // Changing the '\' in path to '/' (Escape sequences!)
            path = path.replace('\\', '/');

            DbConnection.importVoters(path);

            // reloading the table
            refreshTable();

        } catch (Exception e) {
            // Alert box showing error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error occurred while performing action");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // Method to open add voter window
    @FXML
    private void addVoter() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/addVoters.fxml"));
        stage.setScene(new Scene(root, 550, 500));
        stage.resizableProperty().setValue(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    /*
     * method to open edit voters window
     * the window will be opened with the details of selected row
     */
    @FXML
    private void editVoter() {
        try {
            // getting the selected row
            Voters voters = voterTable.getSelectionModel().getSelectedItem();

            // Loading the new FXML loader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/editVoters.fxml"));
            loader.load();

            /*
             * getting the controller class of the FXML and
             * setting the text fields according to the selected voter
             */
            EditVoters editVoters = loader.getController();
            editVoters.setTextField(voters.getVoter_id(), voters.getFirst_name(), voters.getLast_name(),
                    voters.getAddress(), voters.getGender(), voters.getDob());

            // Opening the window
            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.resizableProperty().setValue(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } catch (Exception e) {
            // Alert box showing error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error occurred while performing action");
            alert.setContentText("Please select the row before editing!");
            alert.showAndWait();
        }
    }

    // Method to delete a voter
    @FXML
    private void deleteVoter() {
        try {
            // Getting the selected row
            Voters voters = voterTable.getSelectionModel().getSelectedItem();

            // Deleting the voter by ID
            DbConnection.deleteVoter(voters.getVoter_id());

            // Alert box showing action completed successfully
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Action Successful!");
            alert.setContentText("Voter deleted!");
            alert.showAndWait();

            // reloading the table
            refreshTable();

        } catch (Exception e) {
            // Alert box showing error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error occurred while performing action");
            alert.setContentText("Please select the row before deleting!");
            alert.showAndWait();
        }
    }
}