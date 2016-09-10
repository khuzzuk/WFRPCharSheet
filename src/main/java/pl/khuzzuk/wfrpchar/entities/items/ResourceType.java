package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Named;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@ToString(exclude = "id")
@Embeddable
public class ResourceType implements Named<String>, Documented {
    @Id
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

    public static ResourceType getFromCsv(String line) {
        String[] fields = line.split(";");
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
