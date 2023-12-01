package ca.mcgill.ecse.assetplus.javafx.fxml.events;

import javafx.event.Event;
import javafx.event.EventType;
import java.util.List;

public class AssetTypeDeletedEvent extends Event {
    private final List<Integer> ticketsToDelete;

    public static final EventType<AssetTypeDeletedEvent> ASSET_TYPE_DELETED = 
      new EventType<>(Event.ANY, "ASSET_TYPE_DELETED");

    public AssetTypeDeletedEvent(List<Integer> ticketsToDelete) {
      super(ASSET_TYPE_DELETED);
      this.ticketsToDelete = ticketsToDelete;
    }

    public List<Integer> getTicketsToDelete() {
        return ticketsToDelete;
    }
}