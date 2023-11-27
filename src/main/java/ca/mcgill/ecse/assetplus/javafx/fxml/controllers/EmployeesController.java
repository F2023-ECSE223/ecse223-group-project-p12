package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

//import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
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
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.Guest;
import ca.mcgill.ecse.assetplus.model.User;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet2Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureUtility;
import ca.mcgill.ecse.assetplus.controller.TOGuest;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.AddEmployeePopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.DeleteEmployeePopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ModifyEmployeePopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.AddGuestPopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.DeleteGuestPopUpController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups.ModifyGuestPopUpController;

public class EmployeesController {

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private ResourceBundle resources;

    @FXML
    private VBox TopContent;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private TextField ticketEmployeeField;

    @FXML
    private FlowPane viewAllEmployees;

    @FXML
    private Label showEmployeeError;

    public static String email;

    @FXML
    private Button addGuestButton;

    @FXML
    private TextField ticketGuestField;

    @FXML
    private FlowPane viewAllGuests;

    @FXML
    private Label showGuestError;

    public static String guestEmail;


    @FXML
    void AddEmployee(ActionEvent event) {
        System.out.println("add");
        AddEmployeePopUpController controller = (AddEmployeePopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AddEmployeePopUp.fxml", "Add Employee");

    }

    @FXML
    void modifyEmployeePopup(ActionEvent event, String aEmail) {
        System.out.println("modify");
        email = aEmail;
        ModifyEmployeePopUpController controller = (ModifyEmployeePopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ModifyEmployeePopUp.fxml", "Modify Employee");
        
    }

    @FXML
    void deleteEmployeePopup(ActionEvent event, String aEmail) {
        System.out.println("delete");
        email = aEmail;
        DeleteEmployeePopUpController controller = (DeleteEmployeePopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/DeleteEmployeePopUp.fxml", "Delete Employee");
        
    }

    @FXML
    void AddGuest(ActionEvent event) {
        System.out.println("add");
        AddGuestPopUpController controller = (AddGuestPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/AddGuestPopUp.fxml", "Add Guest");

    }

    @FXML
    void modifyGuestPopup(ActionEvent event, String aEmail) {
        System.out.println("modify");
        guestEmail = aEmail;
        ModifyGuestPopUpController controller = (ModifyGuestPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ModifyGuestPopUp.fxml", "Modify Guest");
        
    }

    @FXML
    void deleteGuestPopup(ActionEvent event, String aEmail) {
        System.out.println("delete");
        guestEmail = aEmail;
        DeleteGuestPopUpController controller = (DeleteGuestPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/DeleteGuestPopUp.fxml", "Delete GUest");
        
    }

    @FXML
    void initialize() {
        resources = AssetPlusFXMLView.getInstance().getBundle();
        showEmployees(AssetPlusFeatureTOController.getAllEmployees());
        showGuests(AssetPlusFeatureTOController.getAllGuests());
        viewAllEmployees.addEventHandler(AssetPlusFXMLView.REFRESH_EVENT, e -> {
            showEmployees(AssetPlusFeatureTOController.getAllEmployees());
        });

        viewAllGuests.addEventHandler(AssetPlusFXMLView.REFRESH_EVENT, e -> {
            showGuests(AssetPlusFeatureTOController.getAllGuests());
        });

        // let the application be aware of the refreshable node
        AssetPlusFXMLView.getInstance().registerRefreshEvent(viewAllEmployees);
        AssetPlusFXMLView.getInstance().registerRefreshEvent(viewAllGuests);
    }

    @FXML
    void showSearchedEmployee(ActionEvent event) {
        if (ticketEmployeeField.getText().isEmpty()) {
            showEmployeeError.setText("");
            showEmployees(AssetPlusFeatureTOController.getAllEmployees());
        } else {

            String err = AssetPlusFeatureUtility.isExistingUser(ticketEmployeeField.getText(), "");
            if (err.isEmpty()) {
                showEmployeeError.setText("");
                Employee employee = (Employee) User.getWithEmail(ticketEmployeeField.getText());
                TOEmployee toEmployee = AssetPlusFeatureTOController.convertFromEmployee(employee);
                List<TOEmployee> employees = new ArrayList<>();
                employees.add(toEmployee);
                showEmployees(employees);
            } else {
                showEmployeeError.setText(translateErrorMessage(err));
                viewAllEmployees.getChildren().clear();
            }
        }
    }

    @FXML
    void showSearchedGuest(ActionEvent event) {
        if (ticketGuestField.getText().isEmpty()) {
            showGuestError.setText("");
            showGuests(AssetPlusFeatureTOController.getAllGuests());
        } else {

            String err = AssetPlusFeatureUtility.isExistingUser(ticketGuestField.getText(), "");
            if (err.isEmpty()) {
                showGuestError.setText("");
                Guest guest = (Guest) User.getWithEmail(ticketGuestField.getText());
                TOGuest toGuest = AssetPlusFeatureTOController.convertFromGuest(guest);
                List<TOGuest> guests = new ArrayList<>();
                guests.add(toGuest);
                showGuests(guests);
            } else {
                showGuestError.setText(translateErrorMessage(err));
                viewAllGuests.getChildren().clear();
            }
        }
    }

    private void showEmployees(List<TOEmployee> employees) {

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
            Label titleName = new Label(resources.getString("key.Name"));
            titleName.setStyle("-fx-text-fill: #8768F2;");
            Label Name = new Label(employee.getName());
            Name.setStyle("-fx-background-color:white;" + "-fx-background-radius:5px;");
            Name.setMinWidth(189);
            vbox1.getChildren().addAll(titleName, Name);
            gridPane.add(vbox1, 0, 0);

            VBox vbox2 = new VBox();
            vbox2.setAlignment(Pos.CENTER_LEFT);
            Label titlePhoneNumber = new Label(resources.getString("key.PhoneNumber"));
            titlePhoneNumber.setStyle("-fx-text-fill: #8768F2;");
            Label phoneNumber = new Label(employee.getPhoneNumber());
            phoneNumber.setStyle("-fx-background-color:white;" + "-fx-background-radius:5px;");
            phoneNumber.setMinWidth(189);
            vbox2.getChildren().addAll(titlePhoneNumber, phoneNumber);
            gridPane.add(vbox2, 0, 1);

            VBox vbox3 = new VBox();
            vbox3.setAlignment(Pos.CENTER_LEFT);
            Label titleEmail = new Label(resources.getString("key.Email"));
            titleEmail.setStyle("-fx-text-fill: #8768F2;");
            Label email = new Label(employee.getEmail());
            email.setStyle("-fx-background-color:white;" + "-fx-background-radius:5px;");
            email.setMinWidth(189);
            vbox3.getChildren().addAll(titleEmail, email);
            gridPane.add(vbox3, 0, 2);

            VBox vbox4 = new VBox();
            vbox4.setAlignment(Pos.CENTER_LEFT);
            Label titleTickets = new Label(resources.getString("key.TicketsAssigned"));
            titleTickets.setStyle("-fx-text-fill: #8768F2;");
            Label tickets = new Label(employee.getTicketFixed().toString());
            tickets.setStyle("-fx-background-color:white;" + "-fx-background-radius:5px;");
            tickets.setMinWidth(189);
            vbox4.getChildren().addAll(titleTickets, tickets);
            gridPane.add(vbox4, 0, 3);
            
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            Button modify = new Button(resources.getString("key.Modify"));
            modify.setStyle("-fx-text-fill: white;" + "-fx-background-color: #8768F2;" + "-fx-background-radius: 10px;" + "-fx-padding: 5px 10px 5px 10px");
            modify.setOnAction(e -> modifyEmployeePopup(e,employee.getEmail()));
            Button deleteButton = new Button(resources.getString("key.Delete"));
            deleteButton.setStyle("-fx-background-color: #a30d11;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");
            deleteButton.setOnAction(e -> deleteEmployeePopup(e, employee.getEmail()));
            Pane pane = new Pane();
            pane.setMinWidth(20);
            hBox.getChildren().addAll(deleteButton, pane, modify);
            gridPane.add(hBox, 0, 4);

            DropShadow ds = new DropShadow();
            ds.setOffsetX(3.0);
            ds.setOffsetY(3.0);
            ds.setColor(Color.GRAY);

            gridPane.setEffect(ds);


            viewAllEmployees.getChildren().add(gridPane);
        }

    }

    private void showGuests(List<TOGuest> guests) {

        viewAllGuests.getChildren().clear();
        for (TOGuest guest : guests) {
            GridPane gridPane = new GridPane();
            gridPane.setMinSize(166, 220);
            gridPane.setPadding(new Insets(10, 10, 10, 10));
            gridPane.setVgap(10);
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setStyle("-fx-background-color:#f8f7ff;" +  
            "-fx-background-radius: 10px;");
            
            
            VBox vbox1 = new VBox();
            vbox1.setAlignment(Pos.CENTER_LEFT);
            Label titleName = new Label(resources.getString("key.Name"));
            titleName.setStyle("-fx-text-fill: #8768F2;");
            Label Name = new Label(guest.getName());
            Name.setStyle("-fx-background-color:white;" + "-fx-background-radius:5px;");
            Name.setMinWidth(189);
            vbox1.getChildren().addAll(titleName, Name);
            gridPane.add(vbox1, 0, 0);

            VBox vbox2 = new VBox();
            vbox2.setAlignment(Pos.CENTER_LEFT);
            Label titlePhoneNumber = new Label(resources.getString("key.PhoneNumber"));
            titlePhoneNumber.setStyle("-fx-text-fill: #8768F2;");
            Label phoneNumber = new Label(guest.getPhoneNumber());
            phoneNumber.setStyle("-fx-background-color:white;" + "-fx-background-radius:5px;");
            phoneNumber.setMinWidth(189);
            vbox2.getChildren().addAll(titlePhoneNumber, phoneNumber);
            gridPane.add(vbox2, 0, 1);

            VBox vbox3 = new VBox();
            vbox3.setAlignment(Pos.CENTER_LEFT);
            Label titleEmail = new Label(resources.getString("key.Email"));
            titleEmail.setStyle("-fx-text-fill: #8768F2;");
            Label email = new Label(guest.getEmail());
            email.setStyle("-fx-background-color:white;" + "-fx-background-radius:5px;");
            email.setMinWidth(189);
            vbox3.getChildren().addAll(titleEmail, email);
            gridPane.add(vbox3, 0, 2);

            VBox vbox4 = new VBox();
            vbox4.setAlignment(Pos.CENTER_LEFT);
            gridPane.add(vbox4, 0, 3);
            
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            Button modify = new Button(resources.getString("key.Modify"));
            modify.setStyle("-fx-text-fill: white;" + "-fx-background-color: #8768F2;" + "-fx-background-radius: 10px;" + "-fx-padding: 5px 10px 5px 10px");
            modify.setOnAction(e -> modifyGuestPopup(e,guest.getEmail()));
            Button deleteButton = new Button(resources.getString("key.Delete"));
            deleteButton.setStyle("-fx-background-color: #a30d11;" + "-fx-text-fill: white;" + "-fx-font-weight: bold;" + "-fx-background-radius: 10px;");
            deleteButton.setOnAction(e -> deleteGuestPopup(e, guest.getEmail()));
            Pane pane = new Pane();
            pane.setMinWidth(20);
            hBox.getChildren().addAll(deleteButton, pane, modify);
            gridPane.add(hBox, 0, 4);

            DropShadow ds = new DropShadow();
            ds.setOffsetX(3.0);
            ds.setOffsetY(3.0);
            ds.setColor(Color.GRAY);

            gridPane.setEffect(ds);


            viewAllGuests.getChildren().add(gridPane);
        }

    }

    public String translateErrorMessage(String err) {
        resources = AssetPlusFXMLView.getInstance().getBundle();
        switch (err) {
            case "Email cannot be empty":
                return resources.getString("key.EmailCannotBeEmpty");
            case "Email cannot be manager@ap.com":
                return resources.getString("key.EmailCannotBemanager@apcom");
            case "Email already linked to an guest account":
                return resources.getString("key.EmailAlreadyLinkedToAnGuestAccount");
            case "Email already linked to an employee account":
                return resources.getString("key.EmailAlreadyLinkedToAnEmployeeAccount");
            case "Email domain cannot be @ap.com":
                return resources.getString("key.EmailDomainCannotBe@apcom");
            case "Email must not contain any spaces":
                return resources.getString("key.EmailMustNotContainAnySpaces");
            case "Invalid email":
                return resources.getString("key.InvalidEmail");
            case "Email domain must be @ap.com":
                return resources.getString("key.EmailDomainMustBe@apcom");
            case "Password cannot be empty":
                return resources.getString("key.PasswordCannotBeEmpty");
            case "Password must be at least four characters long":
                return resources.getString("key.PasswordMustBeAtLeastFourCharactersLong");
            case "Password must contain one character out of !#$":
                return resources.getString("key.PasswordMustContainOneCharacterOutOf!#$");
            case "Password must contain one lower-case character":
                return resources.getString("key.PasswordMustContainOneLower-caseCharacter");
            case "Password must contain one upper-case character":
                return resources.getString("key.PasswordMustContainOneUpper-caseCharacter");
            case "The ticket raiser does not exist":
                return resources.getString("key.TheTicketRaiserDoesNotExist");
            case "Error: user not found":
                return resources.getString("key.ErrorUserNotFound");
            default:
                return resources.getString("key.Error");
        }
    }

}
