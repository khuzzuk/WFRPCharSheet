package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.util.Callback;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.gui.MainWindowBean;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ControllersFactoryDecorator implements Callback<Class<?>, Object> {
    private Map<Class<?>, Controller> controllers;

    @Inject
    public ControllersFactoryDecorator(@MainWindowBean MainWindowController mainWindowController,
                                       ArmorTypesPaneController armorTypesPaneController,
                                       RangedWeaponTypePaneController rangedWeaponTypePaneController,
                                       WhiteWeaponTypePaneController whiteWeaponTypePaneController,
                                       ItemTypesPaneController itemTypesPaneController,
                                       ResourceTypesPaneController resourceTypesPaneController,
                                       ItemsListedController weaponEntitiesPaneController,
                                       HandWeaponsPaneController handWeaponsPaneController,
                                       HandWeaponTypeChooserController handWeaponTypeChooserController,
                                       RangeWeaponsPaneController rangeWeaponsPaneController) {
        controllers = new ConcurrentHashMap<>();
        controllers.put(mainWindowController.getClass(), mainWindowController);
        controllers.put(armorTypesPaneController.getClass(), armorTypesPaneController);
        controllers.put(rangedWeaponTypePaneController.getClass(), rangedWeaponTypePaneController);
        controllers.put(whiteWeaponTypePaneController.getClass(), whiteWeaponTypePaneController);
        controllers.put(itemTypesPaneController.getClass(), itemTypesPaneController);
        controllers.put(resourceTypesPaneController.getClass(), resourceTypesPaneController);
        controllers.put(weaponEntitiesPaneController.getClass(), weaponEntitiesPaneController);
        controllers.put(handWeaponsPaneController.getClass(), handWeaponsPaneController);
        controllers.put(handWeaponTypeChooserController.getClass(), handWeaponTypeChooserController);
        controllers.put(rangedWeaponTypePaneController.getClass(), rangedWeaponTypePaneController);
        controllers.put(rangeWeaponsPaneController.getClass(), rangeWeaponsPaneController);
    }

    @Override
    public Object call(Class<?> param) {
        return controllers.get(param);
    }
}
