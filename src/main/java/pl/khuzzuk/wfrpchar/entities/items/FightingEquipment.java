package pl.khuzzuk.wfrpchar.entities.items;

import lombok.*;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.LangElement;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@DiscriminatorValue("2")
@Entity
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class FightingEquipment extends Item {
    @NonNull
    @Getter
    @Setter
    int strength;
    @NonNull
    @Getter
    @Setter
    EquipmentType type;
    @NonNull
    @Getter
    @Setter
    Placement placement;
    @NonNull
    @Setter
    @Getter
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "DET_EQ_MAP",
            joinColumns = {@JoinColumn(name = "EQ_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DET_ID")})
    Set<Determinant> determinants;
    @NonNull
    @Setter
    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "TEXT", nullable = false)
    @JoinTable(name = "LAN_NAMES_MAP",
            joinColumns = {@JoinColumn(name = "ITEM_ID")})
            Map<LangElement, String> names;

    String getLangToCsv() {
        return (names.get(LangElement.ADJECTIVE_MASC_SING) != null ? names.get(LangElement.ADJECTIVE_MASC_SING) : "") +
                "|" +
                (names.get(LangElement.ADJECTIVE_FEM_SING) != null ? names.get(LangElement.ADJECTIVE_FEM_SING) : "") +
                "|" +
                (names.get(LangElement.ADJECTIVE_NEUTR_SING) != null ? names.get(LangElement.ADJECTIVE_NEUTR_SING) : "") +
                "|" +
                (names.get(LangElement.ABLATIVE) != null ? names.get(LangElement.ABLATIVE) : "");
    }

    String determinantsToCsv(Set<Determinant> determinants) {
        return determinants.stream().map(Determinant::toCSV).collect(Collectors.joining("|"));
    }
}
