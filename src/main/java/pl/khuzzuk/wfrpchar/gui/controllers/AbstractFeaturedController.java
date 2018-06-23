package pl.khuzzuk.wfrpchar.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import pl.khuzzuk.wfrpchar.entities.DeterminantContainer;
import pl.khuzzuk.wfrpchar.entities.Featured;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.gui.EntitiesAdapter;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public abstract class AbstractFeaturedController<T extends DeterminantContainer & Featured> extends ItemsListedController<T> {
    @FXML
    ListView<Determinant> determinantsView;
    String showDeterminantCreatorMsg;

    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }

    public void addDeterminant(Determinant determinant) {
        determinantsView.getItems().add(determinant);
    }

    @FXML
    private void chooseDeterminant() {
        bus.send(showDeterminantCreatorMsg);
    }

    @FXML
    private void removeDeterminant() {
        EntitiesAdapter.removeSelected(determinantsView);
    }

    @Override
    void loadItem(T item) {
        super.loadItem(item);
        Optional.ofNullable(determinantsView).ifPresent(view ->
                EntitiesAdapter.sendToListViewUnchanged(view, item.getDeterminants()));
    }

    @Override
    void addConverters() {
        super.addConverters();
        Optional.ofNullable(determinantsView)
                .ifPresent(view -> addConverter(view::getItems, DeterminantContainer::setDeterminants, HashSet::new));
    }

    @FXML
    @Override
    void clear() {
        super.clear();
        Optional.ofNullable(determinantsView).map(ListView::getItems).ifPresent(List::clear);
    }
}
