package pl.khuzzuk.wfrpchar.entities;

public class Context<T, U> implements Labelled<T, U> {
    private T label;
    private U representation;

    public Context(T label, U representation) {
        this.label = label;
        this.representation = representation;
    }

    @Override
    public T getLabel() {
        return label;
    }

    @Override
    public U getRepresentation() {
        return representation;
    }
}
