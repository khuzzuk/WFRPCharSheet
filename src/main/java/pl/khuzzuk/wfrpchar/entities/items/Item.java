package pl.khuzzuk.wfrpchar.entities.items;

import lombok.*;
import pl.khuzzuk.wfrpchar.entities.Nameable;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.Price;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue(value = "0")
@ToString(exclude = "id")
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public abstract class Item implements Nameable, Persistable {
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

    public abstract String toCsv();

    public enum Accessibility {
        COMMON("Powszechny"), UNCOMMON("Trudno dostępny"), SCARCE("Niespotykany"), RARE("Rzadki"), EXCEPTIONAL("Niedostępny");

        @Getter
        private String name;

        Accessibility(String name) {
            this.name = name;
        }

        public static Accessibility forName(String name) {
            switch (name) {
                case "Powszechny":
                    return COMMON;
                case "Trudno dostępny":
                    return UNCOMMON;
                case "Niespotykany":
                    return SCARCE;
                case "Rzadki":
                    return RARE;
                case "Niedostępny":
                    return EXCEPTIONAL;
                default:
                    throw new IllegalArgumentException("cannot resolve Accessibility level enum with " + name);
            }
        }
    }
}
