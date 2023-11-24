package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class DeleteViewImagesPopUpController {

    private int ticketId;
    private String imageURL;

    @FXML
    private Label urlText;
    
    @FXML
    private Button cancelButton;

    @FXML
    private Button deleteButton;

    @FXML
    void initialize() {
        ticketId = -1;
        imageURL = "";
    }

    @FXML
    void Cancel(ActionEvent event) {
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }
    
    @FXML
    void DeleteImage(ActionEvent event) {
      AssetPlusFeatureSet5Controller.deleteImageFromMaintenanceTicket(imageURL, ticketId);
      ViewUtils.callController("");
      AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    public void setTicketIdAndURL(int id, String url) {
      ticketId = id;
      imageURL = url;
      urlText.setText(url);
    }
}
