package pl.khuzzuk.wfrpchar.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.characters.Appearance;
import pl.khuzzuk.wfrpchar.entities.characters.PersonalHistory;
import pl.khuzzuk.wfrpchar.entities.competency.Profession;
import pl.khuzzuk.wfrpchar.entities.competency.ProfessionClass;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.items.usable.AbstractCommodity;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@ToString(exclude = "id")
public class Player implements Named<String>, Persistable {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    private ProfessionClass professionClass;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    private Profession currentProfession;
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Profession> career;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.EAGER)
    private Character character;
    @Getter
    @Setter
    private Appearance appearance;
    @Getter
    @Setter
    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Determinant> determinants;
    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<AbstractCommodity> commodities;
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Skill> skills;
    @Getter
    @Setter
    private Price money;
    @Getter
    @Setter
    private PersonalHistory personalHistory;
}
