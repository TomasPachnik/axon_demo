package eventHandlers;

import events.MessageCreatedEvent;
import events.MessageReadEvent;
import org.axonframework.eventhandling.EventHandler;

public class MessagesEventHandler {

    @EventHandler
    public void handle(MessageCreatedEvent event) {
        //store to database
        System.out.println("Message received: " + event.getText() + " (" + event.getId() + ")");
    }

    @EventHandler
    public void handle(MessageReadEvent event) {
        //reading from database
        System.out.println("Message read: " + event.getId());
    }
}
