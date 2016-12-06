package pl.khuzzuk.wfrpchar.entities.competency;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.entities.LoadingTimes;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.items.types.Item;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Spell implements Named<String>, Persistable {
    @Getter
    @Setter
    @Id
    @GeneratedValue
    private long id;
    @Getter
    @Setter
    private String name;
    private LoadingTimes castTime;
    private int magicCost;
    @ManyToOne
    private MagicSchool school;
    private int level;
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<Item> ingredients;
    private String description;
}
