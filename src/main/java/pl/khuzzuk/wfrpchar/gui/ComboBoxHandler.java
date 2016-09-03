package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.ComboBox;
import pl.khuzzuk.wfrpchar.entities.Nameable;

import java.util.EnumSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ComboBoxHandler {
    @SuppressWarnings("unchecked")
    static <T extends Enum & Nameable<? extends U>, U extends Comparable<? super U>> void fill(
            Class<T> enumType, ComboBox<U> comboBox) {
        comboBox.getItems().clear();
        Set<Nameable<? extends U>> elements = EnumSet.allOf(enumType);
        comboBox.getItems().addAll(elements
                .stream().map(Nameable::getName).collect(Collectors.toList()));
    }

    static <T extends Enum & Nameable<? extends U>, U extends Comparable<? super U>> void fill(
            Set<T> elements, ComboBox<U> comboBox) {
        comboBox.getItems().clear();
        comboBox.getItems().addAll(elements.stream()
                .map(Nameable::getName).collect(Collectors.toList()));
    }
}
