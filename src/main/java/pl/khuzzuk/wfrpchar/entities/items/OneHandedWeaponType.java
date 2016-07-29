package pl.khuzzuk.wfrpchar.entities.items;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("5")
public class OneHandedWeaponType extends WhiteWeaponType {
    public OneHandedWeaponType() {
        this.placement = Placement.ONE_HAND;
    }

    @Override
    public String toCsv() {
        getLangToCsv();
        return name + ";" +
                weight + ";" +
                price.getGold() + "\\|" + price.getSilver() + "\\|" + price.getLead() + ";" +
                accessibility + ";" +
                specialFeature + ";" +
                strength + ";" +
                type + ";" +
                placement + ";" +
                getLangToCsv() + ";"

                ;
    }

    @Override
    public String toString() {
        return "OneHandedWeaponType{" +
                "name: " + name + ", weight: " + weight + ", " + price + ", accessibility: " + accessibility +
                ", strength: " + strength + ", " + determinants + ", dices: " + rolls + dices
                + "}";
    }
}
