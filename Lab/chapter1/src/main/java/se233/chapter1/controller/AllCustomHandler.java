package se233.chapter1.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import se233.chapter1.Launcher;

import se233.chapter1.model.character.BasedCharacter;
import se233.chapter1.model.item.Armor;
import se233.chapter1.model.item.BasedEquipment;
import se233.chapter1.model.item.Weapon;
import se233.chapter1.view.CharacterPane;

import java.util.ArrayList;

public class AllCustomHandler {
    public static class GenCharacterHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Launcher.setMainCharacter(GenCharacter.setUpCharacter());
            Launcher.refreshPane();
        }
    }

    public static void onDragDetected(MouseEvent event, BasedEquipment equipment, ImageView imgView) {
        Dragboard db = imgView.startDragAndDrop(TransferMode.ANY);
        db.setDragView(imgView.getImage());
        ClipboardContent content = new ClipboardContent();
        content.put(BasedEquipment.DATA_FORMAT, equipment);
        db.setContent(content);
        event.consume();
    }

    public static void onDragOver(DragEvent event, String type) {
        Dragboard db = event.getDragboard();
        BasedEquipment retrievedEquipment = (BasedEquipment) db.getContent(BasedEquipment.DATA_FORMAT);
        BasedCharacter character = Launcher.getMainCharacter();
        if (db.hasContent(BasedEquipment.DATA_FORMAT) && retrievedEquipment.getClass().getSimpleName().equals(type)) {
            if (character.getClass().getSimpleName().equals("BattleMageCharacter")) {
                if (type.equals("Weapon"))
                    event.acceptTransferModes(TransferMode.MOVE);
            } else if (type.equals("Weapon") && character.getType() == ((Weapon) retrievedEquipment).getDamageType()) {
                event.acceptTransferModes(TransferMode.MOVE);
            } else if (type.equals("Armor")) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        }
    }

    public static void onDragDropped(DragEvent event, Label lbl, StackPane imgGroup) {
        boolean dragCompleted = false;
        Dragboard db = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        if (db.hasContent(BasedEquipment.DATA_FORMAT)) {
            BasedEquipment retrievedEquibment = (BasedEquipment) db.getContent(BasedEquipment.DATA_FORMAT);
            BasedCharacter character = Launcher.getMainCharacter();
            if (retrievedEquibment.getClass().getSimpleName().equals("Weapon")) {
                if (Launcher.getEquippedWeapon() != null)
                    allEquipments.add(Launcher.getEquippedWeapon());
                Launcher.setEquippedWeapon((Weapon) retrievedEquibment);
                character.equipWeapon((Weapon) retrievedEquibment);
            } else {
                if (Launcher.getEquippedArmor() != null)
                    allEquipments.add(Launcher.getEquippedArmor());
                Launcher.setEquippedArmor((Armor) retrievedEquibment);
                character.equipArmor((Armor) retrievedEquibment);
            }
            Launcher.setMainCharacter(character);
            Launcher.setAllEquipments(allEquipments);
            Launcher.refreshPane();
            ImageView imgView = new ImageView();
            if (imgGroup.getChildren().size() != 1) {
                imgGroup.getChildren().remove(1);
                Launcher.refreshPane();
            }
            lbl.setText(retrievedEquibment.getClass().getSimpleName() + ": " + retrievedEquibment.getName());
            imgView.setImage(new Image(Launcher.class.getResource(retrievedEquibment.getImagepath()).toString()));
            imgGroup.getChildren().add(imgView);
            dragCompleted = true;
        }
        event.setDropCompleted(dragCompleted);
    }

    public static void onEquipDone(DragEvent event) {
        Dragboard db = event.getDragboard();
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        BasedEquipment retrievedEquipment = (BasedEquipment) db.getContent(BasedEquipment.DATA_FORMAT);
        String retrievedEquipmentName = retrievedEquipment.getName();
        int pos = -1;
        for (int i = 0; i < allEquipments.size(); i++) {
            if (retrievedEquipmentName.equals(allEquipments.get(i).getName())) {
                if ((Launcher.getEquippedWeapon() != null && retrievedEquipmentName.equals(Launcher.getEquippedWeapon().getName())) || (
                        Launcher.getEquippedArmor() != null && retrievedEquipmentName.equals(Launcher.getEquippedArmor().getName())
                )) {
                    pos = i;
                }
            }
        }

        if (pos != -1) {
            allEquipments.remove(pos);
        }
        Launcher.setAllEquipments(allEquipments);
        Launcher.refreshPane();
    }

    public static void unequipAll() {
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        BasedCharacter character = Launcher.getMainCharacter();
        if (Launcher.getEquippedWeapon() != null) {
            allEquipments.add(Launcher.getEquippedWeapon());
            Launcher.setEquippedWeapon(null);
            character.equipWeapon(null);
        }
        if (Launcher.getEquippedArmor() != null) {
            allEquipments.add(Launcher.getEquippedArmor());
            Launcher.setEquippedArmor(null);
            character.equipArmor(null);
        }
        Launcher.setAllEquipments(allEquipments);
        Launcher.setMainCharacter(character);
    }
}
