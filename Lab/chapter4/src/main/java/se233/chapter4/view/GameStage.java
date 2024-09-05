package se233.chapter4.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import se233.chapter4.Launcher;
import se233.chapter4.model.GameCharacter;
import se233.chapter4.model.Keys;

import java.util.ArrayList;

public class GameStage extends Pane {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 400;
    public static final int GROUND = 300;
    private Image gameStageImage;
    private ArrayList<GameCharacter> gameCharacters;
    private Keys keys;
    public GameStage() {
        keys = new Keys();
        gameCharacters = new ArrayList<>();
        gameStageImage = new Image(Launcher.class.getResourceAsStream("assets/Background.png"));
        ImageView backgroundImg = new ImageView(gameStageImage);
        backgroundImg.setFitWidth(WIDTH);
        backgroundImg.setFitHeight(HEIGHT);
        gameCharacters.add(new GameCharacter(30, 30, 0, 0, KeyCode.A, KeyCode.D, KeyCode.W));
        gameCharacters.add(new GameCharacter(730, 30, 0, 48, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.UP));
        gameCharacters.getLast().setGameCharacter("assets/Rockman2.png", 5, 2, 0, 0,410, 347, false, 67, 64, true);
        gameCharacters.getLast().setxMaxVelocity(15);
        gameCharacters.getLast().setyMaxVelocity(25);
        gameCharacters.getLast().setxAcceleration(3);
        gameCharacters.getLast().setyAcceleration(2);
        this.getChildren().addAll(backgroundImg);
        this.getChildren().addAll(gameCharacters);
    }

    public ArrayList<GameCharacter> getGameCharacters() {
        return gameCharacters;
    }

    public Keys getKeys() {
        return keys;
    }
}
