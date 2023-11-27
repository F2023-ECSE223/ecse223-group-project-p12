package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceNote;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.TicketStatusController;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;

public class ViewTicketPopUpController {

  @FXML
  private TextField approvalRequiredField;

  @FXML
  private TextField assetNumberField;

  @FXML
  private TextField assetTypeField;

  @FXML
  private TextField assigneeField;

  @FXML
  private TextArea descriptionField;

  @FXML
  private Rectangle fieldBg;

  @FXML
  private Button priorityButton;

  @FXML
  private TextField raisedOnDateField;

  @FXML
  private Button statusButton;

  @FXML
  private TextField ticketNumberField;

  @FXML
  private TextField ticketRaiserField;

  @FXML
  private TextField timeEstimateField;

    private int ticketId;

    private TOMaintenanceTicket ticket;

    public void setTicketId(int ticketId) {
      this.ticketId = ticketId;
      initializeLabels();
    }

    private void initializeLabels() {
      ticket = ViewUtils.getTicket(ticketId);
      String text = ticket.getApprovalRequired() ? "Yes" : "No";
      approvalRequiredField.setText(text);

      assetNumberField.setText(String.valueOf(ViewUtils.getSpecificAssetFromTicket(ViewUtils.getTicket(ticketId))));
      assetTypeField.setText(ticket.getAssetName());
      descriptionField.setText(ticket.getDescription());

      priorityButton.setText(ticket.getPriority());
      if (!ticket.getPriority().isEmpty()) {
        priorityButton.setStyle(getPriorityStyle(ticket.getPriority()));
      } else {
        priorityButton.setVisible(false);
      }

      statusButton.setText(ticket.getStatus());
      statusButton.setStyle(TicketStatusController.getStyle(ticket.getStatus()));

      raisedOnDateField.setText(ticket.getRaisedOnDate().toString());
      ticketNumberField.setText(String.valueOf(ticket.getId()));
      ticketRaiserField.setText(ticket.getRaisedByEmail());
      assigneeField.setText(ticket.getFixedByEmail());
      timeEstimateField.setText(ticket.getTimeToResolve());
    }

    private String getPriorityStyle(String priority) {
      switch (priority) {
        case "Low":
          return "-fx-background-color: #D3D3D3; -fx-text-fill: #696969; fx-background-radius: 50px";
        case "Normal":
          return "-fx-background-color: #EBF9F1; -fx-text-fill: #1F9254; fx-background-radius: 50px";
        case "Urgent":
          return "-fx-background-color: #FBE7E8; -fx-text-fill: #A30D11; fx-background-radius: 50px";
      }
      return "";
    }

}

