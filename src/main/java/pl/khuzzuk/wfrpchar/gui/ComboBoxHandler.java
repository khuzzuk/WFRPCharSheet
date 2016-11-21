package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.ComboBox;
import pl.khuzzuk.wfrpchar.entities.Named;

import java.util.Collection;
import java.util.stream.Collectors;

public class ComboBoxHandler {

    public static <T extends Named<? extends U>, U extends Comparable<? super U>> void fill(
            Collection<T> elements, ComboBox<U> comboBox) {
        comboBox.getItems().clear();
        comboBox.getItems().addAll(elements.stream()
                .map(Named::getName).collect(Collectors.toList()));
    }

    public static String getEmptyIfNotPresent(ComboBox<String> list) {
        String value = list.getSelectionModel().getSelectedItem();
        return value != null ? value : "";
    }

    public static void selectOrEmpty(ComboBox<String> box, Named<String> element) {
        box.getSelectionModel().select(element != null ? element.getName() : null);
    }
}
