package pl.khuzzuk.wfrpchar.entities.competency;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.*;
import pl.khuzzuk.wfrpchar.entities.items.types.MiscItem;

import java.util.ArrayList;
import java.util.Set;

@Getter
@Setter
public class Spell implements Featured, Persistable, Documented {
    private long id;
    private String name;
    private LoadingTimes castTime;
    private int magicCost;
    @JsonIdentityReference(alwaysAsId = true)
    private MagicSchool school;
    private int level;
    @JsonIdentityReference(alwaysAsId = true)
    private Set<MiscItem> ingredients;
    private String description;

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
