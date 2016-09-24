package pl.khuzzuk.wfrpchar.entities.items;

import pl.khuzzuk.wfrpchar.entities.LoadingTimes;

public interface RangedWeapon extends Weapon {
    int getShortRange();

    int getEffectiveRange();

    int getMaximumRange();

    LoadingTimes getReloadTime();
}
