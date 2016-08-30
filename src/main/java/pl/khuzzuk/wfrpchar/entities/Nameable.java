package pl.khuzzuk.wfrpchar.entities;

public interface Nameable<T extends Comparable<T>> {
    T getName();
}
