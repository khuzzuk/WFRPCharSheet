package pl.khuzzuk.wfrpchar.entities.items;

import pl.khuzzuk.wfrpchar.entities.DeterminantContainer;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;

import java.util.Collection;

public interface BattleEquipment extends Commodity, DeterminantContainer {

    Placement getPlacement();

    int getStrength();

    String getTypeName();

    void addDeterminant(Determinant determinant);

    Collection<Determinant> getBaseDeterminants();
}
