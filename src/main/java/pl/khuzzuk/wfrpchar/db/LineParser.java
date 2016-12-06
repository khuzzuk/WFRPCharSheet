package pl.khuzzuk.wfrpchar.db;

import java.util.Arrays;

public class LineParser {
    static String[] getFrom(String line, int withNumberOfFields) {
        return Arrays.copyOfRange(line.split(";"), 0, withNumberOfFields);
    }
}
