package pl.khuzzuk.wfrpchar.entities.items;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.repo.TypeIdResolver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@ToString(exclude = "id")
@Getter
@Setter
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        resolver = TypeIdResolver.class,
        scope = ResourceType.class)
public class ResourceType implements Named<String>, Commodity, Persistable, Documented {
    @Id
    @GeneratedValue
    private long id;
    @NaturalId
    private String name;
    private int strengthMod;
    private int priceMod;
    private SubstanceType substanceType;
    private Accessibility accessibility;
    private String specialFeatures;

    public static ResourceType getFromCsv(String[] fields) {
        ResourceType resource = new ResourceType();
        resource.name = fields[0];
        resource.strengthMod = Integer.parseInt(fields[1]);
        resource.priceMod = Integer.parseInt(fields[2]);
        resource.substanceType = SubstanceType.valueOf(fields[3]);
        resource.accessibility = Accessibility.valueOf(fields[4]);
        if (fields.length >= 6) {
            resource.specialFeatures = fields[5];
        } else {
            resource.specialFeatures = "";
        }
        return resource;
    }

    public String toCsv() {
        return  name + ";" + strengthMod + ";" + priceMod + ";" + substanceType.name() + ";" +
                accessibility.name() + ";" + specialFeatures;
    }

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
