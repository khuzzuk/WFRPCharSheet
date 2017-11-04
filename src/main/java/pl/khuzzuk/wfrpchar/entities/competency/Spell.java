package pl.khuzzuk.wfrpchar.entities.competency;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import pl.khuzzuk.wfrpchar.entities.*;
import pl.khuzzuk.wfrpchar.entities.items.types.MiscItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
public class Spell implements Featured, Persistable, Documented {
    @Getter
    @Setter
    @Id
    @GeneratedValue
    private long id;
    @Getter
    @Setter
    @NaturalId
    private String name;
    @Getter
    @Setter
    private LoadingTimes castTime;
    @Getter
    @Setter
    private int magicCost;
    @Getter
    @Setter
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private MagicSchool school;
    @Getter
    @Setter
    private int level;
    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<MiscItem> ingredients;
    @Getter
    @Setter
    private String description;

    public static Spell fromCsv(String[] fields) {
        Spell spell = new Spell();
        spell.setName(fields[0]);
        spell.setCastTime(LoadingTimes.valueOf(fields[1]));
        spell.setMagicCost(Integer.valueOf(fields[2]));
        spell.setLevel(Integer.valueOf(fields[4]));
        spell.setDescription(fields[6]);
        return spell;
    }

    @Override
    public String toCsv() {
        return new CsvBuilder(new ArrayList<>())
                .add(getName())
                .add(getCastTime().name())
                .add(getMagicCost() + "")
                .add(getSchool())
                .add(getLevel() + "")
                .add(Named.toCsv(getIngredients(), "|"))
                .add(getDescription())
                .build();
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    @JsonIgnore
    public String getSpecialFeatures() {
        return description;
    }

    @Override
    public void setSpecialFeatures(String specialFeatures) {
        description = specialFeatures;
    }
}
