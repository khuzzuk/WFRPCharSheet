package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Labelled;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.*;
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

    }

    void loadRangedWeaponToEditor(RangedWeaponType rangedWeapon) {
        controller.rwName.setText(rangedWeapon.getName());
        controller.rwTypeName.setText(rangedWeapon.getTypeName());
        controller.rwWeight.setText("" + rangedWeapon.getWeight());
        controller.rwGold.setText(rangedWeapon.getPrice().getGold() + "");
        controller.rwSilver.setText(rangedWeapon.getPrice().getSilver() + "");
        controller.rwLead.setText(rangedWeapon.getPrice().getLead() + "");
        controller.rwAccessibility.getSelectionModel().select(rangedWeapon.getAccessibility().getName());
        controller.rwSpecialFeatures.setText(rangedWeapon.getSpecialFeature());
        controller.rwStrength.setText(rangedWeapon.getStrength() + "");
        controller.rwMinRange.setText(rangedWeapon.getShortRange() + "");
        controller.rwMedRange.setText(rangedWeapon.getEffectiveRange() + "");
        controller.rwMaxRange.setText(rangedWeapon.getMaximumRange() + "");
        controller.rwLoadTime.getSelectionModel().select(rangedWeapon.getReloadTime().getName());
        controller.rwLangMasc.setText(rangedWeapon.getNames().get(LangElement.ADJECTIVE_MASC_SING));
        controller.rwLangFem.setText(rangedWeapon.getNames().get(LangElement.ADJECTIVE_FEM_SING));
        controller.rwLangNeutr.setText(rangedWeapon.getNames().get(LangElement.ADJECTIVE_NEUTR_SING));
        controller.rwLangAblative.setText(rangedWeapon.getNames().get(LangElement.ABLATIVE));
    }

    void saveRangedWeaponType() {
        if (controller.rwName.getText().length() == 0) return;
        List<String> fields = new LinkedList<>();
        fields.add(controller.rwName.getText());
        fields.add(controller.rwWeight.getText());
        fields.add(controller.rwGold.getText() + "|" +
                controller.rwSilver.getText() + "|" +
                controller.rwLead.getText());
        fields.add(Accessibility.forName(
                controller.rwAccessibility.getSelectionModel().getSelectedItem()).name());
        fields.add(controller.rwSpecialFeatures.getText());
        fields.add(controller.rwStrength.getText());
        fields.add("RANGED_WEAPON");
        fields.add("TWO_HANDS");
        fields.add(Optional.ofNullable(controller.rwLangMasc.getText()).orElse("") + "|" +
                Optional.ofNullable(controller.rwLangFem.getText()).orElse("") + "|" +
                Optional.ofNullable(controller.rwLangNeutr.getText()).orElse("") + "|" +
                Optional.ofNullable(controller.rwLangAblative.getText()).orElse(""));
        fields.add(""); //determinants
        fields.add(controller.rwTypeName.getText());
        fields.add(controller.rwMinRange.getText());
        fields.add(controller.rwMedRange.getText());
        fields.add(controller.rwMaxRange.getText());
        fields.add(LoadingTimes.forName(controller.rwLoadTime.getSelectionModel().getSelectedItem()).name());
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

    private <T> void mapTypeToField(Map<T, TextField> fields, Labelled<T> content) {
        TextField field = fields.get(content.getLabel());
        if (field != null) field.setText(content.getRepresentation());
    }

    void saveWhiteWeapon(MainWindowController controller) {
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
}
