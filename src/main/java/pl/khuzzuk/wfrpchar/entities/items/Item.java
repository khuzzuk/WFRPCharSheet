package pl.khuzzuk.wfrpchar.entities.items;

import lombok.*;
import pl.khuzzuk.wfrpchar.entities.Price;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue(value = "0")
@ToString(exclude = "id")
@NoArgsConstructor
public abstract class Item {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    long id;
    @Getter
    @Setter
    @NonNull
    String name;
    @Getter
    @Setter
    @NonNull
    float weight;
    @Getter
    @Setter
    @NonNull
    @Embedded
    Price price;
    @Getter
    @Setter
    @NonNull
    Accessibility accessibility;
    @Getter
    @Setter
    String specialFeature;

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
