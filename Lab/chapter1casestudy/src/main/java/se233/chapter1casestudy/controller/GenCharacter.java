package se233.chapter1casestudy.controller;

import se233.chapter1casestudy.model.character.BasedCharacter;
import se233.chapter1casestudy.model.character.MagicalCharacter;
import se233.chapter1casestudy.model.character.PhysicalCharacter;

import java.util.Random;

public class GenCharacter {
    public static BasedCharacter setUpCharacter() {
        BasedCharacter character;
        Random rand = new Random();
        int type = rand.nextInt(2) + 1;
        int basedDef = rand.nextInt(50);
        int basedRes = rand.nextInt(50);
        if (type == 1) {
            character = new PhysicalCharacter("PhysicalChar1", basedDef, basedRes, "assets/knight.png");
        } else {
            character = new MagicalCharacter("MagicalChar1", basedDef, basedRes, "assets/wizard.png");
        }
        return character;
    }
}
