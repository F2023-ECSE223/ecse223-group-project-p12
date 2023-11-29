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
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;
import java.util.*;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
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
    int gradientRotation;
    List<Pair<String, String>> colorPairs = new ArrayList<>();

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

        gradientRotation = 0;
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
                TOEmployee toEmployee;
                try {
                    toEmployee = AssetPlusFeatureTOController.convertFromEmployee(ticketEmployeeField.getText());
                } catch (Exception e) {
                    showEmployeeError.setText(resources.getString("key.EmployeeNotFound"));
                    viewAllEmployees.getChildren().clear();
                    return;
                }
                List<TOEmployee> employees = new ArrayList<>();
                employees.add(toEmployee);
                showEmployees(employees);
            } else {
                showEmployeeError.setText(resources.getString("key.EmployeeNotFound"));
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
                TOGuest toGuest;
                try{
                    toGuest = AssetPlusFeatureTOController.convertFromGuest(ticketGuestField.getText());
                } catch (Exception e) {
                    showGuestError.setText(resources.getString("key.GuestNotFound"));
                    viewAllGuests.getChildren().clear();
                    return;
                }
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
        gradientRotation = 0;
        viewAllEmployees.getChildren().clear();
        for (TOEmployee employee : employees) {
            VBox container = new VBox();
            container.setMinSize(166, 190);
            container.setPadding(new Insets(10, 10, 10, 10));
            container.setSpacing(15);
            container.setAlignment(Pos.TOP_CENTER);
            container.setStyle("-fx-background-color:#8768F211;" +  
            "-fx-background-radius: 20px;");
            

            HBox hBox1 = new HBox();
            hBox1.setAlignment(Pos.CENTER_LEFT);
            hBox1.setSpacing(15);
            Circle iconEmployee = new Circle();
            iconEmployee.setRadius(15);
            iconEmployee.getStyleClass().add("userCircle");
            

            // Create the linear gradient
            LinearGradient linearGradient = getGradient();
            iconEmployee.setFill(linearGradient);
            Label name = new Label(employee.getName());
            name.setStyle("-fx-font-size: 14px");
            hBox1.getChildren().addAll(iconEmployee, name);

            container.getChildren().add(hBox1);

            HBox hBox2 = new HBox();
            hBox2.setAlignment(Pos.CENTER_LEFT);
            hBox2.setSpacing(5);
            Button iconPhone = new Button();
            iconPhone.getStyleClass().add("icon-phone");
            Label phoneNumber = new Label(employee.getPhoneNumber());
            hBox2.getChildren().addAll(iconPhone, phoneNumber);
            container.getChildren().add(hBox2);


            HBox hBox3 = new HBox();
            hBox3.setAlignment(Pos.CENTER_LEFT);
            hBox3.setSpacing(5);
            Button iconEmail = new Button();
            iconEmail.getStyleClass().add("icon-email");
            Label email = new Label(employee.getEmail());
            hBox3.getChildren().addAll(iconEmail, email);
            container.getChildren().add(hBox3);

            VBox vbox4 = new VBox();
            vbox4.setAlignment(Pos.CENTER_LEFT);
            Label titleTickets = new Label(resources.getString("key.TicketsAssigned"));
            titleTickets.setStyle("-fx-text-fill: #8768F2;");
            String str = "";
            for (int i=0; i<employee.getTicketFixed().size(); i++) {
                int ticketId = employee.getTicketFixed().get(i);

                if (i==employee.getTicketFixed().size()-1) 
                    str = str + ticketId;
                else
                    str = str + ticketId + ", ";

            }
            Label tickets = new Label(str);
            tickets.setPadding(new Insets(5));
            tickets.setStyle("-fx-background-color:white;" + "-fx-background-radius:5px;");
            tickets.setMinWidth(189);
            vbox4.getChildren().addAll(titleTickets, tickets);
            container.getChildren().add(vbox4);
            
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            Button modify = new Button();
            modify.getStyleClass().add("icon-pencil");
            modify.setPickOnBounds(true);
            modify.setOnAction(e -> modifyEmployeePopup(e,employee.getEmail()));
            Button deleteButton = new Button();
            deleteButton.getStyleClass().add("icon-trash");
            deleteButton.setPickOnBounds(true);
            deleteButton.setOnAction(e -> deleteEmployeePopup(e, employee.getEmail()));
            Pane pane = new Pane();
            pane.setMinWidth(40);
            hBox.getChildren().addAll(deleteButton, pane, modify);
            container.getChildren().add(hBox);

            DropShadow ds = new DropShadow();
            ds.setOffsetX(3.0);
            ds.setOffsetY(3.0);
            ds.setColor(Color.GRAY);
            //container.setEffect(ds);

            viewAllEmployees.getChildren().add(container);
        }

    }

    private void showGuests(List<TOGuest> guests) {
        gradientRotation = 0;
        viewAllGuests.getChildren().clear();
        for (TOGuest guest : guests) {
            VBox container = new VBox();
            container.setMinSize(166, 150);
            container.setPadding(new Insets(10, 10, 10, 10));
            container.setSpacing(15);
            container.setAlignment(Pos.TOP_CENTER);
            container.setStyle("-fx-background-color:#8768F222;" +  
            "-fx-background-radius: 20px;");
            

            HBox hBox1 = new HBox();
            hBox1.setAlignment(Pos.CENTER_LEFT);
            hBox1.setSpacing(15);
            Circle iconEmployee = new Circle();
            iconEmployee.setRadius(15);
            iconEmployee.getStyleClass().add("userCircle");
            

            // Create the linear gradient
            LinearGradient linearGradient = getGradient();
            iconEmployee.setFill(linearGradient);
            Label name = new Label(guest.getName());
            name.setStyle("-fx-font-size: 14px");
            hBox1.getChildren().addAll(iconEmployee, name);

            container.getChildren().add(hBox1);

            HBox hBox2 = new HBox();
            hBox2.setAlignment(Pos.CENTER_LEFT);
            hBox2.setSpacing(5);
            Button iconPhone = new Button();
            iconPhone.getStyleClass().add("icon-phone");
            Label phoneNumber = new Label(guest.getPhoneNumber());
            hBox2.getChildren().addAll(iconPhone, phoneNumber);
            container.getChildren().add(hBox2);


            HBox hBox3 = new HBox();
            hBox3.setAlignment(Pos.CENTER_LEFT);
            hBox3.setSpacing(5);
            Button iconEmail = new Button();
            iconEmail.getStyleClass().add("icon-email");
            Label email = new Label(guest.getEmail());
            hBox3.getChildren().addAll(iconEmail, email);
            container.getChildren().add(hBox3);
            
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            Button modify = new Button();
            modify.getStyleClass().add("icon-pencil");
            modify.setPickOnBounds(true);
            modify.setOnAction(e -> modifyGuestPopup(e,guest.getEmail()));
            Button deleteButton = new Button();
            deleteButton.getStyleClass().add("icon-trash");
            deleteButton.setPickOnBounds(true);
            deleteButton.setOnAction(e -> deleteGuestPopup(e, guest.getEmail()));
            Pane pane = new Pane();
            pane.setMinWidth(40);
            hBox.getChildren().addAll(deleteButton, pane, modify);
            container.getChildren().add(hBox);

            DropShadow ds = new DropShadow();
            ds.setOffsetX(3.0);
            ds.setOffsetY(3.0);
            ds.setColor(Color.GRAY);
            //container.setEffect(ds);


            viewAllGuests.getChildren().add(container);
        }

    }

    private String translateErrorMessage(String err) {
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

    private LinearGradient getGradient() {
        colorPairs.add(new Pair<>("#A7C957", "#386641"));
        colorPairs.add(new Pair<>("#FBD1A2", "#D84727"));
        colorPairs.add(new Pair<>("#81ADC8", "#1282A2"));
        colorPairs.add(new Pair<>("#F2B7C6", "#CC76A1"));
        colorPairs.add(new Pair<>("#FF8EA8", "#D64045"));
        colorPairs.add(new Pair<>("#FFFFA0", "#D1BF38"));
        
        Pair<String, String> colors = colorPairs.get(gradientRotation % colorPairs.size());
        
        // Create stops for the gradient (color transition points)
        Stop[] stops = new Stop[]{
                new Stop(0, Color.valueOf(colors.getKey())),     // Start color at 0 position
                new Stop(1, Color.valueOf(colors.getValue()))     // End color at 1 position
        };

        gradientRotation++;
        return new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
    }
}
