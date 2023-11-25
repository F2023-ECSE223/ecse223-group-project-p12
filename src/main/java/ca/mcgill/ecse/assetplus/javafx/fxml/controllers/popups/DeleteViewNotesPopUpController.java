package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class DeleteViewNotesPopUpController {

    private int ticketId;
    private String imageURL;

    @FXML
    private Label urlText;

    @FXML
    private VBox imageToDelete;
    
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
      
      Rectangle rectangle = new Rectangle(0, 0, 80, 80);
      rectangle.setArcWidth(20.0);
      rectangle.setArcHeight(20.0);
      
      Image image = new Image(imageURL, 80, 80, true, true);
      // If the image was loaded without exceptions, consider it valid
      ImagePattern pattern;
      if (image.isError() == false) {
          pattern = new ImagePattern(image);
      }
      else {
          pattern = new ImagePattern(new Image("ca/mcgill/ecse/assetplus/javafx/resources/Images/warning.png", 200, 200, true, true));
          
      }
      rectangle.setFill(pattern);
      

      imageToDelete.getChildren().add(rectangle);
    }
}
