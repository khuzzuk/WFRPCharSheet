package pl.khuzzuk.wfrpchar.messaging;

public interface Message {
    Message setType(String type);
    String getType();
}
