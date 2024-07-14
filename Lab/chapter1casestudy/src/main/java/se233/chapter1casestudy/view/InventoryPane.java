package se233.chapter1casestudy.view;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import se233.chapter1casestudy.Launcher;
import se233.chapter1casestudy.controller.AllCustomHandlers;
import se233.chapter1casestudy.model.item.BasedEquipment;

import java.util.ArrayList;

public class InventoryPane extends ScrollPane {
    ArrayList<BasedEquipment> itemList;
    public InventoryPane() {}
    private Pane getDetailsPane() {
        Pane inventoryInfoPane = new HBox(10);
        inventoryInfoPane.setBorder(null);
        inventoryInfoPane.setPadding(new Insets(25, 25, 25, 25));
        ImageView[] imageList = new ImageView[itemList.size()];
        for (int i = 0; i < itemList.size(); i++) {
            imageList[i] = new ImageView();
            imageList[i].setImage(new Image(Launcher.class.getResource(itemList.get(i).getImagepath()).toString()));
            int finalI = i;
            imageList[i].setOnDragDetected(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    AllCustomHandlers.onDragDetected(event, itemList.get(finalI), imageList[finalI]);
                }
            });

            imageList[i].setOnDragDone(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    AllCustomHandlers.onDragDone(event);
                }
            });
        }
        inventoryInfoPane.getChildren().addAll(imageList);
        return inventoryInfoPane;
    }

    public void drawPane(ArrayList<BasedEquipment> itemList) {
        this.itemList = itemList;
        Pane inventoryInfoPane = getDetailsPane();
        this.setStyle("-fx-background-color: red;");
        this.setContent(inventoryInfoPane);
    }
}
