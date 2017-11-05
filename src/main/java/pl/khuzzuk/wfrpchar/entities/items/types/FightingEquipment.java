package pl.khuzzuk.wfrpchar.entities.items.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.BattleEquipment;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.Wearable;

import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public abstract class FightingEquipment extends Item implements Wearable, BattleEquipment {
    int strength;
    Placement placement;
    Set<Determinant> determinants;
    Map<LangElement, String> names;

    @JsonIgnore
    String getLangToCsv() {
        return (names.get(LangElement.ADJECTIVE_MASC_SING) != null ? names.get(LangElement.ADJECTIVE_MASC_SING) : "") +
                "|" +
                (names.get(LangElement.ADJECTIVE_FEM_SING) != null ? names.get(LangElement.ADJECTIVE_FEM_SING) : "") +
                "|" +
                (names.get(LangElement.ADJECTIVE_NEUTR_SING) != null ? names.get(LangElement.ADJECTIVE_NEUTR_SING) : "") +
                "|" +
                (names.get(LangElement.ABLATIVE) != null ? names.get(LangElement.ABLATIVE) : "");
    }

    @JsonIgnore
    public Collection<Determinant> getDeterminantForType(DeterminantsType type) {
        return determinants.stream().filter(d -> d.getLabel() == type)
                .collect(Collectors.toList());
    }

    public void addDeterminant(Determinant determinant) {
        if (determinants == null) {
            determinants = new HashSet<>();
        }
        determinants.add(determinant);
    }

    @JsonIgnore
    @Override
    public Collection<Determinant> getAllDeterminants() {
        return determinants != null ? determinants : Collections.emptyList();
    }

    @Override
    @JsonIgnore
    public Collection<Determinant> getBaseDeterminants() {
        return getDeterminants();
    }
}
