package pl.khuzzuk.wfrpchar.messaging;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@ToString(exclude = "content")
public class ContentMessage<T> implements BagMessage<T> {
    @Getter
    @Setter
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
}
