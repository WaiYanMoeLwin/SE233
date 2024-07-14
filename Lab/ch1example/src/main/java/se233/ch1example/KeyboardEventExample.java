package se233.ch1example;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class KeyboardEventExample extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Keyboard Example");
        Group root = new Group();
        Scene scene = new Scene(root, 500, 300);
        scene.setFill(Color.WHITE);
        FigureGroup circle = new FigureGroup(80, 50, 10, Color.RED, "*");
        root.getChildren().add(circle.getFigure());

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.UP) {
                    circle.setPosition(0, -5);
                } else if (keyEvent.getCode() == KeyCode.DOWN) {
                    circle.setPosition(0, 5);
                } else if (keyEvent.getCode() == KeyCode.LEFT) {
                    circle.setPosition(-5, 0);
                } else if (keyEvent.getCode() == KeyCode.RIGHT) {
                    circle.setPosition(5, 0);
                }
            }
        });

        stage.setScene(scene);
        stage.show();
    }
}
