package pl.khuzzuk.wfrpchar.entities;

public interface Documented {
    Documented fromCsv(String line);

    String toCsv();
}
