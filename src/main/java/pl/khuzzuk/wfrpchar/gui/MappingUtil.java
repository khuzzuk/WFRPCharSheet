package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.TextField;
import pl.khuzzuk.wfrpchar.entities.Labelled;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;

import java.util.Map;
import java.util.stream.Collectors;

public class MappingUtil {
    public static <T extends Labelled<U, String>, U> void mapDeterminant(T type, Map<U, TextField> map) {
        map.get(type.getLabel()).setText(type.getRepresentation());
    }

    public static String getDeterminants(Map<DeterminantsType, TextField> map) {
        return DeterminantsType.SET.stream()
                .filter(d -> map.get(d) != null && map.get(d).getText().length() > 0)
                .map(d -> "" + map.get(d).getText() + "," + d.name()).collect(Collectors.joining("|"));
    }
}
