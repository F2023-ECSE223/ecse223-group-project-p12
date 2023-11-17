package ca.mcgill.ecse.assetplus.javafx.fxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.assetplus.javafx.fxml.controllers.AddImageController;
import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class AssetPlusFXMLView extends Application {

  public static final EventType<Event> REFRESH_EVENT = new EventType<>("REFRESH");
  private static AssetPlusFXMLView instance;
  private List<Node> refreshableNodes = new ArrayList<>();
  private Stage stage;

  @Override
  public void start(Stage primaryStage) {
    instance = this;
    try {
      stage = primaryStage;
      var root = (Pane) FXMLLoader.load(getClass().getResource("pages/ViewTicketStatus.fxml"));
      var scene = new Scene(root);
      primaryStage.setScene(scene);
      primaryStage.setMinWidth(960);
      primaryStage.setMinHeight(540);
      primaryStage.setTitle("AssetPlus");
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

  public void changeTab(String fxml)
  {
    FXMLLoader loader = new FXMLLoader(getClass()
            .getResource(fxml));
    Parent root;
    try 
    {
        root = (Parent)loader.load();
        this.stage.setScene(new Scene(root));
    } 
    catch (IOException e)
    {
        e.printStackTrace();
    }

  }

}
