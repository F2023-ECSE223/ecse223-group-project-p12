package ca.mcgill.ecse.assetplus.javafx.fxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.util.ResourceBundle;
import java.util.Locale;
import java.util.Stack;
import javafx.event.EventHandler;



public class AssetPlusFXMLView extends Application {

  public static final EventType<Event> REFRESH_EVENT = new EventType<>("REFRESH");
  private static AssetPlusFXMLView instance;
  private List<Node> refreshableNodes = new ArrayList<>();
  private Stage stage;
  private Stack<Stage> popUpStages;
  private Object currentController;
  private String currentPage;
  private String language = "en";
  private final String BUNDLE_PATH = "ca.mcgill.ecse.assetplus.javafx.resources.languages.Bundle";

  // To drag the window
  private double xOffset = 0;
  private double yOffset = 0;

  // Size of the minimize window
  private double prevX = 0;
  private double prevY = 0;
  private double prevWidth = 0;
  private double prevHeight = 0;
  private boolean isMaximized = false;

  @Override
  public void start(Stage primaryStage) {
    instance = this;
    try {
      stage = primaryStage;
      popUpStages = new Stack<>();
      currentPage = "pages/FrontPage.fxml";
      var root = (Pane) FXMLLoader.load(getClass().getResource(currentPage), ResourceBundle.getBundle(BUNDLE_PATH, new Locale(this.language)));

      var scene = new Scene(root);
      scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());
      primaryStage.setScene(scene);
      primaryStage.setMinWidth(960);
      primaryStage.setMinHeight(540);
      primaryStage.setTitle("AssetPlus");
      //primaryStage.initStyle(StageStyle.TRANSPARENT);
      primaryStage.show();

      // Initializes the other pages

      refresh();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Register the node for receiving refresh events
  public void registerRefreshEvent(Node node) {
    refreshableNodes.add(node);
  }

  // Register multiple nodes for receiving refresh events
  public void registerRefreshEvent(Node... nodes) {
    for (var node: nodes) {
      refreshableNodes.add(node);
    }
  }

  public <T extends Event> void registerEventHandler(EventType<T> eventType, EventHandler<T> eventHandler) {
    stage.addEventHandler(eventType, eventHandler);
  }

  // remove the node from receiving refresh events
  public void removeRefreshableNode(Node node) {
    refreshableNodes.remove(node);
  }

  // fire the refresh event to all registered nodes
  public void refresh() {
    for (Node node : refreshableNodes) {
      node.fireEvent(new Event(REFRESH_EVENT));
    }
  }

  public static AssetPlusFXMLView getInstance() {
    return instance;
  }

  public Object loadPopupWindow(String fxml, String title) {
    Stage popUpStage = new Stage();
    popUpStages.push(popUpStage);
    popUpStage.initModality(Modality.APPLICATION_MODAL);
    popUpStage.initStyle(StageStyle.TRANSPARENT);

    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml), ResourceBundle.getBundle(BUNDLE_PATH, new Locale(language)));
    Parent root;
    try 
    {
      root = (Parent) loader.load();
    
      var scene = new Scene(root);
      scene.setFill(Color.TRANSPARENT);
      scene.getStylesheets().add(getClass().getResource("css/style.css").toExternalForm());
      scene.getRoot().getStyleClass().add("popUpWindow");
      popUpStage.setScene(scene);
      popUpStage.setTitle(title);
      popUpStage.setResizable(false);
      popUpStage.show();
      
      // Return the controller of the pop up window
      return loader.getController();

    }
    catch (IOException e)
    {
        e.printStackTrace();
    }
    return null;
  }

  public void closePopUpWindow() {
    if (!this.popUpStages.isEmpty()) {
      Stage stage = this.popUpStages.pop();
      stage.close();
    }
  }

  public Object changeTab(String fxml)
  {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml), ResourceBundle.getBundle(BUNDLE_PATH, new Locale(this.language)));
    currentController = loader.getController();
    Parent root;
    currentPage = fxml;
    
    try 
    {
        root = (Parent) loader.load();
        Scene scene = new Scene(root, this.stage.getScene().getWidth(), this.stage.getScene().getHeight());
        this.stage.setScene(scene);

        return loader.getController();
    }
    catch (IOException e)
    {
        e.printStackTrace();
    }

    return null;
  }

  public Object getCurrentController() {
    return currentController;
  }

  public String getCurrentPage() {
    return currentPage;
  }

  public void setLanguage(String language) {
    this.language = language;
    changeTab(currentPage);
  }

  public String getLanguage() {
    return this.language;
  }

  public ResourceBundle getBundle() {
    return ResourceBundle.getBundle(BUNDLE_PATH, new Locale(this.language));
  }

  public void closeWindow() {
    this.stage.close();
  }

  public void maximizeWindow() {
    if (!isMaximized) {
      // Save current position and window's size
      prevX = stage.getX();
      prevY = stage.getY();
      prevWidth = stage.getWidth();
      prevHeight = stage.getHeight();

      Rectangle2D bounds = Screen.getPrimary().getVisualBounds();


      System.out.printf("Before Maximize %f %f \n", stage.getWidth(), stage.getHeight());
      System.out.printf("Before Maximize %f %f \n", stage.getScene().getWidth(), stage.getScene().getHeight());
      stage.setX(bounds.getMinX());
      stage.setY(bounds.getMinY());
      stage.setWidth(bounds.getWidth());
      stage.setHeight(bounds.getHeight());
      System.out.printf("Maximize %f %f \n", stage.getWidth(), stage.getHeight());
      System.out.printf("Maximize %f %f \n", stage.getScene().getWidth(), stage.getScene().getHeight());

      

      isMaximized = true;
    }
    else {
      stage.setX(prevX);
      stage.setY(prevY);
      stage.setWidth(prevWidth);
      stage.setHeight(prevHeight);
      isMaximized = false;
    }
  
  }

  public void hideWindow() {
    this.stage.setIconified(true);
  }

  public void onToolbarPressed(double x, double y) {
    xOffset = this.stage.getX() - x;
    yOffset = this.stage.getY() - y;
  }

  public void onToolbarDragged(double x, double y) {
    this.stage.setX(x + xOffset);
    this.stage.setY(y + yOffset);
  }

  public void onPopUpToolbarPressed(double x, double y) {
    xOffset = this.popUpStages.get(popUpStages.size()-1).getX() - x;
    yOffset = this.popUpStages.get(popUpStages.size()-1).getY() - y;
  }

  public void onPopUpToolbarDragged(double x, double y) {
    this.popUpStages.get(popUpStages.size()-1).setX(x + xOffset);
    this.popUpStages.get(popUpStages.size()-1).setY(y + yOffset);
  }

  public void fireEvent(Event event) {
    stage.fireEvent(event);
  }
}
