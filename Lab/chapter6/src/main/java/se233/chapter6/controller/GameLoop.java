package se233.chapter6.controller;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import se233.chapter6.Launcher;
import se233.chapter6.model.Direction;
import se233.chapter6.model.Food;
import se233.chapter6.model.Snake;
import se233.chapter6.view.GameStage;

public class GameLoop implements Runnable {
    private GameStage gameStage;
    private Snake snake;
    private Food food, specialFood;
    private float interval = 1000.0f / 10;
    private boolean running;
    public GameLoop(GameStage gameStage, Snake snake, Food food) {
        this.snake = snake;
        this.gameStage = gameStage;
        this.food = food;
        running = true;
    }
    public GameLoop(GameStage gameStage, Snake snake, Food food, Food specialFood) {
        this(gameStage, snake, food);
        this.specialFood = specialFood;
    }
    private void keyProcess() {
        KeyCode curKey = gameStage.getKey();
        Direction curDirection = snake.getDirection();
        if (curKey == KeyCode.UP && curDirection != Direction.DOWN)
            snake.setDirection(Direction.UP);
        else if (curKey == KeyCode.DOWN && curDirection != Direction.UP)
            snake.setDirection(Direction.DOWN);
        else if (curKey == KeyCode.LEFT && curDirection != Direction.RIGHT)
            snake.setDirection(Direction.LEFT);
        else if (curKey == KeyCode.RIGHT && curDirection != Direction.LEFT)
            snake.setDirection(Direction.RIGHT);
        snake.move();
    }
    private void checkCollision() {
        if (snake.collided(food)) {
            snake.grow();
            food.respawn();
            gameStage.setScore(gameStage.getScore() + 1);
        }
        if (snake.collided(specialFood)) {
            snake.grow();
            specialFood.respawn();
            gameStage.setScore(gameStage.getScore() + 5);
        }
        if (snake.checkDead()) {
            running = false; }
    }
    private void redraw() { gameStage.render(snake, food, specialFood); }
    private void showGameOver() {
//        Stage gameOverStage = new Stage();
        Popup popup = new Popup();
        Label gameOverLabel = new Label("Game Over");
        Pane pane = new Pane(gameOverLabel);
        gameOverLabel.setStyle("-fx-text-fill: white; -fx-font-size: 30px");
        pane.setStyle("-fx-background-color: black");
        popup.getContent().addAll(pane);
        popup.show(Launcher.stage);
    }
    @Override
    public void run() {
        while (running) {
            keyProcess();
            checkCollision();
            redraw();
            try {
                Thread.sleep((long) interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Platform.runLater(this::showGameOver);
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Platform.runLater(Launcher.stage::close);
    }
}
