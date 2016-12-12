package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.ListView;
import pl.khuzzuk.wfrpchar.entities.Named;

import java.util.stream.Collectors;

public class ListViewHandler {
    public static String getFromList(ListView<String> listView) {
        return listView.getItems().stream().collect(Collectors.joining("|"));
    }

    public static String getFromNamedList(ListView<Named<String>> listView) {
        return listView.getItems().stream().map(Named::getName).collect(Collectors.joining("|"));
    }

    public static boolean shouldAddToList(String name, ListView<String> listView) {
        return name != null && !name.equals("brak") &&
                !listView.getItems().contains(name);
    }
}
