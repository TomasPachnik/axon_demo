import aggregates.MessagesAggregate;
import commands.CreateMessageCommand;
import commands.MarkReadMessageCommand;
import eventHandlers.MessagesEventHandler;
import org.axonframework.commandhandling.AggregateAnnotationCommandHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.SimpleCommandBus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.gateway.DefaultCommandGateway;
import org.axonframework.eventhandling.AnnotationEventListenerAdapter;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.eventstore.EmbeddedEventStore;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.eventsourcing.eventstore.inmemory.InMemoryEventStorageEngine;

import java.util.UUID;

public class Main {

    private static CommandGateway commandGateway;

    public static void main(String[] args) {

        //command bus
        CommandBus commandBus = new SimpleCommandBus();
        commandGateway = new DefaultCommandGateway(commandBus);

        //message bus
        EventStore eventStore = new EmbeddedEventStore(new InMemoryEventStorageEngine());
        EventSourcingRepository<MessagesAggregate> repository =
                new EventSourcingRepository<>(MessagesAggregate.class, eventStore);

        //setting up aggregate
        AggregateAnnotationCommandHandler<MessagesAggregate> handler =
                new AggregateAnnotationCommandHandler<MessagesAggregate>(
                        MessagesAggregate.class, repository);
        handler.subscribe(commandBus);

        //listener to handle events
        AnnotationEventListenerAdapter annotationEventListenerAdapter
                = new AnnotationEventListenerAdapter(new MessagesEventHandler());
        eventStore.subscribe(eventMessages -> eventMessages.forEach(e -> {
            try {
                annotationEventListenerAdapter.handle(e);
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        }));

        someCommants();
    }

    private static void someCommants(){
        String itemId = UUID.randomUUID().toString();
        commandGateway.send(new CreateMessageCommand(itemId, "Hello, how is your day?"));
        commandGateway.send(new MarkReadMessageCommand(itemId));
    }

}
