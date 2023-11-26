package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;

import ca.mcgill.ecse.assetplus.controller.TOMaintenanceTicket;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;

public class ViewTicketPopUpController {

    @FXML
    private Label approvalRequiredLabel;

    @FXML
    private Label assetNumberLabel;

    @FXML
    private Label assetTypeLabel;

    @FXML
    private Label assigneeLabel;

    @FXML
    private Label priorityLabel;

    @FXML
    private Label raisedOnDateLabel;

    @FXML
    private Label reporterLabel;

    @FXML
    private Label ticketStatusLabel;

    @FXML
    private Label timeEstimateLabel;

    private int ticketId;
    private TOMaintenanceTicket ticket;

    public void setTicketId(int ticketId) {
      this.ticketId = ticketId;
      initializeLabels();
    }

    private void initializeLabels() {
      ticket = ViewUtils.getTicket(ticketId);
      if (ticket.getApprovalRequired()) {
        approvalRequiredLabel.setText("Yes");
      } else {
        approvalRequiredLabel.setText("No");
      }

      assetTypeLabel.setText(ticket.getAssetName());
      assigneeLabel.setText(ViewUtils.getUsername(ticket.getFixedByEmail()));
      priorityLabel.setText(ticket.getPriority());
      raisedOnDateLabel.setText(ticket.getRaisedOnDate().toString());
      reporterLabel.setText(ViewUtils.getUsername(ticket.getRaisedByEmail()));
      ticketStatusLabel.setText(ticket.getStatus());
      timeEstimateLabel.setText(ticket.getTimeToResolve());
    }
}
