package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.LangElement;

import javax.persistence.*;
import java.util.Map;

@DiscriminatorValue("2")
@NoArgsConstructor
public abstract class FightingEquipment extends Item {
    @NonNull
    @Getter
    @Setter
    float strength;
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
    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "LAN_NAMES_MAP",
            joinColumns = {@JoinColumn(name = "ITEM_ID")},
            inverseJoinColumns = {@JoinColumn(name = "LANG_NAME_ID")})
    private Map<LangElement, String> names;
}
