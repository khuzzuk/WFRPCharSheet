package pl.khuzzuk.wfrpchar.entities.items;

import pl.khuzzuk.wfrpchar.rules.Dices;

public interface HandWeapon extends Weapon {
    Dices getDices();

    void setDices(Dices dices);

    int getRolls();

    void setRolls(int rolls);

    int getBattleMod();

    int getInitiativeMod();

    int getParryMod();

    int getOpponentParryMod();
}
