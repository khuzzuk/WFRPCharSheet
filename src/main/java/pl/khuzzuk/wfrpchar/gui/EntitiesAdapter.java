package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.ListView;

import java.util.function.Consumer;

class EntitiesAdapter {
    static void sendQuery(ListView<String> list, Consumer<String> action) {
        String selected = list.getSelectionModel().getSelectedItem();
        if (selected != null) {
            action.accept(selected);
        }
    }
}
