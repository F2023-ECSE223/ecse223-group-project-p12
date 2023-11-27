package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet1Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureTOController;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.EmployeesController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import ca.mcgill.ecse.assetplus.model.Employee;
import ca.mcgill.ecse.assetplus.model.Guest;
import ca.mcgill.ecse.assetplus.model.User;

public class DeleteGuestPopUpController {

    @FXML
    private Label employeeNameDelete;

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
      Guest guest = (Guest) User.getWithEmail(aEmail);
      employeeNameDelete.setText(aEmail);

    }

}