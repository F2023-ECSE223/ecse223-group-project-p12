package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ResourceBundle;
import java.util.List;
import java.text.SimpleDateFormat;;

public class TicketStatusController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox TopContent;

    @FXML
    private Button addTicketButton;

    @FXML
    private ChoiceBox<String> statusChoiceBox;

    @FXML
    private TextField ticketNumberField;

    @FXML
    private TableView<TOMaintenanceTicket> ticketTable;

    @FXML
    private TableColumn<TOMaintenanceTicket, Integer> ticketNumberColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, String> assetColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, String> reporterColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, String> assigneeColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, String> dateStartedColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, String> statusColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, HBox> actionColumn;

    private ObservableList<TOMaintenanceTicket> ticketList;

    @FXML
    void initialize() {
        resources = AssetPlusFXMLView.getInstance().getBundle();

        statusChoiceBox.getItems().addAll(
            resources.getString("key.Open"),
            resources.getString("key.Assigned"),
            resources.getString("key.InProgress"),
            resources.getString("key.Resolved"),
            resources.getString("key.Closed"),
            resources.getString("key.ShowAll")
        );

        statusChoiceBox.setValue(resources.getString("key.SelectStatus"));
        statusChoiceBox.setOnAction(event -> filterTableView(statusChoiceBox.getValue()));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        ticketNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        assetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAssetName()));
        reporterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRaisedByEmail()));
        assigneeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFixedByEmail()));
        dateStartedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(cellData.getValue().getRaisedOnDate())));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        List<TOMaintenanceTicket> tickets = AssetPlusFeatureSet6Controller.getTickets();
        ticketList = FXCollections.observableList(tickets);
        ticketTable.setItems(ticketList);
    }

    @FXML
    void goToTicketMenu(ActionEvent event) {
        AssetPlusFXMLView.getInstance().changeTab("pages/TicketMenu.fxml");
    }

    @FXML
    void filterTableView(String selectedStatus) {
        if (selectedStatus == null || selectedStatus.equals(resources.getString("key.ShowAll"))) {
            ticketTable.setItems(ticketList);
        } else {
            FilteredList<TOMaintenanceTicket> filteredList = new FilteredList<>(ticketList, ticket -> ticket.getStatus().equals(selectedStatus));
            ticketTable.setItems(filteredList);
        }
    }

}
