package se233.chapter1casestudy.controller;

import se233.chapter1casestudy.model.DamageType;
import se233.chapter1casestudy.model.character.BasedCharacter;
import se233.chapter1casestudy.model.item.Armor;
import se233.chapter1casestudy.model.item.BasedEquipment;
import se233.chapter1casestudy.model.item.Weapon;

import java.util.ArrayList;

public class GenItemList {
    public static ArrayList<BasedEquipment> setUpItemList() {
        ArrayList<BasedEquipment> itemList = new ArrayList<>();
        itemList.add(new Weapon("Sword", 10, DamageType.physical, "assets/sword.png"));
        itemList.add(new Weapon("Gun", 20, DamageType.physical, "assets/gun.png"));
        itemList.add(new Weapon("Staff", 30, DamageType.magical, "assets/staff.png"));
        itemList.add(new Armor("Armor", 50, 0, "assets/armor.png"));
        itemList.add(new Armor("Shirt", 0, 50, "assets/shirt.png"));
        return itemList;
    }
}
