package MainDirectory.Controllers;

import MainDirectory.Classes.Candidates;
import MainDirectory.Classes.DbConnection;
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
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.bson.Document;
import java.io.IOException;


public class CandidatesController {
    @FXML
    private TableView<Candidates> candidateTable;
    @FXML
    private TableColumn<Candidates, Integer> idColumnCandidate;
    @FXML
    private TableColumn<Candidates, String> nameColumnCandidate;
    @FXML
    private TableColumn<Candidates, String> partyColumnCandidate;

    // ObservableList is used to add the candidates to the table
    ObservableList<Candidates> candidates = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        refreshTable();
    }

    /*
     * Method to show the candidates on table.
     * gets the candidates from database with query
     * adds them to a ObservableList.
     * table view items will be set by this list
     */
    @FXML
    public void refreshTable() {
        try {
            candidates.clear(); //clears previous data from the list

            MongoDatabase db = DbConnection.getDbConnection();
            MongoCollection<Document> collection = db.getCollection("candidate");
            FindIterable<Document> findIterable = collection.find();

            for (Document document : findIterable) {
                candidates.add(new Candidates(document.getInteger("id"), document.getString("name"),
                        document.getString("party")));
            }

            // Setting the CellValueFactory to columns
            idColumnCandidate.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumnCandidate.setCellValueFactory(new PropertyValueFactory<>("name"));
            partyColumnCandidate.setCellValueFactory(new PropertyValueFactory<>("party"));

            // Adding data from the ObservableList to table view
            candidateTable.setItems(candidates);

        } catch (Exception e) {
            // Alert box showing error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error occurred while performing action");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // Method to open add candidates window
    @FXML
    private void addCandidate() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/FXML/addCandidates.fxml"));
        stage.setScene(new Scene(root, 400, 340));
        stage.resizableProperty().setValue(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }

    /*
     * method to open edit candidates window
     * the window will be opened with the details of selected row
     */
    @FXML
    private void editCandidate() {
        try {
            // getting the selected candidate
            Candidates candidate = candidateTable.getSelectionModel().getSelectedItem();

            // Loading the new FXML loader
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/FXML/editCandidate.fxml"));
            loader.load();

            /*
             * getting the controller class of the FXML and
             * setting the text fields according to the selected candidate
             */
            EditCandidates editCandidates = loader.getController();
            editCandidates.setTextField(candidate.getId(), candidate.getName(), candidate.getParty());

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

    // Method to delete a candidate
    @FXML
    private void deleteCandidate() {
        try {
            // getting the selected row
            Candidates candidate = candidateTable.getSelectionModel().getSelectedItem();

            // deleting the candidate by ID
            DbConnection.deleteCandidate(candidate.getId());

            // Alert box showing action completed successfully
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Action Successful!");
            alert.setContentText("Candidate deleted!");
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