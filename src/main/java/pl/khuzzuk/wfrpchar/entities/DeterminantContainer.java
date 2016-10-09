package pl.khuzzuk.wfrpchar.entities;

import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public interface DeterminantContainer {
    Set<Determinant> getDeterminants();

    void setDeterminants(Set<Determinant> determinants);

    default Collection<Determinant> getDeterminantForType(DeterminantsType type) {
        return getDeterminants().stream().filter(d -> d.getLabel() == type)
                .collect(Collectors.toList());
    }

    default int getDeterminantValueForType(DeterminantsType type) {
        return getDeterminantForType(type).stream().mapToInt(Determinant::getActualValue).sum();
    }
}
