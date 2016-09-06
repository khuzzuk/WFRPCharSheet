package pl.khuzzuk.wfrpchar.gui;

import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;
import pl.khuzzuk.wfrpchar.entities.Labelled;
import pl.khuzzuk.wfrpchar.messaging.publishers.Publishers;

import javax.inject.Inject;
import java.util.Map;

@Component
@MainWindowBean
public class ItemsLoaderToGui {
    @Inject
    @Publishers
    private GuiPublisher guiPublisher;
    @Inject
    @MainWindowBean
    private MainWindowController controller;
    @Inject
    private ArmorTypesPaneController armorTypesController;


    private <T> void mapTypeToField(Map<T, TextField> fields, Labelled<T, String> content) {
        TextField field = fields.get(content.getLabel());
        if (field != null) field.setText(content.getRepresentation());
    }
}
