package pl.khuzzuk.wfrpchar.entities;

import java.util.List;
import java.util.stream.Collectors;

public class CsvBuilder {
    private List<String> fields;

    public CsvBuilder(List<String> fields) {
        this.fields = fields;
    }

    public CsvBuilder add(String field) {
        fields.add(field == null ? "" : field);
        return this;
    }

    public CsvBuilder add(int number) {
        fields.add(number + "");
        return this;
    }

    public CsvBuilder add(float number) {
        fields.add(number + "");
        return this;
    }

    public CsvBuilder add(Named<String> named) {
        fields.add(named != null ? named.getName() != null ? named.getName() : "" : "");
        return this;
    }

    public String build() {
        return fields.stream().collect(Collectors.joining(";"));
    }
}
