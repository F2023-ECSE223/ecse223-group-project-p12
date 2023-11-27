package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import static java.lang.Integer.numberOfLeadingZeros;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOAssetType;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.controller.TOSpecificAsset;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.AddSpecificAssetPopupController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.DeleteSpecificAssetPopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.MaintenanceHistoryPopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ModifySpecificAssetPopupController;
import javafx.beans.binding.DoubleBinding;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
    private TableColumn<TOSpecificAsset, String> lifeExpectancyColumn;

    @FXML
    private TableColumn<TOSpecificAsset, HBox> maintenanceHistoryColumn;

    @FXML
    private TableColumn<TOSpecificAsset, HBox> actionColumn;

    private ObservableList<TOSpecificAsset> assetList;

    @FXML
    private Button addSpecificAssetBtn;

    @FXML
    private TextField assetNumberSearch;

    @FXML
    private TextField assetSearch;

     @FXML
    private DatePicker dateSearch;

    @FXML
    private TextField floorSearch;

    @FXML
    private TextField lifeSearch;

    @FXML
    private TextField roomSearch;

    @FXML
    private GridPane searchPane;

    @FXML
    private Button searchBtn;

    @FXML
    void search(ActionEvent event) {
        searchPane.setVisible(true);
    }

    @FXML
    void initialize() {
      showSpecificAsset();
      assetTable.addEventHandler(AssetPlusFXMLView.REFRESH_EVENT, e -> {
        showSpecificAsset();
      });
      AssetPlusFXMLView.getInstance().registerRefreshEvent(assetTable);

    }

    public void showSpecificAsset(){
        searchPane.setVisible(false);

        List<TOSpecificAsset> assets = AssetPlusFeatureTOController.getSpecificAssets();
        assetList = FXCollections.observableList(assets);
        assetTable.setItems(assetList);
    
        resources = AssetPlusFXMLView.getInstance().getBundle();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
        assetNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAssetNumber()).asObject());
        assetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAssetType().getName()));
        roomColumn.setCellValueFactory(cellData -> {
            int roomNumber = cellData.getValue().getRoomNumber();
            return new SimpleObjectProperty<>(roomNumber != -1 ? roomNumber : null);
        });
        floorColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getFloorNumber()).asObject());
        purchaseDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPurchaseDate().toLocalDate().format(formatter)));
        lifeExpectancyColumn.setCellValueFactory(cellData -> {
            String expectedLifeSpanString = cellData.getValue().getAssetType().getExpectedLifeSpan()+ "";
            String concatenatedString = expectedLifeSpanString + " years";
            return new SimpleStringProperty(concatenatedString);
        });

        maintenanceHistoryColumn.setCellValueFactory(cellData -> {
            Button maintenanceBtn = new Button();
            maintenanceBtn.getStyleClass().add("icon-maintenancehistory");
            maintenanceBtn.setPickOnBounds(true);
            maintenanceBtn.setOnAction(event -> handleMaintenanceHistoryClicked(cellData.getValue()));

            HBox hbox = new HBox(maintenanceBtn);
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);

            return new SimpleObjectProperty<>(hbox);
        });
        
        actionColumn.setCellValueFactory(cellData -> {

        Button editBtn = new Button();
        editBtn.getStyleClass().add("icon-edit");
        editBtn.setPickOnBounds(true);
        editBtn.setOnAction(event -> handleEditButtonClicked(cellData.getValue().getAssetNumber()));

        Button trashBtn = new Button();
        trashBtn.getStyleClass().add("icon-trash");
        trashBtn.setPickOnBounds(true);
        trashBtn.setOnAction(event -> handleTrashButtonClicked(cellData.getValue().getAssetNumber()));

        HBox hbox = new HBox(editBtn, trashBtn);
        hbox.setSpacing(10);
        hbox.setAlignment(Pos.CENTER);

        return new SimpleObjectProperty<>(hbox);
    });

    assetNumberSearch.setOnKeyReleased(event -> performSearch());
    assetSearch.setOnKeyReleased(event -> performSearch());
    dateSearch.setOnAction(event -> performSearch());
    floorSearch.setOnKeyReleased(event -> performSearch());
    lifeSearch.setOnKeyReleased(event -> performSearch());
    roomSearch.setOnKeyReleased(event -> performSearch());

    }

    private void performSearch() {
        int assetNumberText = assetNumberSearch.getText().isEmpty() ? 0 : Integer.parseInt(assetNumberSearch.getText().toLowerCase().trim());
        String assetName = assetSearch.getText().toLowerCase().trim();
        LocalDate searchDate = dateSearch.getValue();
        String floorText = floorSearch.getText().toLowerCase().trim();
        int floorNumber = floorText.isEmpty() ? 0 : Integer.parseInt(floorText);
        String roomText = roomSearch.getText().toLowerCase().trim();
        int roomNumber = roomText.isEmpty() ? 0 : Integer.parseInt(roomText);
        String lifeText = lifeSearch.getText().toLowerCase().trim();
        int lifeNumber = lifeText.isEmpty() ? 0 : Integer.parseInt(lifeText);
    
        FilteredList<TOSpecificAsset> filteredAssets = new FilteredList<>(assetList);

        filteredAssets.setPredicate(asset -> {
            boolean assetNumberMatch = assetNumberText == 0 || asset.getAssetNumber() == assetNumberText;
            boolean assetNameMatch = assetName.isEmpty() || asset.getAssetType().getName().toLowerCase().contains(assetName);
            boolean dateMatch = searchDate == null || asset.getPurchaseDate().toLocalDate().isEqual(searchDate);
            boolean floorMatch = floorText.isEmpty() || (asset.getFloorNumber() == floorNumber);
            boolean lifeExpectancyMatch = lifeNumber == 0 || (asset.getAssetType().getExpectedLifeSpan() == lifeNumber);
            boolean roomMatch = roomText.isEmpty() || (asset.getRoomNumber() == roomNumber);

            return assetNumberMatch && assetNameMatch && dateMatch && floorMatch && lifeExpectancyMatch && roomMatch;
        });

        assetTable.setItems(filteredAssets);
    }
    
    
    
    private void handleEditButtonClicked(int assetNumber) {
        ModifySpecificAssetPopupController.get(assetNumber);
        ModifySpecificAssetPopupController controller = (ModifySpecificAssetPopupController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ModifySpecificAssetPopUp.fxml", "Modify Specific Asset");
    }

    private void handleTrashButtonClicked(int assetNumber) {
        DeleteSpecificAssetPopUpController.get(assetNumber);
        DeleteSpecificAssetPopUpController controller = (DeleteSpecificAssetPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/DeleteSpecificAssetPopUp.fxml", "Delete Specific Asset");        
    }
    
    private void handleMaintenanceHistoryClicked(TOSpecificAsset asset) {
        MaintenanceHistoryPopUpController.get(asset);
        MaintenanceHistoryPopUpController controller = (MaintenanceHistoryPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/MaintenanceHistoryPopUp.fxml", "Maintenance History");       
    }

    @FXML
     void addSpecificAsset(ActionEvent event) {
        AddSpecificAssetPopupController controller = (AddSpecificAssetPopupController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AddSpecificAssetPopUp.fxml", "Add Specific Asset");
    }
    
}


        

    





