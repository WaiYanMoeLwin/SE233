package se233.ch1example;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class DragAndDrop extends Application {
    private double mouseX, mouseY;
    private boolean isEntered;
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Drag and Drop");
        Group root = new Group();
        Scene scene = new Scene(root, 500, 200);
        FigureGroup source = new FigureGroup(80, 50, 40, Color.CORAL, "SOURCE");
        FigureGroup target = new FigureGroup(320, 50, 50, Color.BLUEVIOLET, "DRAG TARGET");
        source.getFigure().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                mouseX = event.getScreenX();
                mouseY = event.getScreenY();
            }
        });

        source.getFigure().setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                source.getFigure().setMouseTransparent(true);
                source.getFigure().toFront();
                source.getFigure().startFullDrag();
            }
        });

        source.getFigure().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double deltaX = event.getScreenX() - mouseX;
                double deltaY = event.getScreenY() - mouseY;
                source.setPosition(deltaX, deltaY);
                mouseX = event.getScreenX();
                mouseY = event.getScreenY();
            }
        });

        source.getFigure().setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!isEntered) {
                    source.setBackOrigin();
                    source.getFigure().setMouseTransparent(false);
                } else {
                    target.getFigure().getChildren().add(source.getFigure());
                }
            }
        });

        target.getFigure().setOnMouseDragEntered(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent mouseDragEvent) {
                isEntered = true;
            }
        });

        target.getFigure().setOnMouseDragExited(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent mouseDragEvent) {
                isEntered = false;
            }
        });

        root.getChildren().addAll(source.getFigure(), target.getFigure());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
