package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Context;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;
import pl.khuzzuk.wfrpchar.entities.items.ArmorPattern;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.types.ArmorType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.MappingUtil;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ArmorTypesPaneController extends ItemsListedController<ArmorType> {
    private static final String TYPE = "ARMOR";
    private Map<DeterminantsType, TextField> determinantsMap;
    private Map<LangElement, TextField> langElementsMap;

    @FXML
    private ComboBox<String> armPlacement;
    @FXML
    private TextField armMasc;
    @FXML
    private TextField armFem;
    @FXML
    private TextField armNeutr;
    @FXML
    private TextField armAbl;
    @FXML
    @Numeric
    TextField armBattleMod;
    @FXML
    @Numeric
    TextField armShootingMod;
    @FXML
    @Numeric
    TextField armOpponentParryMod;
    @FXML
    @Numeric
    TextField armParryMod;
    @FXML
    private ComboBox<String> armPattern;
    @FXML
    @Numeric
    TextField armStrength;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        entityType = ArmorType.class;
        removeEntityTopic = messages.getProperty("armorTypes.remove");
        getEntityTopic = messages.getProperty("armorTypes.query.specific");
        getAllResponse = messages.getProperty("armorTypes.result");
        saveTopic = messages.getProperty("database.saveEquipment");
        saveAction = this::saveArmorType;
        clearAction = this::clear;
        ComboBoxHandler.fill(Accessibility.SET, accessibility);
        ComboBoxHandler.fill(EnumSet.allOf(ArmorPattern.class), armPattern);
        ComboBoxHandler.fill(EnumSet.of(Placement.CORPUS, Placement.HEAD,
                Placement.BELT, Placement.HANDS,
                Placement.LEGS, Placement.FEET),
                armPlacement);
        initMaps();
        initItems();
    }

    private void initMaps() {
        determinantsMap = new HashMap<>();
        determinantsMap.put(DeterminantsType.BATTLE, armBattleMod);
        determinantsMap.put(DeterminantsType.SHOOTING, armShootingMod);
        determinantsMap.put(DeterminantsType.OPPONENT_PARRY, armOpponentParryMod);
        determinantsMap.put(DeterminantsType.PARRY, armParryMod);
        langElementsMap = new HashMap<>();
        langElementsMap.put(LangElement.ADJECTIVE_MASC_SING, armMasc);
        langElementsMap.put(LangElement.ADJECTIVE_FEM_SING, armFem);
        langElementsMap.put(LangElement.ADJECTIVE_NEUTR_SING, armNeutr);
        langElementsMap.put(LangElement.ABLATIVE, armAbl);
    }

    @Override
    public void loadItem(ArmorType armor) {
        name.setText(armor.getName());
        weight.setText(armor.getWeight() + "");
        accessibility.getSelectionModel().select(armor.getAccessibility().getName());
        lead.setText(armor.getPrice().getLead() + "");
        silver.setText(armor.getPrice().getSilver() + "");
        gold.setText(armor.getPrice().getGold() + "");
        armStrength.setText(armor.getStrength() + "");
        armPattern.getSelectionModel().select(armor.getPattern().getName());
        armPlacement.getSelectionModel().select(armor.getPlacement().getName());
        armor.getDeterminants()
                .forEach(a -> MappingUtil.mapDeterminant(a, determinantsMap));
        armor.getNames()
                .forEach((k, v) -> MappingUtil.mapDeterminant(new Context<>(k, v), langElementsMap));
        specialFeatures.setText(armor.getSpecialFeatures());
    }

    @FXML
    private void saveArmorType() {
        if (name.getText().length() < 3) {
            return;
        }
        List<String> fields = new LinkedList<>();
        fields.add(name.getText());
        fields.add(weight.getText());
        fields.add(getPriceFromFields());
        fields.add(Accessibility.forName(accessibility.getSelectionModel().getSelectedItem()).name());
        fields.add(specialFeatures.getText());
        fields.add(armStrength.getText());
        fields.add(TYPE);
        fields.add(Placement.forName(armPlacement.getSelectionModel().getSelectedItem()).name());
        fields.add(armMasc.getText() + "|" +
                armFem.getText() + "|" +
                armNeutr.getText() + "|" +
                armAbl.getText());
        fields.add(MappingUtil.getDeterminants(determinantsMap));
        fields.add(ArmorPattern.forName(armPattern.getSelectionModel().getSelectedItem()).name());
        saveItem(fields.stream().collect(Collectors.joining(";")));
    }

    @Override
    @FXML
    void clear() {
        super.clear();
        armStrength.clear();
        armPlacement.getSelectionModel().clearSelection();
        langElementsMap.values().forEach(TextField::clear);
        determinantsMap.values().forEach(TextField::clear);
        armPattern.getSelectionModel().clearSelection();
    }
}
