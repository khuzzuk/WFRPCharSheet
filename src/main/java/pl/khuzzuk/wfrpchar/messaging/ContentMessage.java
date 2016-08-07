package pl.khuzzuk.wfrpchar.messaging;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@ToString(exclude = "content")
public class ContentMessage<T> implements BagMessage<T> {
    @Getter
    @NonNull
    private String type;
    private T content;
    @Override
    public void setMessage(T content) {
        this.content = content;
    }

    @Override
    public T getMessage() {
        return content;
    }

    @Override
    public Message setType(String type) {
        this.type = type;
        return this;
    }
}
