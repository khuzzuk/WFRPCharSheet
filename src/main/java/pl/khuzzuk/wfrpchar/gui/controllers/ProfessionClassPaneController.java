package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;

import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;

@Component
public class ProfessionClassPaneController extends SkillViewController<ProfessionClass> {
    @FXML
    private ComboBox<DeterminantsType> baseDeterminantType;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ComboBoxHandler.fillWithEnums(EnumSet.allOf(DeterminantsType.class), baseDeterminantType);
        entityType = ProfessionClass.class;
        skillChooserMsg = messages.getProperty("professions.class.skills.getAllTypes");
        initItems();
    }

    @Override
    void addConverters() {
        super.addConverters();
        addConverter(baseDeterminantType::getValue, ProfessionClass::setPrimaryDeterminant);
    }

    @Override
    public void loadItem(ProfessionClass professionClass) {
        super.loadItem(professionClass);
        baseDeterminantType.getSelectionModel().select(professionClass.getPrimaryDeterminant());
    }

    @Override
    ProfessionClass supplyNewItem() {
        return new ProfessionClass();
    }

    void clear() {
        super.clear();
        baseDeterminantType.getSelectionModel().clearSelection();
    }
}
