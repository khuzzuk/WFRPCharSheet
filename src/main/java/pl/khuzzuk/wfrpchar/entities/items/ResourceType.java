package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.Named;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@NoArgsConstructor
@ToString(exclude = "id")
public class ResourceType implements Named<String> {
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

    public static ResourceType getFromCsv(String line) {
        String[] fields = line.split(";");
        ResourceType resource = new ResourceType();
        resource.name = fields[0];
        resource.strengthMod = Integer.parseInt(fields[1]);
        resource.priceMod = Integer.parseInt(fields[2]);
        return resource;
    }
}
