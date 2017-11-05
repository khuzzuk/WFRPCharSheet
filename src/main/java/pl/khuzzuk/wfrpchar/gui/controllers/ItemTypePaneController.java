package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import org.apache.commons.lang3.StringUtils;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.FightingEquipment;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class ItemTypePaneController<T extends FightingEquipment> extends CommodityPaneController<T> {
    @FXML
    ComboBox<Placement> placementBox;
    @FXML
    TextField langMasc;
    @FXML
    TextField langFem;
    @FXML
    TextField langNeutr;
    @FXML
    TextField langAblative;
    Map<LangElement, TextField> langFields;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        langFields = new HashMap<>();
        langFields.put(LangElement.ADJECTIVE_MASC_SING, langMasc);
        langFields.put(LangElement.ADJECTIVE_FEM_SING, langFem);
        langFields.put(LangElement.ADJECTIVE_NEUTR_SING, langNeutr);
        langFields.put(LangElement.ABLATIVE, langAblative);
    }

    @Override
    void addConverters() {
        super.addConverters();
        addConverter(placementBox::getValue, FightingEquipment::setPlacement);
        addConverter(this::getLangNames, FightingEquipment::setNames);
    }

    private Map<LangElement, String> getLangNames() {
        return langFields.entrySet().stream()
                .filter(entry -> StringUtils.isNoneBlank(entry.getValue().getText()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getText()));
    }

    Set<Determinant> getModifiers(Map<DeterminantsType, TextField> modifiers) {
        return modifiers.entrySet().stream()
                .filter(entry -> StringUtils.isNoneBlank(entry.getValue().getText()))
                .map(entry -> {
                    Determinant determinant = entry.getKey().getDeterminant();
                    determinant.setBaseValue(Integer.parseInt(entry.getValue().getText()));
                    return determinant;
                }).collect(Collectors.toSet());
    }

    @Override
    void clear() {
        super.clear();
        placementBox.getSelectionModel().clearSelection();
        langFields.values().forEach(TextInputControl::clear);
    }
}
