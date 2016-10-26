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
                                       AmmunitionTypesPaneController ammunitionTypesPaneController,
                                       HandWeaponsPaneController handWeaponsPaneController,
                                       LinkedEntityChooserController equipmentTypeChooserController,
                                       RangeWeaponsPaneController rangeWeaponsPaneController,
                                       ArmorPaneController armorPaneController,
                                       AmmunitionPaneController ammunitionPaneController,
                                       SkillsPaneController skillsPaneController,
                                       ProfessionClassPaneController professionClassPaneController,
                                       ProfessionPaneController professionPaneController,
                                       RacesPaneController racesPaneController,
                                       PlayerPaneController playerPaneController) {
        controllers = new ConcurrentHashMap<>();
        //TODO rework it to use ApplicationContext instead
        controllers.put(mainWindowController.getClass(), mainWindowController);
        controllers.put(armorTypesPaneController.getClass(), armorTypesPaneController);
        controllers.put(rangedWeaponTypePaneController.getClass(), rangedWeaponTypePaneController);
        controllers.put(whiteWeaponTypePaneController.getClass(), whiteWeaponTypePaneController);
        controllers.put(itemTypesPaneController.getClass(), itemTypesPaneController);
        controllers.put(resourceTypesPaneController.getClass(), resourceTypesPaneController);
        controllers.put(ammunitionTypesPaneController.getClass(), ammunitionTypesPaneController);
        controllers.put(handWeaponsPaneController.getClass(), handWeaponsPaneController);
        controllers.put(equipmentTypeChooserController.getClass(), equipmentTypeChooserController);
        controllers.put(rangedWeaponTypePaneController.getClass(), rangedWeaponTypePaneController);
        controllers.put(rangeWeaponsPaneController.getClass(), rangeWeaponsPaneController);
        controllers.put(armorPaneController.getClass(), armorPaneController);
        controllers.put(ammunitionPaneController.getClass(), ammunitionPaneController);
        controllers.put(skillsPaneController.getClass(), skillsPaneController);
        controllers.put(professionClassPaneController.getClass(), professionClassPaneController);
        controllers.put(professionPaneController.getClass(), professionPaneController);
        controllers.put(racesPaneController.getClass(), racesPaneController);
        controllers.put(playerPaneController.getClass(), playerPaneController);
    }

    @Override
    public Object call(Class<?> param) {
        return controllers.get(param);
    }
}
