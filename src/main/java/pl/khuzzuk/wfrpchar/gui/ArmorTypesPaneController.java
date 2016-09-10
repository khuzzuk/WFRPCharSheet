package pl.khuzzuk.wfrpchar.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Context;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.*;
import pl.khuzzuk.wfrpchar.entities.items.types.ArmorType;
import pl.khuzzuk.wfrpchar.entities.items.types.Item;

import javax.inject.Inject;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ArmorTypesPaneController implements Controller {
    private static final String TYPE = "ARMOR";
    @Inject
    private GuiPublisher publisher;
    private Map<DeterminantsType, TextField> determinantsMap;
    private Map<LangElement, TextField> langElementsMap;

    @FXML
    private ComboBox<String> armPlacement;
    @FXML
    private TextField armSpecialFeatures;
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
    @FXML
    @Numeric
    TextField armGold;
    @FXML
    @Numeric
    TextField armSilver;
    @FXML
    @Numeric
    TextField armLead;
    @FXML
    private ComboBox<String> armAccessibility;
    @FXML
    @FloatNumeric
    TextField armWeight;
    @FXML
    private TextField armName;
    @FXML
    private ListView<String> armorTypesList;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeValidation();
        ComboBoxHandler.fill(EnumSet.allOf(Accessibility.class), armAccessibility);
        ComboBoxHandler.fill(EnumSet.allOf(ArmorPattern.class), armPattern);
        ComboBoxHandler.fill(EnumSet.of(Placement.CORPUS, Placement.HEAD,
                Placement.BELT, Placement.HANDS,
                Placement.LEGS, Placement.FEET),
                armPlacement);
        initMaps();
        publisher.requestArmorTypes();
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

    void loadArmorTypes(Collection<ArmorType> armors) {
        armorTypesList.getItems().clear();
        armorTypesList.getItems().addAll(armors.stream().map(Item::getName).collect(Collectors.toList()));
    }

    void loadArmorTypeToEditor(ArmorType armor) {
        armName.setText(armor.getName());
        armWeight.setText(armor.getWeight() + "");
        armAccessibility.getSelectionModel().select(armor.getAccessibility().getName());
        armLead.setText(armor.getPrice().getLead() + "");
        armSilver.setText(armor.getPrice().getSilver() + "");
        armGold.setText(armor.getPrice().getGold() + "");
        armStrength.setText(armor.getStrength() + "");
        armPattern.getSelectionModel().select(armor.getPattern().getName());
        armPlacement.getSelectionModel().select(armor.getPlacement().getName());
        armor.getDeterminants()
                .forEach(a -> MappingUtil.mapDeterminant(a, determinantsMap));
        armor.getNames()
                .forEach((k, v) -> MappingUtil.mapDeterminant(new Context<>(k, v), langElementsMap));
        armSpecialFeatures.setText(armor.getSpecialFeature());
    }

    @FXML
    void saveArmorType() {
        if (armName.getText().length() < 3) {
            return;
        }
        List<String> fields = new LinkedList<>();
        fields.add(armName.getText());
        fields.add(armWeight.getText());
        fields.add(armGold.getText() + "|" + armSilver.getText() + "|" + armLead.getText());
        fields.add(Accessibility.forName(armAccessibility.getSelectionModel().getSelectedItem()).name());
        fields.add(armSpecialFeatures.getText());
        fields.add(armStrength.getText());
        fields.add(TYPE);
        fields.add(Placement.forName(armPlacement.getSelectionModel().getSelectedItem()).name());
        fields.add(armMasc.getText() + "|" +
                armFem.getText() + "|" +
                armNeutr.getText() + "|" +
                armAbl.getText());
        fields.add(MappingUtil.getDeterminants(determinantsMap));
        fields.add(ArmorPattern.forName(armPattern.getSelectionModel().getSelectedItem()).name());
        publisher.saveItem(fields.stream().collect(Collectors.joining(";")));
    }

    @FXML
    void removeArmorType() {
        if (armName.getText().length() > 2) {
            publisher.removeArmorType(armName.getText());
        }
    }

    @FXML
    private void getArmorType() {
        String name = armorTypesList.getSelectionModel().getSelectedItem();
        if (name != null) {
            publisher.requestArmorTypeLoad(name);
        }
    }
}
