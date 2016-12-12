package pl.khuzzuk.wfrpchar.entities.competency;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import pl.khuzzuk.wfrpchar.db.DAO;
import pl.khuzzuk.wfrpchar.entities.*;
import pl.khuzzuk.wfrpchar.entities.items.types.MiscItem;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Set;

@Entity
public class Spell implements Named<String>, Persistable, Documented {
    @Getter
    @Setter
    @Id
    @GeneratedValue
    private long id;
    @Getter
    @Setter
    @NaturalId
    private String name;
    @Getter
    @Setter
    private LoadingTimes castTime;
    @Getter
    @Setter
    private int magicCost;
    @Getter
    @Setter
    @ManyToOne
    private MagicSchool school;
    @Getter
    @Setter
    private int level;
    @Getter
    @Setter
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<MiscItem> ingredients;
    @Getter
    @Setter
    private String description;

    public static Spell fromCsv(String[] fields, DAO dao) {
        Spell spell = new Spell();
        spell.setName(fields[0]);
        spell.setCastTime(LoadingTimes.valueOf(fields[1]));
        spell.setMagicCost(Integer.valueOf(fields[2]));
        spell.setSchool(dao.getEntity(MagicSchool.class, fields[3]));
        spell.setLevel(Integer.valueOf(fields[4]));
        spell.setIngredients(dao.getEntities(MiscItem.class, fields[5].split("\\|")));
        spell.setDescription(fields[6]);
        return spell;
    }

    @Override
    public String toCsv() {
        return new CsvBuilder(new ArrayList<>())
                .add(getName())
                .add(getCastTime().name())
                .add(getMagicCost() + "")
                .add(getSchool())
                .add(getLevel() + "")
                .add(Named.toCsv(getIngredients(), "|"))
                .add(getDescription())
                .build();
    }
}
