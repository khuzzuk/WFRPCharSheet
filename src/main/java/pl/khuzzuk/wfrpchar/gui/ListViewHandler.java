package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import pl.khuzzuk.wfrpchar.entities.Named;

import java.util.stream.Collectors;

public class ListViewHandler {
    public static String getFromList(ListView<String> listView) {
        return listView.getItems().stream().collect(Collectors.joining("|"));
    }

    public static String getFromNamedList(ListView<Named<String>> listView) {
        return listView.getItems().stream().map(Named::getName).collect(Collectors.joining("|"));
    }

    public static <T> boolean shouldAddToList(T element, ListView<T> listView) {
        return element != null && !element.equals("brak") &&
                !listView.getItems().contains(element);
    }

    public static <T> boolean shouldAddToList(T element, TableView<T> listView) {
        return element != null && !element.equals("brak") &&
                !listView.getItems().contains(element);
    }
}
