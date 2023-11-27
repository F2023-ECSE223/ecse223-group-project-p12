package ca.mcgill.ecse.assetplus.javafx.fxml.events;

import javafx.event.Event;
import javafx.event.EventType;
import java.util.List;

public class EmployeeDeletedEvent extends Event {
    private final List<Integer> ticketsToDelete;

    public static final EventType<EmployeeDeletedEvent> EMPLOYEE_DELETED = 
      new EventType<>(Event.ANY, "EMPLOYEE_DELETED");

    public EmployeeDeletedEvent(List<Integer> ticketsToDelete) {
      super(EMPLOYEE_DELETED);
      this.ticketsToDelete = ticketsToDelete;
    }

    public List<Integer> getTicketsToDelete() {
        return ticketsToDelete;
    }
}