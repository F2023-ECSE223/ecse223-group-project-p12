package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;


import java.util.ResourceBundle;
import javax.swing.text.View;
import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.controller.TOMaintenanceNote;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;


public class ViewNotesController {
    private int currentTicketNumber;

    @FXML
    private ResourceBundle resources;

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox TopContent;

    @FXML
    private Button addNoteButton;

    @FXML
    private Label numberLabel;

    @FXML
    private Label errorMessage;

    @FXML
    private FlowPane grid;

    @FXML
    public void initialize() {
        currentTicketNumber = -1;

        // the grid listen to the refresh event
        grid.addEventHandler(AssetPlusFXMLView.REFRESH_EVENT, e -> {
            refreshView();
        });

        // let the application be aware of the refreshable node
        AssetPlusFXMLView.getInstance().registerRefreshEvent(grid);
    }

    @FXML
    void AddNote(ActionEvent event) {
        if (currentTicketNumber!=-1) {
            AddNotePopUpController controller = (AddNotePopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ViewNotesAddNotePopUp.fxml", "Add Note");
            if (controller!=null)
                controller.setTicketId(currentTicketNumber);
            else System.out.println("controller null");
        }
        else {
            errorMessage.setVisible(true);
            setErrorMessage("key.ViewNotes_EnterValidTicket");
        }
    }

    private void refreshView() {
        try {
            ObservableList<TOMaintenanceNote> noteList = ViewUtils.getTicketNotes(currentTicketNumber);

            if (noteList != null) {
                errorMessage.setVisible(false);
                showNotes(noteList);
            }
            else {
                errorMessage.setVisible(true);
                setErrorMessage("key.ViewNotes_InvalidTicketNumber");
                grid.getChildren().clear();
            }
        }
        catch(NumberFormatException e) {
            System.out.println(e);
        }
    }
    
    private void showNotes(ObservableList<TOMaintenanceNote> list) {
        if (list.size()==0) {
            errorMessage.setVisible(true);
            setErrorMessage("key.ViewNotes_NoNotesWithTicket");
        }
        grid.getChildren().clear();
        for (int i=0; i<list.size(); i++) {
            TOMaintenanceNote note = list.get(i);

            VBox container = new VBox();
            int width = 220;
            int height = 140;
            container.setPrefSize(width, height);
            container.setSpacing(10);
            container.setPadding(new Insets(10));
            container.setAlignment(Pos.CENTER_LEFT);

            HBox dateBox = new HBox();
            Label datePrefix = new Label(AssetPlusFXMLView.getInstance().getBundle().getString("key.ViewNotes_Date"));
            datePrefix.getStyleClass().add("noteText");
            Label date = new Label(note.getDate().toString());
            dateBox.getChildren().addAll(datePrefix, date);

            HBox authorBox = new HBox();
            Label authorPrefix = new Label(AssetPlusFXMLView.getInstance().getBundle().getString("key.ViewNotes_Author"));
            authorPrefix.getStyleClass().add("noteText");
            Label author = new Label(note.getNoteTakerEmail());
            authorBox.getChildren().addAll(authorPrefix, author);

            Label descPrefix = new Label(AssetPlusFXMLView.getInstance().getBundle().getString("key.ViewNotes_Description"));
            descPrefix.getStyleClass().add("noteText");
            Label description = new Label(note.getDescription());
            description.setWrapText(true);

            container.getChildren().addAll(dateBox, authorBox, descPrefix, description);
            
            final Integer index = new Integer(i);
            HBox buttons = new HBox();
            buttons.setSpacing(10);

            // Create the update button
            Button updateBtn = new Button();
            updateBtn.getStyleClass().add("icon-pencil");
            updateBtn.setPickOnBounds(true);
            updateBtn.setOnAction(e -> updateBtnClicked(index));

            // Create the delete button
            Button trashBtn = new Button();
            trashBtn.getStyleClass().add("icon-trash");
            trashBtn.setPickOnBounds(true);
            trashBtn.setOnAction(e -> trashBtnClicked(index));

            buttons.getChildren().addAll(updateBtn, trashBtn);

            // Create an AnchorPane to hold the image and button
            AnchorPane anchorPane = new AnchorPane();
            anchorPane.getChildren().addAll(container, buttons);
            anchorPane.getStyleClass().add("note");

            // Set the button's position to the top right corner
            AnchorPane.setTopAnchor(buttons, 10.0);
            AnchorPane.setRightAnchor(buttons, 10.0); 

            grid.getChildren().add(anchorPane);
        }
    }

    private void updateBtnClicked(int index) {
        ModifyNotePopUpController controller = (ModifyNotePopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ViewNotesModifyNotePopUp.fxml", "Modify Note");
        if (controller != null)
            controller.setTicketIdAndIndex(currentTicketNumber, index);
        ViewUtils.callController("");
    }

    private void trashBtnClicked(int index) {
        DeleteViewNotesPopUpController controller = (DeleteViewNotesPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ViewNotesDeletePopUp.fxml", "Delete Note");
        if (controller != null)
            controller.setTicketIdAndIndex(currentTicketNumber, index);
        ViewUtils.callController("");
    }

    private void setErrorMessage(String message) {
        errorMessage.setText(AssetPlusFXMLView.getInstance().getBundle().getString(message));
    }

    public void setTicketId(int ticketId) {
        currentTicketNumber = ticketId;
        numberLabel.setText(Integer.toString(ticketId));
        refreshView();
    }
}