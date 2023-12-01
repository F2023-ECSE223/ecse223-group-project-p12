package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class AddGuestPopUpController {

    @FXML
    private Button cancelCreateEmployeeButton;

    @FXML
    private TextField createEmailField;

    @FXML
    private Button createEmployeeButton;

    @FXML
    private Label createErrorMessage;

    @FXML
    private TextField createNameField;

    @FXML
    private TextField createPasswordField;

    @FXML
    private TextField createPhoneNumberField;

    @FXML
    private GridPane popupCreateEmployee;

    @FXML
    private ResourceBundle resources;

    @FXML
    void cancelCreateEmployee(ActionEvent event) {
      createEmailField.clear();
      createPhoneNumberField.clear();
      createNameField.clear();
      createPasswordField.clear();
      createErrorMessage.setText("");
      AssetPlusFXMLView.getInstance().closePopUpWindow();


    }

    @FXML
    void createEmployee(ActionEvent event) {
      
      String err = AssetPlusFeatureSet1Controller.addEmployeeOrGuest(createEmailField.getText(), createPasswordField.getText(), createNameField.getText(), createPhoneNumberField.getText(), false);
        if (err.isEmpty()) {
            createEmailField.clear();
            createPhoneNumberField.clear();
            createNameField.clear();
            createPasswordField.clear();
            createErrorMessage.setText("");
            ViewUtils.callController("");
            AssetPlusFXMLView.getInstance().closePopUpWindow();
        } else {
            createErrorMessage.setText(translateErrorMessage(err));
        }
    }

    @FXML
    void initialize() {
      resources = AssetPlusFXMLView.getInstance().getBundle();

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
