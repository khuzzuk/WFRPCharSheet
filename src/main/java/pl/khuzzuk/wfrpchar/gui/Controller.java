package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.lang.reflect.Field;

public interface Controller extends Initializable {

    default void initializeValidation() {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field f : fields) {
            determineNumericAction(f, this);
        }
    }

    static void determineNumericAction(Field f, Controller controller) {
        try {
            if ((f.getType().equals(TextField.class))) {
                if (f.isAnnotationPresent(Numeric.class)) {
                    setIntegerListenerToTextField((TextField) f.get(controller));
                } else if (f.isAnnotationPresent(FloatNumeric.class)) {
                    setFloatListenerToTextField((TextField) f.get(controller));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static void setIntegerListenerToTextField(TextField field) {
        field.textProperty().addListener((obs, oldV, newV) -> field.setText(newV.replaceAll("[^\\d\\-]", "")));
    }

    static void setFloatListenerToTextField(TextField field) {
        field.textProperty().addListener((obs, oldV, newV) -> field.setText(newV.replaceAll("[^\\d\\-.,]", "")));
    }
}
