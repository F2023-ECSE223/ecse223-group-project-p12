package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import java.util.ResourceBundle;
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
        statusChoiceBox.setOnAction(event -> filterTableView(getKey(statusChoiceBox.getValue())));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        ticketNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        assetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAssetName()));
        reporterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRaisedByEmail()));
        assigneeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFixedByEmail()));
        dateStartedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(cellData.getValue().getRaisedOnDate())));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        ticketList = ViewUtils.getMaintenanceTickets();
        ticketTable.setItems(ticketList);
        
        statusColumn.setCellFactory(new Callback<TableColumn<TOMaintenanceTicket,String>,TableCell<TOMaintenanceTicket,String>>() {
            @Override
            public TableCell<TOMaintenanceTicket, String> call(TableColumn<TOMaintenanceTicket, String> param) {
                return new TableCell<TOMaintenanceTicket, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setStyle(null);
                        }
                        else {
                            setText(resources.getString("key." + item));
                            setStyle(getStatusStyle(resources.getString("key." + item)));
                        }
                    }

                    private String getStatusStyle(String status) {
                        switch (status) {
                            case "Open":
                                return "-fx-background-color: #D3D3D3; -fx-text-fill: #696969;";
                            case "Ouvert":
                                return "-fx-background-color: #D3D3D3; -fx-text-fill: #696969;";
                            case "Assigned":
                                return "-fx-background-color: #FEF2E5; -fx-text-fill: #CD6200";
                            case "Assigné":
                                return "-fx-background-color: #FEF2E5; -fx-text-fill: #CD6200";
                            case "In Progress":
                                return "-fx-background-color: #FFF8DC; -fx-text-fill: #FFD700";
                            case "En progrès":
                                return "-fx-background-color: #FFF8DC; -fx-text-fill: #FFD700";
                            case "Resolved":
                                return "-fx-background-color: #EBF9F1; -fx-text-fill: #1F9254";
                            case "Résolu":
                                return "-fx-background-color: #EBF9F1; -fx-text-fill: #1F9254";
                            case "Closed":
                                return "-fx-background-color: #FBE7E8; -fx-text-fill: #A30D11";
                            case "Fermé":
                                return "-fx-background-color: #FBE7E8; -fx-text-fill: #A30D11";
                            default: 
                                return "";
                        }
                    }
                };
            }
        });
    }

    @FXML
    void goToTicketMenu(ActionEvent event) {
        AssetPlusFXMLView.getInstance().changeTab("pages/TicketMenu.fxml");
    }

    @FXML
    void filterTableView(String selectedStatus) {
        if (selectedStatus == null || selectedStatus.equals("key.ShowAll")) {
            ticketTable.setItems(ticketList);
        } else {
            String status = selectedStatus.substring(selectedStatus.lastIndexOf(".") + 1);
            FilteredList<TOMaintenanceTicket> filteredList = new FilteredList<>(ticketList, ticket -> ticket.getStatus().equals(status));
            ticketTable.setItems(filteredList);
        }
    }

    public String getKey(String text) {
        for (String key: resources.keySet()) {
            if (resources.getString(key).equals(text)) {
                return key;
            }
        }

        return "defaultKey";
    } 

}
