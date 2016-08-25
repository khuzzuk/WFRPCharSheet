package pl.khuzzuk.wfrpchar.messaging;

public class CommunicateMessage extends AbstractMessage {
    @Override
    public String toString() {
        return "Message=" + getType();
    }
}
