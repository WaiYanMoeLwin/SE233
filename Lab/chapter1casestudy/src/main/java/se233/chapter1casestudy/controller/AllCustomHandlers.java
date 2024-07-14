package se233.chapter1casestudy.controller;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.StackPane;
import se233.chapter1casestudy.Launcher;
import se233.chapter1casestudy.model.character.BasedCharacter;
import se233.chapter1casestudy.model.item.Armor;
import se233.chapter1casestudy.model.item.BasedEquipment;
import se233.chapter1casestudy.model.item.Weapon;

import java.util.ArrayList;

public class AllCustomHandlers {
    public static class GenCharacterHandler implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            Launcher.setMainCharacter(GenCharacter.setUpCharacter());
            Launcher.refreshPane();
        }
    }

    public static void onDragDetected(MouseEvent event, BasedEquipment equipment, ImageView imageView) {
        Dragboard db = imageView.startDragAndDrop(TransferMode.ANY);
        db.setDragView(imageView.getImage());
        ClipboardContent content = new ClipboardContent();
        content.put(BasedEquipment.DATA_FORMAT, equipment);
        db.setContent(content);
        event.consume();
    }

    public static void onDragOver(DragEvent event, String type) {
        Dragboard db = event.getDragboard();
        if (db.hasContent(BasedEquipment.DATA_FORMAT)) {
            BasedEquipment retrievedEquipment = (BasedEquipment) db.getContent(BasedEquipment.DATA_FORMAT);
            if (retrievedEquipment.getClass().getSimpleName().equals(type)) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
        }
    }

    public static void onDragDropped(DragEvent event, StackPane imgGroup, Label lbl) {
        boolean dragComplete = false;
        Dragboard db = event.getDragboard();
        ImageView imageView = new ImageView();
        if (db.hasContent(BasedEquipment.DATA_FORMAT)) {
            BasedEquipment retrievedEquipment = (BasedEquipment) db.getContent(BasedEquipment.DATA_FORMAT);
            ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
            BasedCharacter character = Launcher.getMainCharacter();
            if (retrievedEquipment.getClass().getSimpleName().equals("Weapon")) {
                if (Launcher.getEquippedWeapon() != null)
                    allEquipments.add(Launcher.getEquippedWeapon());
                Launcher.setEquippedWeapon((Weapon) retrievedEquipment);
                character.equipWeapon((Weapon) retrievedEquipment);
            } else {
                if (Launcher.getEquippedArmor() != null)
                    allEquipments.add(Launcher.getEquippedArmor());
                Launcher.setEquippedArmor((Armor) retrievedEquipment);
                character.equipArmor((Armor) retrievedEquipment);
            }
            Launcher.setMainCharacter(character);
            Launcher.setAllEquipments(allEquipments);
            Launcher.refreshPane();

            if (imgGroup.getChildren().size() != 1) {
                imgGroup.getChildren().remove(1);
                Launcher.refreshPane();
            }

            lbl.setText(retrievedEquipment.getClass().getSimpleName() + ": " + retrievedEquipment.getName());
            imgGroup.getChildren().add(imageView);
            event.setDropCompleted(dragComplete);
        }
    }

    public static void onDragDone(DragEvent event) {
        Dragboard db = event.getDragboard();
        BasedEquipment retrievedEquipment = (BasedEquipment) db.getContent(BasedEquipment.DATA_FORMAT);
        ArrayList<BasedEquipment> allEquipments = Launcher.getAllEquipments();
        int pos = -1;
        for (int i = 0; i < allEquipments.size(); i++) {
            if (allEquipments.get(i).getName().equals(retrievedEquipment.getName())) {
                pos = i;
            }
        }
        if (pos != -1) {
            allEquipments.remove(pos);
        }
        Launcher.setAllEquipments(allEquipments);
        Launcher.refreshPane();
    }
}
