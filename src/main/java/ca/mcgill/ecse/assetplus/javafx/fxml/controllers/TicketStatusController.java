package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ViewImagesController;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.util.Callback;
import java.util.ResourceBundle;
import com.google.common.collect.Table;
import com.google.errorprone.annotations.OverridingMethodsMustInvokeSuper;
import java.text.SimpleDateFormat;
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
        reporterColumn.setCellValueFactory(cellData -> new SimpleStringProperty(ViewUtils.getUsername(cellData.getValue().getRaisedByEmail())));
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
                            setCursor(Cursor.DEFAULT);
                        }
                        else {
                            setText(resources.getString("key." + item));
                            setStyle(getStatusStyle(resources.getString("key." + item)));
                            setOnMouseClicked(event -> handleStatusCellClicked("key." + item));
                            setCursor(Cursor.HAND);
                        }
                    }

                    private String getStatusStyle(String status) {
                        switch (status) {
                            case "Open":
                                return "-fx-background-color: #D3D3D3; -fx-text-fill: #696969;";
                            case "Ouvert":
                                return "-fx-background-color: #D3D3D3; -fx-text-fill: #696969;";
                            case "Assigned":
                                return "-fx-background-color: #E6F7FF; -fx-text-fill: #0066CC";
                            case "Assigné":
                                return "-fx-background-color: #E6F7FF; -fx-text-fill: #0066CC";
                            case "In Progress":
                                return "-fx-background-color: #FEF2E5; -fx-text-fill: #CD6200";
                            case "En progrès":
                                return "-fx-background-color: #FEF2E5; -fx-text-fill: #FFD700";
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

        ticketNumberColumn.setCellFactory(col -> {
            TableCell<TOMaintenanceTicket, Integer> cell = new TableCell<TOMaintenanceTicket, Integer>() {
                @Override
                protected void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle(null);
                    } else {
                        setText("#" + String.valueOf(item));
                        setStyle("-fx-text-fill: #8768F2; -fx-underline: true; -fx-cursor: hand;");
                    }
                }
            };
        
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty()) {
                    System.out.println("Ticket number " + cell.getItem() + " clicked!");
                }
            });
        
            return cell;
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

            Button editBtn = new Button();
            editBtn.getStyleClass().add("icon-edit");
            editBtn.setPickOnBounds(true);
            editBtn.setOnAction(event -> handleEditButtonClicked());

            Button trashBtn = new Button();
            trashBtn.getStyleClass().add("icon-trash");
            trashBtn.setPickOnBounds(true);
            trashBtn.setOnAction(event -> handleTrashButtonClicked());

            HBox hbox = new HBox(imgBtn, editBtn, trashBtn);
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);

            return new SimpleObjectProperty<>(hbox);
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

    private String getKey(String text) {
        for (String key: resources.keySet()) {
            if (resources.getString(key).equals(text)) {
                return key;
            }
        }

        return "defaultKey";
    } 

    private void handleStatusCellClicked(String status) {
        switch (status) {
            case "key.Open":
                AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AssignStaffToTicket.fxml", "Assign Staff To Ticket");
                break;
        }
    }

    private void handleImageButtonClicked(int ticketId) {
        ViewImagesController controller = (ViewImagesController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ViewImages.fxml", "View Images");
        controller.setTicketId(ticketId);
    }

    private void handleEditButtonClicked() {
        AssetPlusFXMLView.getInstance().changeTab("pages/TicketMenu.fxml", "editTab");
    }

    private void handleTrashButtonClicked() {
        AssetPlusFXMLView.getInstance().changeTab("pages/TicketMenu.fxml", "deleteTab");
    }

}
