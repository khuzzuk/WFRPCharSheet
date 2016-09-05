package pl.khuzzuk.wfrpchar.entities;

public interface Labelled<T, U> {
    T getLabel();
    U getRepresentation();
}
