package pl.khuzzuk.wfrpchar.entities.items;

import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;

import java.util.Collection;

public interface Weapon extends Commodity {
    int getStrength();

    String getTypeName();

    Weapon getBaseType();

    Collection<Determinant> getDeterminants();

    void addDeterminant(Determinant determinant);

    Collection<Determinant> getAllDeterminants();

    Collection<Determinant> getDeterminantForType(DeterminantsType type);
}
