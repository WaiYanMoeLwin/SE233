package se233.chapter1casestudy.model.item;

import javafx.scene.input.DataFormat;

import java.io.Serializable;

public class BasedEquipment implements Serializable {
    public static final DataFormat DATA_FORMAT = new DataFormat("src.main.java.se233.chapter1casestudy.model.item.BasedEquipment");
    protected String name, imagepath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    @Override
    public String toString() {
        return name;
    }
}
