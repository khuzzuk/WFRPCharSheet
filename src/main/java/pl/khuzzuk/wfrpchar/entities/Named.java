package pl.khuzzuk.wfrpchar.entities;

public interface Named<T extends Comparable<T>> {
    T getName();
}
