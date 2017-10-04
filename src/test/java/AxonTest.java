import aggregates.MessagesAggregate;
import commands.CreateMessageCommand;
import commands.MarkReadMessageCommand;
import events.MessageCreatedEvent;
import events.MessageReadEvent;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

public class AxonTest {

    private FixtureConfiguration<MessagesAggregate> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = new AggregateTestFixture<MessagesAggregate>(MessagesAggregate.class);
    }

    @Test
    public void messageCreatedTest(){
        String eventText = "Hello, how is your day?";
        String id = UUID.randomUUID().toString();
        fixture.given()
                .when(new CreateMessageCommand(id, eventText))
                .expectEvents(new MessageCreatedEvent(id, eventText));
    }

    @Test
    public void messageReadTest() {
        String id = UUID.randomUUID().toString();

        fixture.given(new MessageCreatedEvent(id, "Hello"))
                .when(new MarkReadMessageCommand(id))
                .expectEvents(new MessageReadEvent(id));
    }

}
