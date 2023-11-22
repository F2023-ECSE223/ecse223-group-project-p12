package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AssetMenuController {


    @FXML
    private ResourceBundle resources;

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox TopContent;

    @FXML
    private Button addImageButton;

    @FXML
    private Label replaceMe;

    @FXML
    void AddImage(ActionEvent event) {
    }

    @FXML
    private Button addTicketButton;

    @FXML
    private TextField ticketNumberField;

    @FXML
    private TableView<TOSpecificAsset> assetTable;

    @FXML
    private TableColumn<TOSpecificAsset, Integer> assetNumberColumn;

    @FXML
    private TableColumn<TOSpecificAsset, String> assetColumn;

    @FXML
    private TableColumn<TOSpecificAsset, Integer> roomColumn;

    @FXML
    private TableColumn<TOSpecificAsset, Integer> floorColumn;

    @FXML
    private TableColumn<TOSpecificAsset, String> purchaseDateColumn;

    @FXML
    private TableColumn<TOSpecificAsset, String> lifeExpectancyColumn;

    @FXML
    private TableColumn<TOSpecificAsset, String> maintenaceHistoryColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, HBox> actionColumn;
    

    private ObservableList<TOMaintenanceTicket> ticketList;

    private ObservableList<TOSpecificAsset> assetList;

    @FXML
    void initialize() {
        resources = AssetPlusFXMLView.getInstance().getBundle();

        /*
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

         */

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        assetNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        assetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAssetName()));
        floorColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRaisedByEmail()));
        roomColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFixedByEmail()));
        purchaseDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(cellData.getValue().getRaisedOnDate())));
        maintenaceHistoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

      
        List<TOMaintenanceTicket> tickets = AssetPlusFeatureSet6Controller.getTickets();
        ticketList = FXCollections.observableList(tickets);
        ticketTable.setItems(ticketList);


        List<TOSpecificAsset> assets = AssetPlusFeatureTOController.getSpecificAssets();
        assetList = FXCollections.observableList(assets);
        assetTable.setItems(assetList);

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


}
