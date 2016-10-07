package pl.khuzzuk.wfrpchar.entities.competency;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@ToString(exclude = "id")
public class Profession implements Named<String>, Persistable, Documented {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String specialFeatures;
    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "PROF_DET",
            joinColumns = {@JoinColumn(name = "PROF_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DET_ID")})
    private Set<Skill> skills;
    @Getter
    @Setter
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "PROF_NEXTPROF",
            joinColumns = {@JoinColumn(name = "PRIM_PROF")},
            inverseJoinColumns = {@JoinColumn(name = "NEXT_PROF")})
    private Set<Profession> nextProfessions;
    @Getter
    @Setter
    @OneToMany
    @JoinTable(name = "PROF_DET",
            joinColumns = {@JoinColumn(name = "PROF_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DET_ID")})
    private Set<Determinant> determinants;

    @Override
    public String toCsv() {
        return null;
    }
}
