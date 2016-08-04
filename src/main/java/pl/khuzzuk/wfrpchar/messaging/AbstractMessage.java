package pl.khuzzuk.wfrpchar.messaging;

public abstract class AbstractMessage implements Message {
    private String type;

    @Override
    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String getType() {
        return type;
    }
}
