package pl.khuzzuk.wfrpchar.entities;

import org.hibernate.annotations.NaturalId;

import java.util.Collection;
import java.util.stream.Collectors;

public interface Named<T extends Comparable<? super T>> {
    @NaturalId
    T getName();
    default boolean namedEquals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Named named = (Named) o;

        return getName() != null ? getName().equals(named.getName()) : named.getName() == null;

    }

    default int namedHashCode() {
        return getName() != null ? getName().hashCode() : 0;
    }

    static String toCsv(Collection<? extends Named<String>> elements, String sepaerator) {
        return elements.stream().map(Named::getName).collect(Collectors.joining(sepaerator));
    }
}
