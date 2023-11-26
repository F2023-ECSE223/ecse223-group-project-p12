package ca.mcgill.ecse.assetplus.javafx.fxml.controllers.popups;


import ca.mcgill.ecse.assetplus.controller.AssetPlusFeatureSet5Controller;
import ca.mcgill.ecse.assetplus.javafx.fxml.AssetPlusFXMLView;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.ViewUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.text.View;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ViewImagesController {
    private int currentTicketNumber;
    private int sizeImg;

    @FXML
    private ResourceBundle resources;

    @FXML
    private HBox DashboardAndContent;

    @FXML
    private VBox TopContent;

    @FXML
    private Button addImageButton;

    @FXML
    private Label numberLabel;

    @FXML
    private Label errorMessage;

    @FXML
    private FlowPane grid;

    @FXML
    public void initialize() {
        currentTicketNumber = -1;
        sizeImg = 120;

        // the grid listen to the refresh event
        grid.addEventHandler(AssetPlusFXMLView.REFRESH_EVENT, e -> {
            refreshView();
        });

        // let the application be aware of the refreshable node
        AssetPlusFXMLView.getInstance().registerRefreshEvent(grid);
    }

    @FXML
    void AddImage(ActionEvent event) {
        if (currentTicketNumber!=-1) {
            AddImagePopUpController controller = (AddImagePopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ViewImagesAddImagePopUp.fxml", "Add Image");
            if (controller!=null)
                controller.setTicketId(currentTicketNumber);
            else System.out.println("controller null");
        }
        else {
            errorMessage.setVisible(true);
            setErrorMessage("key.ViewImages_EnterValidTicket");
        }
    }

    private void refreshView() {
        try {
            ObservableList<String> imageList = ViewUtils.getTicketImages(currentTicketNumber);

            if (imageList != null) {
                errorMessage.setVisible(false);
                showImages(imageList);
            }
            else {
                errorMessage.setVisible(true);
                setErrorMessage("key.ViewImages_InvalidTicketNumber");
                grid.getChildren().clear();
            }
        }
        catch(NumberFormatException e) {
            System.out.println(e);
        }
    }
    
    private void showImages(ObservableList<String> list) {
        grid.getChildren().clear();

        if (list.size()==0) {
            errorMessage.setVisible(true);
            setErrorMessage("key.ViewImages_NoImagesWithTicket");
        }
        else {
            errorMessage.setVisible(true);
            setErrorMessage("key.ViewImages_LoadingImages");
        }

        List<javafx.concurrent.Task<Image>> tasks = new ArrayList<>();
        for (String imageURL : list) {
            javafx.concurrent.Task<Image> task = new javafx.concurrent.Task<>() {
                @Override
                protected Image call() throws Exception {
                    return new Image(imageURL, sizeImg, sizeImg, true, true);
                }
            };
            tasks.add(task);
            new Thread(task).start();
        }

        for (javafx.concurrent.Task<Image> task : tasks) { 
            task.setOnSucceeded(event -> {
                Rectangle rectangle = new Rectangle(0, 0, sizeImg, sizeImg);
                rectangle.setArcWidth(30.0);
                rectangle.setArcHeight(30.0);
                
                Image image = task.getValue();
                // If the image was loaded without exceptions, consider it valid
                ImagePattern pattern;
                if (image.isError() == false) {
                    pattern = new ImagePattern(image);
                }
                else {
                    pattern = new ImagePattern(new Image("ca/mcgill/ecse/assetplus/javafx/resources/Images/warning.png", 200, 200, true, true));
                    
                }
                rectangle.setFill(pattern);
                
                // Create the delete button
                Button trashBtn = new Button();
                trashBtn.getStyleClass().add("icon-trash");
                trashBtn.setStyle("-icon-paint:  white");
                trashBtn.setPickOnBounds(true);
                trashBtn.setOnAction(e -> trashBtnClicked(image.getUrl()));

                // Create an AnchorPane to hold the image and button
                AnchorPane anchorPane = new AnchorPane();
                anchorPane.getChildren().addAll(rectangle, trashBtn);

                // Set the button's position to the top right corner
                AnchorPane.setTopAnchor(trashBtn, 10.0);
                AnchorPane.setRightAnchor(trashBtn, 10.0); 

                grid.getChildren().add(anchorPane);
            });
        }
    }

    private void trashBtnClicked(String url) {
        DeleteViewImagesPopUpController controller = (DeleteViewImagesPopUpController) AssetPlusFXMLView.getInstance().loadPopupWindow("popUp/ViewImagesDeletePopUp.fxml", "Delete Image");
        if (controller != null)
            controller.setTicketIdAndURL(currentTicketNumber, url);
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