package pl.khuzzuk.wfrpchar.entities.items;

import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;

import java.util.Collection;

public interface Bastard extends HandWeapon {
    Collection<Determinant> getOneHandedDeterminants();

    int getOneHandedStrength();
}
