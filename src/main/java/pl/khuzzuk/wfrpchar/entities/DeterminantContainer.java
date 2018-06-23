package pl.khuzzuk.wfrpchar.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public interface DeterminantContainer {
    Collection<Determinant> getAllDeterminants();
    Collection<Determinant> getDeterminants();

    void setDeterminants(Set<Determinant> determinants);

    @JsonIgnore
    default Collection<Determinant> getDeterminantForType(DeterminantsType type) {
        return getAllDeterminants().stream().filter(d -> d.getLabel() == type)
                .collect(Collectors.toList());
    }

    @JsonIgnore
    default int getDeterminantValueForType(DeterminantsType type) {
        return getDeterminantForType(type).stream().mapToInt(Determinant::getActualValue).sum();
    }
}
