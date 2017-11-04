package pl.khuzzuk.wfrpchar.entities;

import pl.khuzzuk.wfrpchar.entities.competency.Skill;

import java.util.Set;

public interface SkillContainer extends Featured {
    Set<Skill> getSkills();

    void setSkills(Set<Skill> skills);
}
