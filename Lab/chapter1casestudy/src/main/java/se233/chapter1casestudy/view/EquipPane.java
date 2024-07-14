package se233.chapter1casestudy.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import se233.chapter1casestudy.Launcher;
import se233.chapter1casestudy.controller.AllCustomHandlers;
import se233.chapter1casestudy.model.item.Armor;
import se233.chapter1casestudy.model.item.Weapon;


public class EquipPane extends ScrollPane {
    private Weapon equippedWeapon;
    private Armor equippedArmor;
    public EquipPane() {}
    private Pane getDetailsPane() {
        Pane equipInfoPane = new VBox(10);
        ((VBox) equipInfoPane).setAlignment(Pos.CENTER);
        equipInfoPane.setBorder(null);
        equipInfoPane.setPadding(new Insets(25, 25, 25, 25));
        StackPane weaponImgGroup = new StackPane();
        StackPane armorImgGroup = new StackPane();
        ImageView weaponImgView = new ImageView();
        ImageView armorImgView = new ImageView();
        ImageView bg1 = new ImageView();
        ImageView bg2 = new ImageView();
        bg1.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        bg2.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        Label weaponLbl = new Label();
        Label armorLbl = new Label();
        weaponImgGroup.getChildren().addAll(bg1, weaponImgView);
        armorImgGroup.getChildren().addAll(bg2, armorImgView);
        if (equippedWeapon != null) {
            weaponLbl.setText("Weapon: " + equippedWeapon.getName());
            weaponImgView.setImage(new Image(Launcher.class.getResource(equippedWeapon.getImagepath()).toString()));

        } else {
            weaponLbl.setText("Weapon: ");
            weaponImgView.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        }

        if (equippedArmor != null) {
            armorLbl.setText("Armor: " + equippedArmor.getName());
            armorImgView.setImage(new Image(Launcher.class.getResource(equippedArmor.getImagepath()).toString()));
        } else {
            armorLbl.setText("Armor: ");
            armorImgView.setImage(new Image(Launcher.class.getResource("assets/blank.png").toString()));
        }

        weaponImgGroup.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                AllCustomHandlers.onDragOver(event, "Weapon");
            }
        });

        armorImgGroup.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                AllCustomHandlers.onDragOver(event, "Armor");
            }
        });

        weaponImgGroup.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                AllCustomHandlers.onDragDropped(event, weaponImgGroup, weaponLbl);
            }
        });

        armorImgGroup.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                AllCustomHandlers.onDragDropped(event, armorImgGroup, armorLbl);
            }
        });

        equipInfoPane.getChildren().addAll(weaponLbl, weaponImgGroup, armorLbl, armorImgGroup);
        return equipInfoPane;
    }

    public void drawPane(Weapon equippedWeapon, Armor equippedArmor) {
        this.equippedWeapon = equippedWeapon;
        this.equippedArmor = equippedArmor;
        Pane equipInfoPane = getDetailsPane();
        this.setStyle("-fx-background-color: red;");
        this.setContent(equipInfoPane);
    }
}
