package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.application.AssetPlusApplication;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.model.Manager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SettingsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox TopContent;

    @FXML
    private Button saveSettingsButton;

    @FXML
    private RadioButton enRadioButton;

    @FXML
    private RadioButton frRadioButton;

    @FXML
    private TextField updateManagerPasswordField;

    @FXML
    private Label updateManagerPasswordError;

    @FXML
    private Label updateManagerPasswordSuccess;

    private ToggleGroup languageToggleGroup;

    @FXML
    void initialize() {
        languageToggleGroup = new ToggleGroup();
        enRadioButton.setToggleGroup(languageToggleGroup);
        frRadioButton.setToggleGroup(languageToggleGroup);

        String language = AssetPlusFXMLView.getInstance().getLanguage();
        if (language.equals("en")) {
            enRadioButton.setSelected(true);
        } else if (language.equals("fr")) {
            frRadioButton.setSelected(true);
        }

        Manager manager = AssetPlusApplication.getAssetPlus().getManager();
        updateManagerPasswordField.setText(manager.getPassword());
    }

    @FXML
    void saveSettings(ActionEvent event) {
        RadioButton selectedButton = (RadioButton) languageToggleGroup.getSelectedToggle();

        if (selectedButton != null) {
            String language = selectedButton.getText().toLowerCase().substring(0, 2);
            AssetPlusFXMLView.getInstance().setLanguage(language);
        }
    }

    @FXML
    void updateManagerPassword(ActionEvent event) {
        String err = AssetPlusFeatureSet1Controller.updateManager(updateManagerPasswordField.getText());
        if (err.isEmpty()) {
            updateManagerPasswordError.setText("");
            updateManagerPasswordSuccess.setText(resources.getString("key.PasswordChangedSuccesfuly"));
        } else {
            updateManagerPasswordError.setText(translateErrorMessage(err));
            updateManagerPasswordSuccess.setText("");
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
