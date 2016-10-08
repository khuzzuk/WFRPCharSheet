package pl.khuzzuk.wfrpchar.gui.controllers;

import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Component
public class ProfessionPaneController extends AbstractFeaturedController {
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showDeterminantCreatorMsg = messages.getProperty("determinants.creator.show.pr");
        getAction = guiPublisher::requestProfession;
        removeAction = guiPublisher::removeProfession;
        saveAction = this::save;
        guiPublisher.requestProfessions();
    }

    public void loadToEditor(Profession profession) {
        name.setText(profession.getName());
        specialFeatures.setText(profession.getSpecialFeatures());
        EntitiesAdapter.sendToListView(determinantsView, profession.getDeterminants());
    }

    private void save() {
        List<String> fields = new ArrayList<>();
        fields.add(name.getText());
        fields.add(specialFeatures.getText());
        fields.add("");
        fields.add("");
        fields.add(Determinant.determinantsToCsv(Determinant.parseFromGui(
                determinantsView.getItems().stream().collect(Collectors.toList()))));
        guiPublisher.publish(fields.stream().collect(Collectors.joining(";")), messages.getProperty("professions.save"));
    }
}
