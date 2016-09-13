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
    @Inject
    private WhiteWeaponTypePaneController whiteWeaponTypePaneController;
    @Inject
    private ItemTypesPaneController itemTypesPaneController;
    @Inject
    private ResourceTypesPaneController resourceTypesPaneController;
    @Inject
    private ItemsListedController weaponEntitiesPaneController;

    @Override
    public Object call(Class<?> param) {
        if (param.equals(mainWindowController.getClass())) {
            return mainWindowController;
        } else if (param.equals(armorTypesPaneController.getClass())) {
            return armorTypesPaneController;
        } else if (param.equals(rangedWeaponTypePaneController.getClass())) {
            return rangedWeaponTypePaneController;
        } else if (param.equals(whiteWeaponTypePaneController.getClass())) {
            return whiteWeaponTypePaneController;
        } else if (param.equals(itemTypesPaneController.getClass())) {
            return itemTypesPaneController;
        } else if (param.equals(resourceTypesPaneController.getClass())) {
            return resourceTypesPaneController;
        } else {
            return weaponEntitiesPaneController;
        }
    }
}
