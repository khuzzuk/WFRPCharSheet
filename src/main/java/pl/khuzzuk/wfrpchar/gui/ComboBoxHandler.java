package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
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

    public static <T extends Named<String>> void fillWithEnums(Collection<T> enums, ComboBox<T> comboBox) {
        comboBox.getItems().clear();
        comboBox.getItems().addAll(enums);
        comboBox.setConverter(new StringConverter<T>() {
            @Override
            public String toString(T object) {
                return object.getName();
            }

            @Override
            public T fromString(String string) {
                return enums.stream().filter(e -> e.getName().equals(string)).findAny().orElse(enums.iterator().next());
            }
        });
//        comboBox.setCellFactory(param -> new ListCell<T>() {
//            @Override
//            protected void updateItem(T item, boolean empty) {
//                super.updateItem(item, empty);
//                setGraphic(new Text(empty ? "" : item.getName()));
//            }
//        });
    }

    public static String getEmptyIfNotPresent(ComboBox<String> list) {
        String value = list.getSelectionModel().getSelectedItem();
        return value != null ? value : "";
    }

    public static void selectOrEmpty(ComboBox<String> box, Named<String> element) {
        box.getSelectionModel().select(element != null ? element.getName() : null);
    }
}
