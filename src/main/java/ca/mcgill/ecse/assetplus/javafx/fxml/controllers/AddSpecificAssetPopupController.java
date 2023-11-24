package ca.mcgill.ecse.assetplus.javafx.fxml.controllers;

import java.nio.file.AtomicMoveNotSupportedException;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;


public class AddSpecificAssetPopupController {

    @FXML
    private TextField assetNumber;

    @FXML
    private ChoiceBox<?> assetTypes;

    @FXML
    private Button cancelAssetBtn;

    @FXML
    private Button createAssetBtn;

    @FXML
    private DatePicker dateChoice;

    @FXML
    private Rectangle fieldBg;

    @FXML
    private ChoiceBox<?> floorChoice;

    @FXML
    private TextField lifeExpectancy;

    @FXML
    private ChoiceBox<?> roomChoice;

    @FXML
    private Label topPopups;

    @FXML
    void cancel(ActionEvent event) {
      System.out.println("is this cancelling?");
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void create(ActionEvent event) {

    }

}
