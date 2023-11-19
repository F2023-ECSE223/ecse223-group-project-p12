package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.util.Locale;
import java.util.ResourceBundle;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
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
    }

    @FXML
    void saveSettings(ActionEvent event) {
        RadioButton selectedButton = (RadioButton) languageToggleGroup.getSelectedToggle();

        if (selectedButton != null) {
            String language = selectedButton.getText().toLowerCase().substring(0, 2);
            AssetPlusFXMLView.getInstance().setLanguage(language);
        }
    }

}
