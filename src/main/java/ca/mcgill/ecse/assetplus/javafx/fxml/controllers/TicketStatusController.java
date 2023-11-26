package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.AddTicketPopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ApproveTicketController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.AssignStaffToTicketController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.StartAndCompleteWorkController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ModifyTicketPopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.DeleteTicketPopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ViewImagesController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ViewNotesController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ViewTicketPopUpController;
import ca.mcgill.ecse.assetplus.model.AssetPlus;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;
import java.util.ResourceBundle;
import com.google.common.collect.Table;
import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import javafx.stage.Stage;;

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
    private TableColumn<TOMaintenanceTicket, Hyperlink> ticketNumberColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, String> assetColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, String> reporterColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, String> assigneeColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, String> dateStartedColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, Button> statusColumn;

    @FXML
    private TableColumn<TOMaintenanceTicket, HBox> actionColumn;

    @FXML
    private DatePicker datePickerBtn;

    private ObservableList<TOMaintenanceTicket> ticketList;

    @FXML
    void initialize() {
        resources = AssetPlusFXMLView.getInstance().getBundle();

        showTableView();
        ticketTable.addEventHandler(AssetPlusFXMLView.REFRESH_EVENT, e -> {
            showTableView();
        });
        AssetPlusFXMLView.getInstance().registerRefreshEvent(ticketTable);

        statusChoiceBox.getItems().addAll(
            resources.getString("key.TicketStatus_Open"),
            resources.getString("key.TicketStatus_Assigned"),
            resources.getString("key.TicketStatus_InProgress"),
            resources.getString("key.TicketStatus_Resolved"),
            resources.getString("key.TicketStatus_Closed"),
            resources.getString("key.ShowAll")
        );

        statusChoiceBox.setValue(resources.getString("key.TicketStatus_SelectStatus"));
        statusChoiceBox.setOnAction(event -> filterTableView(getKey(statusChoiceBox.getValue())));
    }

    @FXML
    void goToTicketMenu(ActionEvent event) {
        AddTicketPopUpController controller = (AddTicketPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AddTicketPopUp.fxml", "Add Ticket");
    }

    @FXML
    void filterTableView(String selectedStatus) {
        if (selectedStatus == null || selectedStatus.equals("key.ShowAll")) {
            ticketTable.setItems(ticketList);
        } else {
            FilteredList<TOMaintenanceTicket> filteredList = new FilteredList<>(ticketList, ticket -> selectedStatus.contains(ticket.getStatus()));
            ticketTable.setItems(filteredList);
        }
    }

    private String getKey(String text) {
        for (String key: resources.keySet()) {
            if (resources.getString(key).equals(text)) {
                return key;
            }
        }

        return "defaultKey";
    } 

    @FXML
    void handleDatePickerClicked(ActionEvent event) {
        
    }

    private void showTableView() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");

        assetColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAssetName()));
        reporterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(ViewUtils.getUsername(cellData.getValue().getRaisedByEmail())));
        assigneeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFixedByEmail()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        //purchaseDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPurchaseDate().toLocalDate().format(formatter)));
        dateStartedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRaisedOnDate().toLocalDate().format(formatter)));
        statusColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));
        assigneeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(ViewUtils.getUsername(cellData.getValue().getFixedByEmail())));
        dateStartedColumn.setCellValueFactory(cellData -> new SimpleStringProperty(dateFormat.format(cellData.getValue().getRaisedOnDate())));
        
        statusColumn.setCellValueFactory(cellData -> {
            int ticketId = cellData.getValue().getId();
            String status = cellData.getValue().getStatus();
            Button statusButton = new Button(resources.getString("key.TicketStatus_" + status));
            statusButton.setStyle(getStyle(status));
            setCursor(statusButton);
            statusButton.setOnAction(event -> handleStatusCellClicked("key.TicketStatus_" + status, ticketId));
            Tooltip statusToolTip = new Tooltip(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketStatus_ChangeStatus"));
            statusToolTip.setStyle("-fx-text-fill: #808080");
            statusButton.setTooltip(statusToolTip);
    
            return new SimpleObjectProperty<>(statusButton);
        });

        ticketList = ViewUtils.getMaintenanceTickets();
        ticketTable.setItems(ticketList);
        
        ticketNumberColumn.setCellValueFactory(cellData -> {
            int ticketId = cellData.getValue().getId();
            Hyperlink link = new Hyperlink("#" + String.valueOf(ticketId));
            link.setStyle("-fx-text-fill: #8768F2; -fx-underline: true; -fx-cursor: hand;");
            link.setOnAction(event -> handleTicketClicked(ticketId));

            return new SimpleObjectProperty<>(link);
        });

        actionColumn.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(HBox item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    setGraphic(item);
                }
            }
        });

        actionColumn.setCellValueFactory(cellData -> {
            int ticketId = cellData.getValue().getId();
            // Create an HBox with three SVGPath objects representing icons
            Button imgBtn = new Button();
            imgBtn.getStyleClass().add("icon-image");
            imgBtn.setPickOnBounds(true);
            imgBtn.setOnAction(event -> handleImageButtonClicked(ticketId));
            setCursor(imgBtn);
            Tooltip imgTooltip = new Tooltip(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketStatus_ViewImages"));
            imgTooltip.setStyle("-fx-text-fill: #4488D8");
            imgBtn.setTooltip(imgTooltip);

            Button notesBtn = new Button();
            notesBtn.getStyleClass().add("icon-notes");
            notesBtn.setPickOnBounds(true);
            notesBtn.setOnAction(event -> handleNotesButtonClicked(ticketId));
            setCursor(notesBtn);
            Tooltip noteTooltip = new Tooltip(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketStatus_ViewNotes"));
            noteTooltip.setStyle("-fx-text-fill: #CD6200");
            notesBtn.setTooltip(noteTooltip);

            Button editBtn = new Button();
            editBtn.getStyleClass().add("icon-edit");
            editBtn.setPickOnBounds(true);
            editBtn.setOnAction(event -> handleEditButtonClicked(ticketId));
            setCursor(editBtn);
            Tooltip editTooltip = new Tooltip(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketStatus_ModifyTicket"));
            editTooltip.setStyle("-fx-text-fill: #624DE3");
            editBtn.setTooltip(editTooltip);

            Button trashBtn = new Button();
            trashBtn.getStyleClass().add("icon-trash");
            trashBtn.setPickOnBounds(true);
            trashBtn.setOnAction(event -> handleTrashButtonClicked(ticketId));
            setCursor(trashBtn);
            Tooltip trashTooltip = new Tooltip(AssetPlusFXMLView.getInstance().getBundle().getString("key.TicketStatus_DeleteTicket"));
            trashTooltip.setStyle("-fx-text-fill: #A30D11");
            trashBtn.setTooltip(trashTooltip);

            HBox hbox = new HBox(imgBtn, notesBtn, editBtn, trashBtn);
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);

            return new SimpleObjectProperty<>(hbox);
    
        });
    }

    private void handleStatusCellClicked(String status, int ticketId) {
        StartAndCompleteWorkController sharedController;
        switch (status) {
            case "key.TicketStatus_Open":
                AssignStaffToTicketController controller1 = (AssignStaffToTicketController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AssignStaffToTicket.fxml", "Assign Staff To Ticket");
                controller1.setTicketId(ticketId);
                break;
            case "key.TicketStatus_Assigned":
                sharedController = (StartAndCompleteWorkController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/StartWork.fxml", "Start Work");
                sharedController.setTicketId(ticketId);
                break;
            case "key.TicketStatus_InProgress":
                sharedController = (StartAndCompleteWorkController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/CompleteWork.fxml", "Complete Work");
                sharedController.setTicketId(ticketId);
                break;
            case "key.TicketStatus_Resolved":
                ApproveTicketController controller2 = (ApproveTicketController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ApproveTicket.fxml", "Approve Ticket");
                controller2.setTicketId(ticketId);
                break;
        }
    }

    private void handleImageButtonClicked(int ticketId) {
        ViewImagesController controller = (ViewImagesController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ViewImages.fxml", "View Images");
        controller.setTicketId(ticketId);
    }

    private void handleNotesButtonClicked(int ticketId) {
        ViewNotesController controller = (ViewNotesController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ViewNotes.fxml", "View Notes");
        controller.setTicketId(ticketId);
    }

    private void handleEditButtonClicked(int ticketId) {
        ModifyTicketPopUpController controller = (ModifyTicketPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ModifyTicketPopUp.fxml", "Update Ticket");
        if (controller==null) System.out.println("controller null");
        System.out.println("Updating with ticket number: " + Integer.toString(ticketId));
        controller.setTicketId(ticketId);

    }

    private void handleTrashButtonClicked(int ticketId) {
        DeleteTicketPopUpController.setId(ticketId);
        DeleteTicketPopUpController controller = (DeleteTicketPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/DeleteTicketPopUp.fxml", "Delete Ticket");
        if (controller==null) System.out.println("controller null");
        System.out.println("Deleting with ticket number: " + Integer.toString(ticketId));
    private void handleTrashButtonClicked() {
        
    }

    private void handleTicketClicked(int ticketId) {
        ViewTicketPopUpController controller = (ViewTicketPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ViewTicketPopUp.fxml", "View Ticket");
        controller.setTicketId(ticketId);
    }

    private String getStyle(String status) {
        switch (status) {
            case "Open":
                return "-fx-background-color: #D3D3D3; -fx-text-fill: #696969; fx-background-radius: 50px";
            case "Assigned":
                return "-fx-background-color: #E6F7FF; -fx-text-fill: #0066CC; fx-background-radius: 50px";
            case "InProgress":
                return "-fx-background-color: #FEF2E5; -fx-text-fill: #CD6200; fx-background-radius: 50px";
            case "Resolved":
                return "-fx-background-color: #EBF9F1; -fx-text-fill: #1F9254; fx-background-radius: 50px";
            case "Closed":
                return "-fx-background-color: #FBE7E8; -fx-text-fill: #A30D11; fx-background-radius: 50px";
            default: 
                return "";
        }
    }

    private void setCursor(Button button) {
        button.setOnMouseEntered(event -> button.setCursor(Cursor.HAND));
        button.setOnMouseExited(event -> button.setCursor(Cursor.DEFAULT));
    }
}
