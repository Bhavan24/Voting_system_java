package MainDirectory.Controllers;

import MainDirectory.Classes.Candidates;
import MainDirectory.Classes.Connector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import java.sql.ResultSet;

public class VotingBallots {
    @FXML
    private TableView<Candidates> candidateTable;
    @FXML
    private TableColumn<Candidates, Integer> idColumnCandidate;
    @FXML
    private TableColumn<Candidates, String> nameColumnCandidate;
    @FXML
    private TableColumn<Candidates, String> partyColumnCandidate;
    @FXML
    private BorderPane borderpane;

    // ObservableList is used to add the candidates to the table
    ObservableList<Candidates> candidates = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        refreshTable();
    }

    /*
     * Method to show the candidates on table.
     * gets the results from database with query
     * adds them to a ObservableList.
     * table view items will be set by this list
     */
    @FXML
    public void refreshTable() {
        try {
            candidates.clear(); //clears previous data from the list

            ResultSet set = Connector.newSqlQuery("SELECT * FROM `candidate`");

            while (set.next()) {
                candidates.add(new Candidates(set.getInt("id"),
                        set.getString("name"), set.getString("party")));
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

    // Method to add votes
    @FXML
    private void addVote() {
        try {
            // getting the selected row
            Candidates candidate = candidateTable.getSelectionModel().getSelectedItem();

            // adding the vote by candidate id
            Connector.addVote(candidate.getId());

            // Alert box showing action completed successfully
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Vote Added Successfully!");
            alert.setContentText("You voted for " + candidate.getName());
            alert.showAndWait();

            // Loading a FXML stage
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/empty.fxml"));
            borderpane.setCenter(root);

        } catch (Exception e) {
            // Alert box showing error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error occurred while performing action");
            alert.setContentText("Please select the row before voting!");
            alert.showAndWait();
        }
    }
}