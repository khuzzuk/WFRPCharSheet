package pl.khuzzuk.wfrpchar.entities.competency;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.Featured;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = MagicSchool.class)
@Getter
@Setter
public class MagicSchool implements Featured {
    private String name;
    private String description;

    @Override
    public String toString() {
        return name;
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
