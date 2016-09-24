package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import pl.khuzzuk.wfrpchar.entities.items.ResourceType;
import pl.khuzzuk.wfrpchar.gui.ComboBoxHandler;
import pl.khuzzuk.wfrpchar.gui.GuiPublisher;
import pl.khuzzuk.wfrpchar.gui.Numeric;

import javax.inject.Inject;
import javax.inject.Named;
import java.net.URL;
import java.util.Collection;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractWeaponController extends ItemsListedController {
    @FXML
    ComboBox<String> secondaryResource;
    @FXML
    ComboBox<String> primaryResource;
    @FXML
    @Numeric
    TextField gold;
    @FXML
    @Numeric
    TextField silver;
    @FXML
    @Numeric
    TextField lead;
    @FXML
    ListView<String> determinantsView;
    @FXML
    TextArea specialFeatures;

    Collection<ResourceType> resources;
    @Inject
    @Named("messages")
    Properties messages;
    @Inject
    GuiPublisher guiPublisher;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    public void fillResourceBoxes(Collection<ResourceType> resourceTypes) {
        resources = resourceTypes;
        Set<ResourceType> toFill = resources.stream().collect(Collectors.toSet());
        ComboBoxHandler.fill(toFill, primaryResource);
        ComboBoxHandler.fill(toFill, secondaryResource);
    }
}
