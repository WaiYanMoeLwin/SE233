package se233.chapter4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se233.chapter4.controller.DrawingLoop;
import se233.chapter4.controller.GameLoop;
import se233.chapter4.view.GameStage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) {
        GameStage gameStage = new GameStage();
        GameLoop gameLoop = new GameLoop(gameStage);
        DrawingLoop drawingLoop = new DrawingLoop(gameStage);
        Scene scene = new Scene(gameStage, GameStage.WIDTH, GameStage.HEIGHT);
        scene.setOnKeyPressed(e -> gameStage.getKeys().add(e.getCode()));
        scene.setOnKeyReleased(e -> gameStage.getKeys().remove(e.getCode()));
        stage.setTitle("Mario");
        stage.setScene(scene);
        stage.show();
        Thread gameThread = new Thread(gameLoop);
        Thread drawingThread = new Thread(drawingLoop);
        gameThread.setDaemon(true);
        drawingThread.setDaemon(true);
        gameThread.start();
        drawingThread.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
