package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.controller.TOGuest;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.EmployeesController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.Guest;
import ca.mcgill.ecse.assetplus.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ModifyGuestPopUpController {

    @FXML
    private TextField modifyEmailField;

    @FXML
    private Label modifyErrorMessage;

    @FXML
    private TextField modifyNameField;

    @FXML
    private TextField modifyPasswordField;

    @FXML
    private TextField modifyPhoneNumberField;

    @FXML
    private ResourceBundle resources;

    public String aEmail;

    @FXML
    void cancelModifyEmployee(ActionEvent event) {
        AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void modifyEmployee(ActionEvent event) {
        String err = AssetPlusFeatureSet1Controller.updateEmployeeOrGuest(modifyEmailField.getText(), modifyPasswordField.getText(), modifyNameField.getText(), modifyPhoneNumberField.getText());
        if (err.isEmpty()) {
            modifyErrorMessage.setText("");
            ViewUtils.callController("");
            AssetPlusFXMLView.getInstance().closePopUpWindow();
        } else {
            modifyErrorMessage.setText(translateErrorMessage(err));
        }
    }

    @FXML
    void initialize() {
        resources = AssetPlusFXMLView.getInstance().getBundle();
        aEmail = EmployeesController.guestEmail;
        ToGuest toGuest = AssetPlusFeatureTOController.convertFromGuest(aEmail);
        modifyEmailField.setText(toGuest.getEmail());
        modifyPasswordField.setText(toGuest.getPassword());
        modifyNameField.setText(toGuest.getName());
        modifyPhoneNumberField.setText(toGuest.getPhoneNumber());
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

}