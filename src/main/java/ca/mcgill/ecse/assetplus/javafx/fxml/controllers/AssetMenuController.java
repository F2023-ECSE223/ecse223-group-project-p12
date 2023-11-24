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
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.AddImagePopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.AddSpecificAssetPopupController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
    private Label replaceMe;

    @FXML
    private TextField assetNumberField;

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
    private TableColumn<TOSpecificAsset, Integer> lifeExpectancyColumn;

    @FXML
    private TableColumn<TOSpecificAsset, HBox> maintenanceHistoryColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, HBox> actionColumn;

    private ObservableList<TOSpecificAsset> assetList;

    @FXML
    private Button addSpecificAssetBtn;

    @FXML
    void initialize() {
        List<TOSpecificAsset> assets = AssetPlusFeatureTOController.getSpecificAssets();
        assetList = FXCollections.observableList(assets);
        assetTable.setItems(assetList);

        resources = AssetPlusFXMLView.getInstance().getBundle();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        assetNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAssetNumber()).asObject());
        assetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAssetType().getName()));
        lifeExpectancyColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAssetType().getExpectedLifeSpan()).asObject());
        floorColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRoomNumber()).asObject());
        roomColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getFloorNumber()).asObject());
        purchaseDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(cellData.getValue().getPurchaseDate())));
        
        maintenanceHistoryColumn.setCellValueFactory(cellData -> {
            Button maintenanceBtn = new Button();
            maintenanceBtn.getStyleClass().add("icon-maintenancehistory");
            maintenanceBtn.setPickOnBounds(true);
            maintenanceBtn.setOnAction(event -> handleMaintenanceHistoryClicked());

            HBox hbox = new HBox(maintenanceBtn);
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);

            return new SimpleObjectProperty<>(hbox);
        });
        

         actionColumn.setCellValueFactory(cellData -> {
        // Create an HBox with three SVGPath objects representing icons

        Button editBtn = new Button();
        editBtn.getStyleClass().add("icon-edit");
        editBtn.setPickOnBounds(true);
        editBtn.setOnAction(event -> handleEditButtonClicked());

        Button trashBtn = new Button();
        trashBtn.getStyleClass().add("icon-trash");
        trashBtn.setPickOnBounds(true);
        trashBtn.setOnAction(event -> handleTrashButtonClicked());

        HBox hbox = new HBox(editBtn, trashBtn);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);

        return new SimpleObjectProperty<>(hbox);
    });

    }

    private void handleEditButtonClicked() {
        AssetPlusFXMLView.getInstance().changeTab("pages/TicketMenu.fxml", "editTab");
    }

    private void handleTrashButtonClicked() {
        AssetPlusFXMLView.getInstance().changeTab("pages/TicketMenu.fxml", "deleteTab");
    }
    
    private void handleMaintenanceHistoryClicked() {

    }

    @FXML
     void addSpecificAsset(ActionEvent event) {
        System.out.println("is anything happening?");
        AddSpecificAssetPopupController controller = (AddSpecificAssetPopupController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AddSpecificAssetPopUp.fxml", "Add Specific Asset");
    }

    
}


        

    





