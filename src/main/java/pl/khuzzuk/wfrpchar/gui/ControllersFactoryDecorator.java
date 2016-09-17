package pl.khuzzuk.wfrpchar.gui;

import javafx.util.Callback;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ControllersFactoryDecorator implements Callback<Class<?>, Object> {
    private Map<Class<?>, Controller> controllers;
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
    @Inject
    private HandWeaponsPaneController handWeaponsPaneController;
    @Inject
    private HandWeaponTypeChooserController handWeaponTypeChooserController;

    @PostConstruct
    private void organize() {
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
    }

    @Override
    public Object call(Class<?> param) {
        return controllers.get(param);
    }
}
