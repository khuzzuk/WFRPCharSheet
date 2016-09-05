package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Labelled;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.BastardWeaponType;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.WhiteWeaponType;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Component
@MainWindowBean
public class ItemsLoaderToGui {
    private Map<DeterminantsType, TextField> whiteWeaponModifiers;
    private Map<DeterminantsType, TextField> bastWhiteWeaponMods;
    private Map<LangElement, TextField> whiteWeaponLangFields;
    @Inject
    @Publishers
    private GuiPublisher guiPublisher;
    @Inject
    @MainWindowBean
    private MainWindowController controller;
    @Inject
    private ArmorTypesPaneController armorTypesController;

    void loadWhiteWeaponToEditor(WhiteWeaponType weaponType) {
        controller.nameWW.setText(weaponType.getName());
        controller.typeNameWW.setText(weaponType.getTypeName());
        controller.accessibilityBoxWW.getSelectionModel().select(weaponType.getAccessibility().getName());
        controller.placementBoxWW.getSelectionModel().select(weaponType.getPlacement().getName());
        controller.diceWW.getSelectionModel().select(weaponType.getDices().name());
        controller.rollsWW.adjustValue(weaponType.getRolls());
        controller.weightWW.setText("" + weaponType.getWeight());
        controller.goldWW.setText("" + weaponType.getPrice().getGold());
        controller.silverWW.setText("" + weaponType.getPrice().getSilver());
        controller.leadWW.setText("" + weaponType.getPrice().getLead());
        controller.strengthBasicWW.setText("" + weaponType.getStrength());
        weaponType.getDeterminants().forEach(d -> mapTypeToField(whiteWeaponModifiers, d));
        weaponType.getNames().forEach((lang, val) -> whiteWeaponLangFields.get(lang).setText(val));
        controller.specialFeaturesWW.setText(weaponType.getSpecialFeature());
        if (weaponType instanceof BastardWeaponType) {
            BastardWeaponType bastard = (BastardWeaponType) weaponType;
            controller.strengthBastardWW.setText("" + bastard.getOneHandedStrength());
            bastard.getOneHandedDeterminants().forEach(d -> mapTypeToField(bastWhiteWeaponMods, d));
        }
    }

    void saveWhiteWeaponType() {
        List<String> fields = new LinkedList<>();
        if (controller.nameWW.getText().length() == 0) return;
        fields.add(controller.nameWW.getText());
        fields.add(controller.weightWW.getText());
        fields.add(controller.goldWW.getText() + "|" +
                controller.silverWW.getText() + "|" +
                controller.leadWW.getText());
        fields.add(Accessibility.forName(controller.accessibilityBoxWW.getSelectionModel().getSelectedItem()).name());
        fields.add(controller.specialFeaturesWW.getText());
        fields.add(controller.strengthBasicWW.getText());
        fields.add("WEAPON");
        fields.add(Placement.forName(controller.placementBoxWW.getSelectionModel().getSelectedItem()).name());
        fields.add(controller.langMascWW.getText() + "|" + controller.langFemWW.getText() + "|" +
                controller.langNeutrWW.getText() + "|" + controller.langAblativeWW.getText());
        String line = EnumSet.allOf(DeterminantsType.class).stream()
                .filter(d -> whiteWeaponModifiers.get(d) != null && whiteWeaponModifiers.get(d).getText().length() > 0)
                .map(d -> "" + whiteWeaponModifiers.get(d).getText() + "," + d.name()).collect(Collectors.joining("|"));
        fields.add(line);
        fields.add(controller.typeNameWW.getText());
        fields.add(controller.diceWW.getSelectionModel().getSelectedItem());
        fields.add(Integer.toString((int) controller.rollsWW.getValue()));
        if (controller.placementBoxWW.getSelectionModel().getSelectedIndex() == 2) {
            fields.add(controller.strengthBastardWW.getText());
            line = EnumSet.allOf(DeterminantsType.class).stream()
                    .filter(d -> bastWhiteWeaponMods.get(d) != null && bastWhiteWeaponMods.get(d).getText().length() > 0)
                    .map(d -> "" + bastWhiteWeaponMods.get(d).getText() + "," + d.name())
                    .collect(Collectors.joining("|"));
            fields.add(line);
        }
        guiPublisher.saveItem(fields.stream().collect(Collectors.joining(";")));
    }

    void initFieldsMap() {
        whiteWeaponModifiers = new HashMap<>();
        whiteWeaponModifiers.put(DeterminantsType.BATTLE, controller.battleModWW);
        whiteWeaponModifiers.put(DeterminantsType.INITIATIVE, controller.initModWW);
        whiteWeaponModifiers.put(DeterminantsType.PARRY, controller.parryModWW);
        whiteWeaponModifiers.put(DeterminantsType.OPPONENT_PARRY, controller.opponentParryModWW);
        bastWhiteWeaponMods = new HashMap<>();
        bastWhiteWeaponMods.put(DeterminantsType.BATTLE, controller.bastBattleModWW);
        bastWhiteWeaponMods.put(DeterminantsType.INITIATIVE, controller.bastInitModWW);
        bastWhiteWeaponMods.put(DeterminantsType.PARRY, controller.bastParryModWW);
        bastWhiteWeaponMods.put(DeterminantsType.OPPONENT_PARRY, controller.bastOpParryModWW);
        whiteWeaponLangFields = new HashMap<>();
        whiteWeaponLangFields.put(LangElement.ADJECTIVE_MASC_SING, controller.langMascWW);
        whiteWeaponLangFields.put(LangElement.ADJECTIVE_FEM_SING, controller.langFemWW);
        whiteWeaponLangFields.put(LangElement.ADJECTIVE_NEUTR_SING, controller.langNeutrWW);
        whiteWeaponLangFields.put(LangElement.ABLATIVE, controller.langAblativeWW);
    }

    private <T> void mapTypeToField(Map<T, TextField> fields, Labelled<T, String> content) {
        TextField field = fields.get(content.getLabel());
        if (field != null) field.setText(content.getRepresentation());
    }
}
