package se233.chapter1casestudy.model.item;

public class Armor extends BasedEquipment {
    private int defense, resistance;
    public Armor(String name, int defense, int resistance, String imagepath) {
        this.name = name;
        this.defense = defense;
        this.resistance = resistance;
        this.imagepath = imagepath;
    }

    public int getDefense() { return defense; }
    public int getResistance() { return resistance; }
}
