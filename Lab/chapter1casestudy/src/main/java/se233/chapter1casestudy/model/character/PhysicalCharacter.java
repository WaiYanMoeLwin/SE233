package se233.chapter1casestudy.model.character;

import se233.chapter1casestudy.model.DamageType;

public class PhysicalCharacter extends BasedCharacter {
    public PhysicalCharacter(String name, int basedDef, int basedRes, String imagepath) {
        this.name = name;
        this.type = DamageType.physical;
        this.imagepath = imagepath;
        this.fullHp = 50;
        this.basedPow = 30;
        this.basedDef = basedDef;
        this.basedRes = basedRes;
        this.hp = this.fullHp;
        this.power = this.basedPow;
        this.defense = this.basedDef;
        this.resistance = this.basedRes;
    }
}
