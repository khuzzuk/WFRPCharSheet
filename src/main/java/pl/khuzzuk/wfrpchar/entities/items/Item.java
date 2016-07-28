package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.Price;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue(value = "0")
@ToString(exclude = "id")
public abstract class Item {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    protected long id;
    @Getter
    @Setter
    @NonNull
    protected String name;
    @Getter
    @Setter
    @NonNull
    protected float weight;
    @Getter
    @Setter
    @NonNull
    @Embedded
    protected Price price;
    @Getter
    @Setter
    @NonNull
    protected Accessibility accessibility;
    @Getter
    @Setter
    private String specialFeature;

    public enum Accessibility {
        COMMON, UNCOMMON, SCARCE, RARE, EXCEPTIONAL;

        public static Accessibility forName(String name) {
            switch (name) {
                case "COMMON":
                    return COMMON;
                case "UNCOMMON":
                    return UNCOMMON;
                case "SCARCE":
                    return SCARCE;
                case "RARE":
                    return RARE;
                case "EXCEPTIONAL":
                    return EXCEPTIONAL;
                default:
                    throw new IllegalArgumentException("cannot resolve Accessibility level anum with " + name);
            }
        }
    }
}
