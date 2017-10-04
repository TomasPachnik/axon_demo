package aggregates;

import commands.CreateMessageCommand;
import commands.MarkReadMessageCommand;
import events.MessageCreatedEvent;
import events.MessageReadEvent;
import exceptions.InputException;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventhandling.EventHandler;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

public class MessagesAggregate {

    @AggregateIdentifier
    private String id;

    public MessagesAggregate() {
    }

    @CommandHandler
    public MessagesAggregate(CreateMessageCommand command) {
        validateInput(command);
        apply(new MessageCreatedEvent(command.getId(), command.getText()));
    }

    @CommandHandler
    public void markRead(MarkReadMessageCommand command) {
        apply(new MessageReadEvent(id));
    }

    @EventHandler
    public void on(MessageCreatedEvent event) {
        this.id = event.getId();
    }

    private void validateInput(CreateMessageCommand command){
        if(command.getText().length()> 30){
            throw new InputException("'" + command.getText() + "' is too long!");
        }
    }

}
