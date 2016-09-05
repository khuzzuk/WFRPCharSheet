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
    @Inject
    private RangedWeaponTypePaneController rangedWeaponTypePaneController;

    @Override
    public Object call(Class<?> param) {
        if (param.equals(mainWindowController.getClass())) {
            return mainWindowController;
        } else if (param.equals(armorTypesPaneController.getClass())) {
            return armorTypesPaneController;
        } else {
            return rangedWeaponTypePaneController;
        }
    }
}
