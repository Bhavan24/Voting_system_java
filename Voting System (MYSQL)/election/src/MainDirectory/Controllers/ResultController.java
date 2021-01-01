package MainDirectory.Controllers;

import MainDirectory.Classes.Connector;
import MainDirectory.Classes.Results;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultController {
    @FXML
    private TableView<Results> resultTable;
    @FXML
    private TableColumn<Results, String> nameColumn;
    @FXML
    private TableColumn<Results, Integer> votesColumn;
    @FXML
    private Label lblWinner;
    @FXML
    private PieChart pieChart;

    // this ObservableList is used to add the results to the table
    ObservableList<Results> results = FXCollections.observableArrayList();

    // this ObservableList is used to add the results to the pie chart
    ObservableList<PieChart.Data> resultPie = FXCollections.observableArrayList();

    @FXML
    public void initialize() throws SQLException {
        refreshTable();
        // printing out the winner
        lblWinner.setText("The Winner is " + Connector.findWinner());
        System.out.println(lblWinner.getText());
    }

    /*
     * Method to show the results on table.
     * gets the results from database with query
     * adds them to a ObservableList.
     * table view items will be set by this list
     */
    @FXML
    public void refreshTable() {
        try {
            results.clear(); //clears previous data from the list

            String query = "SELECT candidate.name, result.votes FROM candidate JOIN " +
                    "result ON candidate.id = result.id";
            ResultSet set = Connector.newSqlQuery(query);

            System.out.println("Candidates\t\tVotes");

            while (set.next()) {
                String name = set.getString("name");
                int votes = set.getInt("votes");

                // Printing the result on console (using '*' to make a graph)
                System.out.println(name + "\t" + votes + "\t" + ("*".repeat(votes)));

                results.add(new Results(name, votes));
                resultPie.add(new PieChart.Data(name, votes));
            }

            // Setting the CellValueFactory to columns
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            votesColumn.setCellValueFactory(new PropertyValueFactory<>("votes"));

            // Adding data from the ObservableList to table view
            resultTable.setItems(results);
            // Adding data from the ObservableList to pie chart
            pieChart.setData(resultPie);

        } catch (Exception e) {
            // Alert box showing error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error occurred while performing action");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}