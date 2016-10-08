package pl.khuzzuk.wfrpchar.entities.competency;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import pl.khuzzuk.wfrpchar.db.DAO;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Persistable;
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
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "DET_PROF_MAP",
            joinColumns = {@JoinColumn(name = "PROF_ID")},
            inverseJoinColumns = {@JoinColumn(name = "DET_ID")})
    private Set<Determinant> determinants;

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
        DeterminantFactory factory = new DeterminantFactory();
        profession.setDeterminants(factory.createDeterminants(fields[4]));
        return profession;
    }

    public static Profession build(String[] fields, DAO dao) {
        return build(fields, getSkills(fields, dao), getProfessions(fields, dao));
    }

    public static Profession update(final Profession profession, String[] fields, DAO dao) {
        profession.setSkills(getSkills(fields, dao));
        profession.setNextProfessions(getProfessions(fields, dao));
        return profession;
    }

    private static Set<Skill> getSkills(String[] fields, DAO dao) {
        Set<Skill> skills = new HashSet<>();
        for (String name : fields[2].split("|")) {
            skills.add(dao.getEntity(Skill.class, name));
        }
        return skills;
    }

    private static Set<Profession> getProfessions(String[] fields, DAO dao) {
        Set<Profession> professions = new HashSet<>();
        for (String name : fields[3].split("|")) {
            professions.add(dao.getEntity(Profession.class, name));
        }
        return professions;
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
}
