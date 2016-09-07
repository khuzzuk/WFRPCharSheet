package pl.khuzzuk.wfrpchar.entities.items;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.Named;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "class", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("1")
@ToString(exclude = "id")
@NoArgsConstructor
public abstract class Weapon implements Named<String>, Commodity {
    @Id
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private String name;
}
