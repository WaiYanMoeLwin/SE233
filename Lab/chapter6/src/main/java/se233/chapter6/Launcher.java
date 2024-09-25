package se233.chapter6;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.stage.Stage;
import se233.chapter6.controller.GameLoop;
import se233.chapter6.model.Food;
import se233.chapter6.model.Snake;
import se233.chapter6.view.GameStage;

public class Launcher extends Application {
    public static Stage stage;
    public static void main(String[] args) { launch(args); }
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        GameStage gameStage = new GameStage();
        Snake snake = new Snake(new Point2D(GameStage.WIDTH/2, GameStage.HEIGHT/2));
        Food food = new Food();
        Food specialFood = new Food();
        GameLoop gameLoop = new GameLoop(gameStage, snake, food, specialFood);
        Scene scene = new Scene(gameStage, GameStage.WIDTH * GameStage.TILE_SIZE, GameStage.HEIGHT * GameStage.TILE_SIZE);
        scene.setOnKeyPressed(event -> gameStage.setKey(event.getCode()));
        scene.setOnKeyReleased(event -> gameStage.setKey(null));
        stage.setTitle("Snake Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        Thread game = new Thread(gameLoop);
        game.start();
    }
}
