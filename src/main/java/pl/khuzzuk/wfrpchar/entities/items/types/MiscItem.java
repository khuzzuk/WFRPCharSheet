package pl.khuzzuk.wfrpchar.entities.items.types;

import lombok.EqualsAndHashCode;
import pl.khuzzuk.wfrpchar.entities.Price;
import pl.khuzzuk.wfrpchar.entities.items.Accessibility;

//TODO move this to AbstractCommodity hierarchy
@EqualsAndHashCode(callSuper = true)
public class MiscItem extends Item {
    public MiscItem() {
        type = EquipmentType.MISC_ITEM;
    }

    public MiscItem(String name, float weight, Price price, Accessibility accessibility) {
        this();
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.accessibility = accessibility;
    }

    @Override
    public String toCsv() {
        return name + ";" + weight + ";" + price + ";" + accessibility + ";" +
                specialFeatures + ";;" + type;
    }
}
