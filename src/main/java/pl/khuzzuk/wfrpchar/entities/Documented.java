package pl.khuzzuk.wfrpchar.entities;

import java.util.*;
import java.util.stream.Collectors;

public interface Documented {
    String toCsv();

    static String toCsv(Collection<? extends Documented> items, String separator) {
        if (items.isEmpty() || items.iterator().next() == null) {
            return "";
        }
        return items.stream().map(Documented::toCsv).collect(Collectors.joining(separator));
    }

    static <T extends Named<String> & Persistable> Set<T> toSet(String fields, String separator, Class<T> type) {
        return new HashSet<>();
    }

    static <T extends Named<String> & Persistable> List<T> toList(String fields, String separator, Class<T> type) {
        return new LinkedList<>();
    }
}
