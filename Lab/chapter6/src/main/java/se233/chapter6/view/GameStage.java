package se233.chapter6.view;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import se233.chapter6.model.Food;
import se233.chapter6.model.Snake;

public class GameStage extends Pane {
    public static final int WIDTH = 30;
    public static final int HEIGHT = 30;
    public static final int TILE_SIZE = 10;
    private Canvas canvas;
    private HBox scoreBox;
    private Label scoreLbl;
    private int score;
    private KeyCode key;

    public GameStage() {
        this.setHeight(TILE_SIZE * HEIGHT);
        this.setWidth(TILE_SIZE * WIDTH);
        score = 0;
        scoreLbl = new Label(("Score: 0"));
        scoreBox = new HBox(scoreLbl);
        scoreBox.setStyle("-fx-background-color: grey");
        canvas = new Canvas(TILE_SIZE * WIDTH, TILE_SIZE * HEIGHT);
        this.getChildren().addAll(scoreBox, canvas);
    }
    public void render(Snake snake, Food food) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLUE);
        snake.getBody().forEach(p -> {
            gc.fillRect(p.getX() * TILE_SIZE, p.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        });
        gc.setFill(Color.RED);
        gc.fillRect(food.getPosition().getX() * TILE_SIZE, food.getPosition().getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
    public void render(Snake snake, Food food, Food specialFood) {
        render(snake, food);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.GREEN);
        gc.fillRect(specialFood.getPosition().getX() * TILE_SIZE, specialFood.getPosition().getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
    }
    public KeyCode getKey() { return key; }
    public void setKey(KeyCode key) { this.key = key; }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        Platform.runLater(() -> scoreLbl.setText("Score: " + score));
    }
}
