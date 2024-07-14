package se233.ch1example;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.*;

public class DragListModel {
    ListView<String> lV;
    public DragListModel() {
        lV = new ListView<>();
        lV.setPrefSize(200, 200);
        lV.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard db = lV.startDragAndDrop(TransferMode.MOVE);
                String selectedItems = lV.getSelectionModel().getSelectedItem();
                ClipboardContent content = new ClipboardContent();
                content.putString(selectedItems);
                db.setContent(content);
            }
        });

        lV.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (event.getGestureSource() != lV && db.hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
            }
        });

        lV.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                boolean dragCompleted = false;
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    String list = db.getString();
                    lV.getItems().add(list);
                    dragCompleted = true;
                }
                event.setDropCompleted(dragCompleted);
            }
        });

        lV.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                lV.getItems().remove(lV.getSelectionModel().getSelectedItem());
            }
        });
    }
}
