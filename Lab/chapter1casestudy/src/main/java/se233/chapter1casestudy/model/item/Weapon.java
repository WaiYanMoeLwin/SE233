package se233.chapter1casestudy.model.item;

import se233.chapter1casestudy.model.DamageType;

public class Weapon extends BasedEquipment {
    private int power;
    private DamageType type;

    public Weapon(String name, int power, DamageType type, String imagepath) {
        this.name = name;
        this.power = power;
        this.type = type;
        this.imagepath = imagepath;
    }

    public int getPower() { return power; }
    public DamageType getType() { return type; }

}
