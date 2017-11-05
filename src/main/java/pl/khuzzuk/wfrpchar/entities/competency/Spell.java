package pl.khuzzuk.wfrpchar.entities.competency;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.Featured;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.items.types.MiscItem;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

import java.util.Set;

@Getter
@Setter
@ToString(of = "name")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = Spell.class)
public class Spell implements Featured {
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
    @JsonIgnore
    public String getSpecialFeatures() {
        return description;
    }

    @Override
    public void setSpecialFeatures(String specialFeatures) {
        description = specialFeatures;
    }
}
