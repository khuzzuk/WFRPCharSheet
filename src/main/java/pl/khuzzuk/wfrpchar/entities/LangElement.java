package pl.khuzzuk.wfrpchar.entities;

import java.util.HashMap;
import java.util.Map;

public enum LangElement {
    ADJECTIVE_MASC_SING,
    ADJECTIVE_FEM_SING,
    ADJECTIVE_NEUTR_SING,
    ADIECTIVUM;

    public static Map<LangElement, String> parseLang(String data) {
        String[] columns = data.split("\\|");
        Map<LangElement, String> lang = new HashMap<>(4);
        if (columns.length > 0 && !columns[0].equals("")) {
            lang.put(ADJECTIVE_MASC_SING, columns[0]);
        }
        if (columns.length > 1 && !columns[1].equals("")) {
            lang.put(ADJECTIVE_FEM_SING, columns[1]);
        }
        if (columns.length > 2 && !columns[2].equals("")) {
            lang.put(ADJECTIVE_NEUTR_SING, columns[2]);
        }
        if (columns.length > 3 && !columns[3].equals("")) {
            lang.put(ADIECTIVUM, columns[3]);
        }
        return lang;
    }
}
