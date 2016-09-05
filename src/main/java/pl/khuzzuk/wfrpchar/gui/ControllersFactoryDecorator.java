package pl.khuzzuk.wfrpchar.gui;

import javafx.util.Callback;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class ControllersFactoryDecorator implements Callback<Class<?>, Object> {
    @Inject
    @MainWindowBean
    private MainWindowController mainWindowController;
    @Inject
    private ArmorTypesPaneController armorTypesPaneController;

    @Override
    public Object call(Class<?> param) {
        if (param.equals(mainWindowController.getClass())) {
            return mainWindowController;
        } else {
            return armorTypesPaneController;
        }
    }
}
