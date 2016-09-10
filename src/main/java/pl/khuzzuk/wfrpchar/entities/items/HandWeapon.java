package pl.khuzzuk.wfrpchar.entities.items;

import pl.khuzzuk.wfrpchar.rules.Dices;

public interface HandWeapon extends Weapon {
    Dices getDices();

    int getRolls();

    int getBattleMod();

    int getInitiativeMod();

    int getParryMod();

    int getOpponentParryMod();
}
