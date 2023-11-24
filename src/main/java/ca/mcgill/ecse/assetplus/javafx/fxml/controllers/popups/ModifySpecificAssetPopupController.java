package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class ModifySpecificAssetPopupController {

    @FXML
    private TextField assetNumber;

    @FXML
    private ComboBox<?> assetTypes;

    @FXML
    private Button cancelAssetBtn;

    @FXML
    private Button createAssetBtn;

    @FXML
    private DatePicker dateChoice;

    @FXML
    private Rectangle fieldBg;

    @FXML
    private ComboBox<?> floorChoice;

    @FXML
    private TextField lifeExpectancy;

    @FXML
    private VBox lifeExpectancyBox;

    @FXML
    private ComboBox<?> roomChoice;

    @FXML
    private Label topPopups;

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void create(ActionEvent event) {

    }

    @FXML
    void handleAssetType(ActionEvent event) {

    }

}
