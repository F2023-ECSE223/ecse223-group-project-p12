package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.TOEmployee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.util.*;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.resources.languages.*;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;

public class EmployeesController {

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox TopContent;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button cancelCreateEmployeeButton;

    @FXML
    private Button createEmployeeButton;

    @FXML
    private TabPane employeeOptions;

    @FXML
    private TextField modifyEmailField;

    @FXML
    private TextField modifyNameField;

    @FXML
    private TextField modifyPhoneNumberField;

    @FXML
    private GridPane popupCreateEmployee;

    @FXML
    private TextField ticketEmployeeField;

    @FXML
    private FlowPane viewAllEmployees;


    @FXML
    void AddEmployee(ActionEvent event) {
        employeeOptions.getSelectionModel().select(1);

    }

    @FXML
    void cancelCreateEmployee(ActionEvent event) {
        modifyEmailField.clear();
        modifyPhoneNumberField.clear();
        modifyNameField.clear();
        employeeOptions.getSelectionModel().select(0);
    }

    @FXML
    void createEmployee(ActionEvent event) {
        Employee employee = new Employee(modifyNameField.getText(), modifyPhoneNumberField.getText(), "abcd", modifyEmailField.getText(), AssetPlusApplication.getAssetPlus());
        AssetPlusApplication.getAssetPlus().addEmployee(employee);
        modifyEmailField.clear();
        modifyPhoneNumberField.clear();
        modifyNameField.clear();
        employeeOptions.getSelectionModel().select(0);
        initialize();   
    }

    @FXML
    void modifyEmployeePopup(ActionEvent event) {
        employeeOptions.getSelectionModel().select(2);
    }

    @FXML
    void deleteEmployeePopup(ActionEvent event) {
        employeeOptions.getSelectionModel().select(3);
    }

    @FXML
    void initialize() {
        showEmployees(ViewUtils.getAllEmployees());
    }

    private void showEmployees(List<TOEmployee> employees) {
        //ResourceBundle resourceBundle = AssetPlusFXMLView.getInstance().getLanguage().equalsIgnoreCase("en") ? ResourceBundle.getBundle("Bundle_en.properties") : ResourceBundle.getBundle("Bundle_fr.properties");

        viewAllEmployees.getChildren().clear();
        for (TOEmployee employee : employees) {
            GridPane gridPane = new GridPane();
            gridPane.setMinSize(166, 220);
            gridPane.setPadding(new Insets(10, 10, 10, 10));
            gridPane.setVgap(10);
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setStyle("-fx-background-color:#f8f7ff;" +  
            "-fx-background-radius: 10px;");
            
            
            VBox vbox1 = new VBox();
            vbox1.setAlignment(Pos.CENTER_LEFT);
            Label titleName = new Label("Name");
            titleName.setStyle("-fx-text-fill: #8768F2;");
            Label Name = new Label(employee.getName());
            Name.setStyle("-fx-background-color:white;" + "-fx-background-radius:5px;");
            Name.setMinWidth(189);
            vbox1.getChildren().addAll(titleName, Name);
            gridPane.add(vbox1, 0, 0);

            VBox vbox2 = new VBox();
            vbox2.setAlignment(Pos.CENTER_LEFT);
            Label titlePhoneNumber = new Label("PhoneNumber");
            titlePhoneNumber.setStyle("-fx-text-fill: #8768F2;");
            Label phoneNumber = new Label(employee.getPhoneNumber());
            phoneNumber.setStyle("-fx-background-color:white;" + "-fx-background-radius:5px;");
            phoneNumber.setMinWidth(189);
            vbox2.getChildren().addAll(titlePhoneNumber, phoneNumber);
            gridPane.add(vbox2, 0, 1);

            VBox vbox3 = new VBox();
            vbox3.setAlignment(Pos.CENTER_LEFT);
            Label titleEmail = new Label("Email");
            titleEmail.setStyle("-fx-text-fill: #8768F2;");
            Label email = new Label(employee.getEmail());
            email.setStyle("-fx-background-color:white;" + "-fx-background-radius:5px;");
            email.setMinWidth(189);
            vbox3.getChildren().addAll(titleEmail, email);
            gridPane.add(vbox3, 0, 2);

            VBox vbox4 = new VBox();
            vbox4.setAlignment(Pos.CENTER_LEFT);
            Label titleTickets = new Label("TicketsAssigned");
            titleTickets.setStyle("-fx-text-fill: #8768F2;");
            Label tickets = new Label(employee.getTicketFixed().toString());
            tickets.setStyle("-fx-background-color:white;" + "-fx-background-radius:5px;");
            tickets.setMinWidth(189);
            vbox4.getChildren().addAll(titleTickets, tickets);
            gridPane.add(vbox4, 0, 3);
            
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            Button modify = new Button("Modify");
            modify.setStyle("-fx-text-fill: white;" + "-fx-background-color: #8768F2;" + "-fx-background-radius: 10px;" + "-fx-padding: 5px 10px 5px 10px");
            modify.setOnAction(e -> modifyEmployeePopup(e));
            Button deleteButton = new Button("Delete");
            deleteButton.setStyle("-fx-background-color: #a30d11;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");
            deleteButton.setOnAction(e -> deleteEmployeePopup(e));
            Pane pane = new Pane();
            pane.setMinWidth(20);
            hBox.getChildren().addAll(modify, pane, deleteButton);
            gridPane.add(hBox, 0, 4);

            DropShadow ds = new DropShadow();
            ds.setOffsetX(3.0);
            ds.setOffsetY(3.0);
            ds.setColor(Color.GRAY);

            gridPane.setEffect(ds);


            viewAllEmployees.getChildren().add(gridPane);
        }

    }

}
