package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.ListView;
import pl.khuzzuk.wfrpchar.entities.Named;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class EntitiesAdapter {
    static void sendQuery(ListView<String> list, Consumer<String> action) {
        String selected = list.getSelectionModel().getSelectedItem();
        if (selected != null) {
            action.accept(selected);
        }
    }

    static <T extends Named<U>, U extends Comparable<U>> void sendToListView(
            ListView<U> view, Collection<T> items) {
        view.getItems().clear();
        view.getItems().addAll(items.stream().map(Named::getName).collect(Collectors.toList()));
    }
}
