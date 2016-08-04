package pl.khuzzuk.wfrpchar.messaging;

public class ContentMessage<T> implements BagMessage<T> {
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
