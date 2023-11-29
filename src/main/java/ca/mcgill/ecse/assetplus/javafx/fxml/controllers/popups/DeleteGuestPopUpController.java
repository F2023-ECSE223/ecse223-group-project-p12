package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.EmployeesController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;

public class DeleteGuestPopUpController {

    @FXML
    private TextField employeeNameDelete;

    public static String aEmail;

    @FXML
    void cancelDeleteEmployee(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void deleteEmployee(ActionEvent event) {
      AssetPlusFeatureSet6Controller.deleteEmployeeOrGuest(aEmail);
      ViewUtils.callController("");
      AssetPlusFXMLView.getInstance().closePopUpWindow();

    }

    @FXML
    void initialize() {
      aEmail = EmployeesController.guestEmail;
      employeeNameDelete.setText(aEmail);
      employeeNameDelete.setEditable(false);
      employeeNameDelete.setFocusTraversable(false);
      employeeNameDelete.setStyle("-fx-text-fill: #333333;");

    }

}
