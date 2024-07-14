package se233.chapter1casestudy.model.character;

import se233.chapter1casestudy.model.DamageType;
import se233.chapter1casestudy.model.item.Armor;
import se233.chapter1casestudy.model.item.Weapon;

public class BasedCharacter {
    protected String name, imagepath;
    protected DamageType type;
    protected int fullHp, basedPow, basedDef, basedRes;
    protected int hp, power, defense, resistance;
    protected Weapon weapon;
    protected Armor armor;

    public String getName() {
        return name;
    }

    public String getImagepath() {
        return imagepath;
    }

    public DamageType getType() {
        return type;
    }

    public int getFullHp() {
        return fullHp;
    }

    public int getHp() {
        return hp;
    }

    public int getPower() {
        return power;
    }

    public int getDefense() {
        return defense;
    }

    public int getResistance() {
        return resistance;
    }

    public void equipWeapon(Weapon weapon) {
        if (weapon != null) {
            this.weapon = weapon;
            this.power = basedPow + weapon.getPower();
        }
    }

    public void equipArmor(Armor armor) {
        if (armor != null) {
            this.armor = armor;
            this.defense = basedDef + armor.getDefense();
            this.resistance = basedRes + armor.getResistance();
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
