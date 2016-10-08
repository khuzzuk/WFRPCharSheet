package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.ListView;
import pl.khuzzuk.wfrpchar.entities.Named;

import java.util.Collection;
import java.util.stream.Collectors;

public class EntitiesAdapter {
    public static <T extends Named<U>, U extends Comparable<U>> void sendToListView(
            ListView<U> view, Collection<T> items) {
        view.getItems().clear();
        view.getItems().addAll(items.stream().map(Named::getName).collect(Collectors.toList()));
    }
}
