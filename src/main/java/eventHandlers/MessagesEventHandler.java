package eventHandlers;

import events.MessageCreatedEvent;
import events.MessageReadEvent;
import org.axonframework.eventhandling.EventHandler;

public class MessagesEventHandler {
    @EventHandler
    public void handle(MessageCreatedEvent event) {
        System.out.println("Message received: " + event.getText() + " (" + event.getId() + ")");
    }

    @EventHandler
    public void handle(MessageReadEvent event) {
        System.out.println("Message read: " + event.getId());
    }
}
