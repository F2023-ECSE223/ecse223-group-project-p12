package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

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
    private TableColumn<TOSpecificAsset, String> lifeExpectancyColumn;

    @FXML
    private TableColumn<TOSpecificAsset, HBox> maintenanceHistoryColumn;

    @FXML
    private TableColumn<TOSpecificAsset, HBox> actionColumn;

    private ObservableList<TOSpecificAsset> assetList;

    @FXML
    private Button addSpecificAssetBtn;

    @FXML
    private ComboBox<String> typeChoice;


    @FXML
    void initialize() {
      showSpecificAsset();
      assetTable.addEventHandler(AssetPlusFXMLView.REFRESH_EVENT, e -> {
        showSpecificAsset();
      });
      AssetPlusFXMLView.getInstance().registerRefreshEvent(assetTable);

    }

    public void showSpecificAsset(){
        typeChoice.setValue(resources.getString("key.AssetMenu_SelectType"));
        typeChoice.setOnAction(event -> filterTableView(typeChoice.getValue()));

        ArrayList<String> types = new ArrayList<>();
        for (TOAssetType type : AssetPlusFeatureTOController.getAssetTypes()){
            types.add(type.getName());
        }
        ObservableList<String> typesList = FXCollections.observableArrayList(types);
        typesList.add(resources.getString("key.ShowAll"));
        typeChoice.setItems(typesList);

        List<TOSpecificAsset> assets = AssetPlusFeatureTOController.getSpecificAssets();
        assetList = FXCollections.observableList(assets);
        assetTable.setItems(assetList);
    
        resources = AssetPlusFXMLView.getInstance().getBundle();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
        assetNumberColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAssetNumber()).asObject());
        assetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAssetType().getName()));
        roomColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRoomNumber()).asObject());
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
            maintenanceBtn.setOnAction(event -> handleMaintenanceHistoryClicked());

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

    }
    
    private void handleEditButtonClicked(int assetNumber) {
        ModifySpecificAssetPopupController.get(assetNumber);
        System.out.println(assetNumber);
        ModifySpecificAssetPopupController controller = (ModifySpecificAssetPopupController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ModifySpecificAssetPopUp.fxml", "Modify Specific Asset");
    }

    private void handleTrashButtonClicked(int assetNumber) {
        DeleteSpecificAssetPopUpController.get(assetNumber);
        System.out.println(assetNumber);
        DeleteSpecificAssetPopUpController controller = (DeleteSpecificAssetPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/DeleteSpecificAssetPopUp.fxml", "Delete Specific Asset");        
    }
    
    private void handleMaintenanceHistoryClicked() {

    }

    @FXML
     void addSpecificAsset(ActionEvent event) {
        AddSpecificAssetPopupController controller = (AddSpecificAssetPopupController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AddSpecificAssetPopUp.fxml", "Add Specific Asset");
    }

    private void setPercentageWidth(TableColumn<?, ?> column, double percentage) {
        DoubleBinding widthBinding = assetTable.widthProperty().multiply(percentage / 100.0);
        column.prefWidthProperty().bind(widthBinding);
    }

    @FXML
    void filterTableView(String selectedType) {
        System.out.println(selectedType);
        if (selectedType == null || selectedType.equals(resources.getString("key.ShowAll"))) {
            showSpecificAsset();
        } else {
            FilteredList<TOSpecificAsset> filteredList = new FilteredList<>(assetList, asset -> selectedType.contains(asset.getAssetType().getName()));
            assetTable.setItems(filteredList);
        }
    }
    
}


        

    





