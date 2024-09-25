package se233.chapter1.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.chapter1.controller.AllCustomHandler;
import se233.chapter1.Launcher;
import se233.chapter1.model.item.BasedEquipment;


import java.util.ArrayList;

public class InventoryPane extends ScrollPane {
    private static final Logger logger = LogManager.getLogger(InventoryPane.class);
    ArrayList<BasedEquipment> equipmentArray;
    public InventoryPane() {}
    private Pane getDetailsPane() {
        Pane inventoryInfoPane = new HBox(10);
        inventoryInfoPane.setBorder(null);
        inventoryInfoPane.setPadding(new Insets(25, 25, 25, 25));
        if (equipmentArray != null) {
            ImageView[] imageViewList = new ImageView[equipmentArray.size()];
            for (int i = 0; i < equipmentArray.size(); i++) {
                imageViewList[i] = new ImageView();
                imageViewList[i].setImage(new Image(Launcher.class.getResource(equipmentArray.get(i).getImagepath()).toString()));
                int finalI = i;
                imageViewList[i].setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        AllCustomHandler.onDragDetected(mouseEvent, equipmentArray.get(finalI), imageViewList[finalI]);
                    }
                });
                imageViewList[i].setOnDragDone(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        AllCustomHandler.onEquipDone(event);
                        String weaponName = "None", armorName = "None";
                        if (Launcher.getEquippedWeapon() != null) {
                            weaponName = Launcher.getEquippedWeapon().getName();
                        }
                        if (Launcher.getEquippedArmor() != null) {
                            armorName = Launcher.getEquippedArmor().getName();
                        }
                        logger.info("Weapon: " + weaponName + " Armor: " + armorName);
                    }
                });
            }
            inventoryInfoPane.getChildren().addAll(imageViewList);
        }
        return inventoryInfoPane;
    }

    public void drawPane(ArrayList<BasedEquipment> equipmentArray) {
        this.equipmentArray = equipmentArray;
        Pane inventoryInfo = getDetailsPane();
        this.setStyle("-fx-background-color: red");
        this.setContent(inventoryInfo);
    }
}
