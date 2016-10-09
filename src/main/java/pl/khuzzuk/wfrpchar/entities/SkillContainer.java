package pl.khuzzuk.wfrpchar.entities;

import pl.khuzzuk.wfrpchar.db.DAO;
import pl.khuzzuk.wfrpchar.entities.competency.Skill;

import java.util.HashSet;
import java.util.Set;

public interface SkillContainer {
    Set<Skill> getSkills();

    void setSkills(Set<Skill> skills);

    static <T extends SkillContainer> T updateSkills(final T skillContainer, String[] fields, DAO dao) {
        if (fields.length >= 4) {
            String[] skillsNames = fields[3].split("\\|");
            Set<Skill> skills = new HashSet<>();
            for (String name : skillsNames) {
                skills.add(dao.getEntity(Skill.class, name));
            }
            skillContainer.setSkills(skills);
        }
        return skillContainer;
    }
}
