package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.ListView;
import pl.khuzzuk.wfrpchar.entities.Named;

import java.util.Collection;
import java.util.stream.Collectors;

public class EntitiesAdapter {
    public static <T extends Named<U>, U extends Comparable<? super U>> void sendToListView(
            ListView<U> view, Collection<T> items) {
        view.getItems().clear();
        view.getItems().addAll(items.stream().map(Named::getName).collect(Collectors.toList()));
    }

    public static <T> void sendToListViewUnchanged(ListView<T> list, Collection<T> elements) {
        list.getItems().clear();
        list.getItems().addAll(elements);
    }

    public static void removeSelected(ListView<?> view) {
        if (view.getSelectionModel().getSelectedIndex() > -1) {
            view.getItems().remove(view.getSelectionModel().getSelectedIndex());
        }
    }
}
