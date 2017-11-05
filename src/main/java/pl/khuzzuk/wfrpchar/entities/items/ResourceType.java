package pl.khuzzuk.wfrpchar.entities.items;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

@NoArgsConstructor
@ToString(exclude = "id")
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.IntSequenceGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = ResourceType.class)
public class ResourceType implements Named<String>, Commodity {
    private String name;
    private int strengthMod;
    private int priceMod;
    private SubstanceType substanceType;
    private Accessibility accessibility;
    private String specialFeatures;

    @Override
    @JsonIgnore
    public Price getPrice() {
        return new Price(1,0,0).multiply(((float)priceMod)/100);
    }

    @Override
    public void setPrice(Price price) {
        throw new UnsupportedOperationException();
    }

    @Override
    @JsonIgnore
    public float getWeight() {
        //TODO add weigthmod to resources
        return 1;
    }

    @Override
    public void setWeight(float weight) {
        throw new UnsupportedOperationException();
    }
}
