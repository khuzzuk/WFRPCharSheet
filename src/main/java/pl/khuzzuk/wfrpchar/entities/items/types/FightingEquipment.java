package pl.khuzzuk.wfrpchar.entities.items.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import pl.khuzzuk.wfrpchar.entities.LangElement;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;
import pl.khuzzuk.wfrpchar.entities.items.BattleEquipment;
import pl.khuzzuk.wfrpchar.entities.items.Placement;
import pl.khuzzuk.wfrpchar.entities.items.Wearable;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.*;
import java.util.stream.Collectors;

@DiscriminatorValue("2")
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public abstract class FightingEquipment extends Item implements Wearable, BattleEquipment {
    @Min(0)
    int strength;
    @NonNull
    Placement placement;
    @NonNull
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "DET_EQ_MAP",
            joinColumns = {@JoinColumn(name = "EQ_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DET_ID")})
    Set<Determinant> determinants;
    @NonNull
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "TEXT", nullable = false)
    @JoinTable(name = "LAN_NAMES_MAP",
            joinColumns = {@JoinColumn(name = "ITEM_ID")})
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
    public Collection<Determinant> getAllDeterminants() {
        return determinants != null ? determinants : Collections.emptyList();
    }

    @Override
    @JsonIgnore
    public Collection<Determinant> getBaseDeterminants() {
        return getDeterminants();
    }
}
