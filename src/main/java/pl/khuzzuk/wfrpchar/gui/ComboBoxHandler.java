package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.ComboBox;
import pl.khuzzuk.wfrpchar.entities.Nameable;

import java.util.Set;
import java.util.stream.Collectors;

public class ComboBoxHandler {

    static <T extends Nameable<? extends U>, U extends Comparable<? super U>> void fill(
            Set<T> elements, ComboBox<U> comboBox) {
        comboBox.getItems().clear();
        comboBox.getItems().addAll(elements.stream()
                .map(Nameable::getName).collect(Collectors.toList()));
    }
}
