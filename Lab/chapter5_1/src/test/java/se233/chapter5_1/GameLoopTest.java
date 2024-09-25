package se233.chapter5_1;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se233.chapter5_1.controller.GameLoop;
import se233.chapter5_1.model.AnimatedSprite;
import se233.chapter5_1.model.GameCharacter;
import se233.chapter5_1.model.Keys;
import se233.chapter5_1.view.GameStage;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class GameLoopTest {
    List<GameCharacter> gameCharacterList;
    GameStage gameStage;
    GameLoop gameLoop;
    GameCharacter gc;
    AnimatedSprite imageView;
    Keys keys;
    Method updateMethod;

    @BeforeAll
    public static void initJfxRuntime() {
        javafx.application.Platform.startup(() -> {});
    }
    @BeforeEach
    public void setup() throws NoSuchMethodException {
        gameStage = Mockito.mock(GameStage.class);
        keys = new Keys();
        Mockito.when(gameStage.getKeys()).thenReturn(keys);
        gameCharacterList = new ArrayList<>();
        gc = Mockito.mock(GameCharacter.class);
        Mockito.when(gc.getLeftKey()).thenReturn(KeyCode.A);
        Mockito.when(gc.getRightKey()).thenReturn(KeyCode.D);
        Mockito.when(gc.getUpKey()).thenReturn(KeyCode.W);
        imageView = Mockito.mock(AnimatedSprite.class);
        Mockito.when(gc.getImageView()).thenReturn(imageView);
        gameCharacterList.add(gc);
        gameLoop = new GameLoop(gameStage);
        updateMethod = GameLoop.class.getDeclaredMethod("update", List.class);
        updateMethod.setAccessible(true);
    }
    @Test
    public void update_givenPressedLeftKey_shouldCallTickAndMoveLeft() throws Exception {
        keys.add(KeyCode.A);
        updateMethod.invoke(gameLoop, gameCharacterList);
        Mockito.verify(imageView, Mockito.times(1)).tick();
        Mockito.verify(gc, Mockito.times(1)).moveLeft();
    }
    @Test
    public void update_givenPressedLeftKeyAndRightKeySquentially_shouldCallStop() throws Exception {
        keys.add(KeyCode.A);
        keys.add(KeyCode.D);
        updateMethod.invoke(gameLoop, gameCharacterList);
        Mockito.verify(gc, Mockito.times(1)).stop();
    }
}
