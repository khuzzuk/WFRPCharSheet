package pl.khuzzuk.wfrpchar.rules;

import org.apache.commons.collections4.SetUtils;
import pl.khuzzuk.wfrpchar.entities.Named;

import java.util.EnumSet;
import java.util.Set;

public enum Dices implements Named<String> {
    K4, K6, K8, K10, K12, K20, K100;

    public static final Set<Dices> SET = SetUtils.unmodifiableSet(EnumSet.allOf(Dices.class));
    @Override
    public String getName() {
        return name();
    }
}
