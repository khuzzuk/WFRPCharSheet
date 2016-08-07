package pl.khuzzuk.wfrpchar.messaging;

import lombok.Getter;
import lombok.ToString;

@ToString
public abstract class AbstractMessage implements Message {
    @Getter
    private String type;

    @Override
    public Message setType(String type) {
        this.type = type;
        return this;
    }
}
