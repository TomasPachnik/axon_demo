package events;

public class MessageReadEvent {
    private String id;

    public MessageReadEvent() {
    }

    public MessageReadEvent(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
