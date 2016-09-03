package pl.khuzzuk.wfrpchar.rules;

import pl.khuzzuk.wfrpchar.entities.Nameable;

public enum Dices implements Nameable<String> {
    K4, K6, K8, K10, K12, K20, K100;

    @Override
    public String getName() {
        return name();
    }
}
