package pl.khuzzuk.wfrpchar.entities.competency;

import lombok.Getter;
import lombok.Setter;
import pl.khuzzuk.wfrpchar.db.DAO;
import pl.khuzzuk.wfrpchar.entities.Documented;
import pl.khuzzuk.wfrpchar.entities.Named;
import pl.khuzzuk.wfrpchar.entities.Persistable;
import pl.khuzzuk.wfrpchar.entities.determinants.DeterminantsType;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ProfessionClass implements Named<String>, Persistable, Documented {
    @Getter
    @Setter
    @Id
    @GeneratedValue
    private long id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String specialFeatures;
    @Getter
    @Setter
    private DeterminantsType primaryDeterminant;
    @Getter
    @Setter
    @OneToMany(fetch = FetchType.EAGER)
    private Set<Skill> skills;

    @Override
    public String toCsv() {
        return name + ";" + specialFeatures + ";" + primaryDeterminant.name() + ";" + Skill.collectionToCsv(skills);
    }

    public static ProfessionClass fromCsv(String[] fields) {
        ProfessionClass professionClass = new ProfessionClass();
        professionClass.setName(fields[0]);
        professionClass.setSpecialFeatures(fields[1]);
        professionClass.setPrimaryDeterminant(DeterminantsType.valueOf(fields[2]));
        return professionClass;
    }

    public static ProfessionClass fromCsv(String[] fields, DAO dao) {
        return updateSkills(fromCsv(fields), fields, dao);
    }

    public static ProfessionClass updateSkills(final ProfessionClass professionClass, String[] fields, DAO dao) {
        if (fields.length >= 4) {
            String[] skillsNames = fields[3].split("\\|");
            Set<Skill> skills = new HashSet<>();
            for (String name : skillsNames) {
                skills.add(dao.getEntity(Skill.class, name));
            }
            professionClass.setSkills(skills);
        }
        return professionClass;
    }
}
