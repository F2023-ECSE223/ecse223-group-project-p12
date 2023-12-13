package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet4Controller;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet6Controller;

public class DeleteTicketPopUpController {

  private static int ticketId;

  private static TOMaintenanceTicket ticket;

    @FXML
    private Button cancelButton;

    @FXML
    private Button deleteTicketButton;

    @FXML
    private Rectangle fieldBgDelete;

    @FXML
    private TextField ticketNumberField;


    @FXML
    void initialize(){
      ticketNumberField.setEditable(false);
      ticketNumberField.setFocusTraversable(false);
      ticketNumberField.setText(Integer.toString(ticketId));
      ticketNumberField.setText("#"+ticket.getId()+"");
    }

    @FXML
    void cancelClicked(ActionEvent event) {
       AssetPlusFXMLView.getInstance().closePopUpWindow();
    }

    @FXML
    void deleteTicketClicked(ActionEvent event) {
        AssetPlusFeatureSet4Controller.deleteMaintenanceTicket(ticket.getId());
        ViewUtils.callController("");
        ticketNumberField.setText("");
        AssetPlusFXMLView.getInstance().closePopUpWindow();    
    }

    public static void setId(int id){
      ticketId = id;
      ticket = AssetPlusFeatureSet6Controller.getTicket(ticketId);
    }

}
