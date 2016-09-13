package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Persistable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@ToString(exclude = "id")
public class ResourceType implements Named<String>, Persistable, Documented {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int strengthMod;
    @Getter
    @Setter
    private int priceMod;
    @Getter
    @Setter
    private SubstanceType substanceType;

    public static ResourceType getFromCsv(String[] fields) {
        ResourceType resource = new ResourceType();
        resource.name = fields[0];
        resource.strengthMod = Integer.parseInt(fields[1]);
        resource.priceMod = Integer.parseInt(fields[2]);
        resource.substanceType = SubstanceType.valueOf(fields[3]);
        return resource;
    }

    public String toCsv() {
        return  name + ";" + strengthMod + ";" + priceMod + ";" + substanceType.name();
    }
}
