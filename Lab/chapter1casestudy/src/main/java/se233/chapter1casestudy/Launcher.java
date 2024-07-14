package se233.chapter1casestudy;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import se233.chapter1casestudy.controller.GenCharacter;
import se233.chapter1casestudy.controller.GenItemList;
import se233.chapter1casestudy.model.character.BasedCharacter;
import se233.chapter1casestudy.model.item.Armor;
import se233.chapter1casestudy.model.item.BasedEquipment;
import se233.chapter1casestudy.model.item.Weapon;
import se233.chapter1casestudy.view.CharacterPane;
import se233.chapter1casestudy.view.EquipPane;
import se233.chapter1casestudy.view.InventoryPane;

import java.util.ArrayList;

public class Launcher extends Application {
    private static BasedCharacter mainCharacter = null;
    private static Weapon equippedWeapon = null;
    private static Armor equippedArmor = null;
    private static ArrayList<BasedEquipment> allEquipments = null;
    private static InventoryPane inventoryPane = null;
    private static EquipPane equipPane = null;
    private static CharacterPane characterPane = null;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chapter 1");
        primaryStage.setResizable(false);
        primaryStage.show();
        allEquipments = GenItemList.setUpItemList();
        mainCharacter = GenCharacter.setUpCharacter();
        Scene scene = new Scene(getMainPane());
        primaryStage.setScene(scene);
    }

    private Pane getMainPane() {
        BorderPane mainPane = new BorderPane();
        inventoryPane = new InventoryPane();
        equipPane = new EquipPane();
        characterPane = new CharacterPane();
        refreshPane();
        mainPane.setCenter(characterPane);
        mainPane.setBottom(inventoryPane);
        mainPane.setLeft(equipPane);
        return mainPane;
    }

    public static void refreshPane() {
        inventoryPane.drawPane(allEquipments);
        equipPane.drawPane(equippedWeapon, equippedArmor);
        characterPane.drawPane(mainCharacter);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static BasedCharacter getMainCharacter() {
        return mainCharacter;
    }

    public static void setMainCharacter(BasedCharacter mainCharacter) {
        Launcher.mainCharacter = mainCharacter;
    }

    public static ArrayList<BasedEquipment> getAllEquipments() {
        return allEquipments;
    }

    public static void setAllEquipments(ArrayList<BasedEquipment> allEquipments) {
        Launcher.allEquipments = allEquipments;
    }

    public static Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public static void setEquippedWeapon(Weapon equippedWeapon) {
        Launcher.equippedWeapon = equippedWeapon;
    }

    public static Armor getEquippedArmor() {
        return equippedArmor;
    }

    public static void setEquippedArmor(Armor equippedArmor) {
        Launcher.equippedArmor = equippedArmor;
    }
}
