package pl.khuzzuk.wfrpchar.entities.competency;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.SkillContainer;
import pl.khuzzuk.wfrpchar.entities.determinants.Determinant;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantFactory;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
public class Profession implements Persistable, Documented, SkillContainer {
    @Id
    @GeneratedValue
    @Getter
    @Setter
    private long id;
    @Getter
    @Setter
    @NaturalId
    private String name;
    @Getter
    @Setter
    private String specialFeatures;
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Skill> skills;
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Profession> nextProfessions;
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "DET_PROF_MAP",
            joinColumns = {@JoinColumn(name = "PROF_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DET_ID")})
    private Set<Determinant> determinants;
    @Setter
    @Getter
    @ManyToOne
    @JsonIdentityReference(alwaysAsId = true)
    private ProfessionClass professionClass;

    @Override
    public String toCsv() {
        List<String> fields = new ArrayList<>();
        fields.add(name);
        fields.add(specialFeatures);
        fields.add(skills.stream().map(Skill::getName).collect(Collectors.joining("|")));
        fields.add(nextProfessions.stream().map(Profession::getName).collect(Collectors.joining("|")));
        fields.add(Determinant.determinantsToCsv(determinants));
        return fields.stream().collect(Collectors.joining(";"));
    }

    public static Profession build(String[] fields,
                                   Set<Skill> skills,
                                   Set<Profession> professions) {
        Profession profession = new Profession();
        profession.setName(fields[0]);
        profession.setSpecialFeatures(fields[1]);
        profession.setSkills(skills != null ? skills : new HashSet<>());
        profession.setNextProfessions(professions != null ? professions : new HashSet<>());
        profession.setDeterminants(DeterminantFactory.createDeterminants(fields[4]));
        return profession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Profession that = (Profession) o;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
