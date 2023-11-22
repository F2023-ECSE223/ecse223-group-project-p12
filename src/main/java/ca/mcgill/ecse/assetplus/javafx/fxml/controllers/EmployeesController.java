package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.TOEmployee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.*;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.resources.languages.*;
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
    private GridPane popupCreateEmployee;

    @FXML
    private TextField ticketEmployeeField;

    @FXML
    private FlowPane viewAllEmployees;

    @FXML
    void AddEmployee(ActionEvent event) {

    }

    @FXML
    void cancelCreateEmployee(ActionEvent event) {

    }

    @FXML
    void createEmployee(ActionEvent event) {

    }

    @FXML
    void initialize() {
        showEmployees(ViewUtils.getAllEmployees());
    }

    private void showEmployees(List<TOEmployee> employees) {
        ResourceBundle resourceBundle = AssetPlusFXMLView.getInstance().getLanguage().equalsIgnoreCase("en") ? ResourceBundle.getBundle("Bundle_en.properties") : ResourceBundle.getBundle("Bundle_fr.properties");

        viewAllEmployees.getChildren().clear();
        for (TOEmployee employee : employees) {
            GridPane gridPane = new GridPane();
            gridPane.setMinSize(218, 249);
            gridPane.setPadding(new Insets(10, 15, 10, 15));
            gridPane.setAlignment(Pos.CENTER_LEFT);
            gridPane.setVgap(10);
            gridPane.setStyle("-fx-background-color:#f8f7ff;" +  
            "-fx-background-radius: 10px;");
            
            
            VBox vbox1 = new VBox();
            vbox1.setAlignment(Pos.CENTER_LEFT);
            Label titleName = new Label(resourceBundle.getString("Name"));
            Label Name = new Label(employee.getName());
            vbox1.getChildren().addAll(titleName, Name);
            gridPane.add(vbox1, 0, 0);

            VBox vbox2 = new VBox();
            vbox2.setAlignment(Pos.CENTER_LEFT);
            Label titlePhoneNumber = new Label(resourceBundle.getString("PhoneNumber"));
            Label phoneNumber = new Label(employee.getPhoneNumber());
            vbox2.getChildren().addAll(titlePhoneNumber, phoneNumber);
            gridPane.add(vbox2, 0, 1);

            VBox vbox3 = new VBox();
            vbox3.setAlignment(Pos.CENTER_LEFT);
            Label titleEmail = new Label(resourceBundle.getString("Email"));
            Label email = new Label(employee.getEmail());
            vbox3.getChildren().addAll(titleEmail, email);
            gridPane.add(vbox3, 0, 2);

            VBox vbox4 = new VBox();
            vbox4.setAlignment(Pos.CENTER_LEFT);
            Label titleTickets = new Label(resourceBundle.getString("TicketsAssigned"));
            Label tickets = new Label(employee.getTicketFixed().toString());
            vbox4.getChildren().addAll(titleTickets, tickets);
            gridPane.add(vbox4, 0, 3);

            viewAllEmployees.getChildren().add(gridPane);
        }

    }

}
