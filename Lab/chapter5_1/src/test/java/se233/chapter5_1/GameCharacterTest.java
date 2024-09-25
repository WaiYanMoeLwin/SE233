package se233.chapter5_1;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import se233.chapter5_1.model.GameCharacter;
import se233.chapter5_1.view.GameStage;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

public class GameCharacterTest {
    private GameCharacter gameCharacter;
    Field xVelocityField, yVelocityField, yAccelerationField, canJumpField, isJumpingField, isFallingField, yMaxVelocityField;
    Field isMoveLeftField, isMoveRightField;

    @BeforeAll
    public static void initJfxRuntime() {
        javafx.application.Platform.startup(() -> {});
    }
    @BeforeEach
    public void setUP() throws NoSuchFieldException {
        gameCharacter = new GameCharacter(0, 30, 30, "assets/Character1.png", 4, 3, 2, 111, 97, KeyCode.A, KeyCode.D, KeyCode.W);
        xVelocityField = gameCharacter.getClass().getDeclaredField("xVelocity");
        yVelocityField = gameCharacter.getClass().getDeclaredField("yVelocity");
        yAccelerationField = gameCharacter.getClass().getDeclaredField("yAcceleration");
        canJumpField = gameCharacter.getClass().getDeclaredField("canJump");
        isJumpingField = gameCharacter.getClass().getDeclaredField("isJumping");
        isFallingField = gameCharacter.getClass().getDeclaredField("isFalling");
        yMaxVelocityField = gameCharacter.getClass().getDeclaredField("yMaxVelocity");
        isMoveLeftField = gameCharacter.getClass().getDeclaredField("isMoveLeft");
        isMoveRightField = gameCharacter.getClass().getDeclaredField("isMoveRight");
        xVelocityField.setAccessible(true);
        yVelocityField.setAccessible(true);
        yAccelerationField.setAccessible(true);
        canJumpField.setAccessible(true);
        isJumpingField.setAccessible(true);
        isFallingField.setAccessible(true);
        yMaxVelocityField.setAccessible(true);
        isMoveLeftField.setAccessible(true);
        isMoveRightField.setAccessible(true);
    }
    @Test
    public void respawn_givenNewGameCharacter_thenCoordinatesAre30_30() {
        gameCharacter.respawn();
        assertEquals(30, gameCharacter.getX(), "Initial X");
        assertEquals(30, gameCharacter.getY(), "Initial Y");
    }
    @Test
    public void respawn_givenNewGameCharacter_thenScoreIs0() {
        gameCharacter.respawn();
        assertEquals(0, gameCharacter.getScore(), "Initial Score");
    }
    @Test
    public void moveX_givenMoveRightOnce_thenXCoordinateIncreasedByXVelocity() throws IllegalAccessException {
        gameCharacter.respawn();
        gameCharacter.moveRight();
        gameCharacter.moveX();
        assertEquals(30 + xVelocityField.getInt(gameCharacter), gameCharacter.getX(), "Move right X");
    }
    @Test
    public void moveY_givenTwoConsecutiveCalls_thenYVelocityIncreases() throws IllegalAccessException {
        gameCharacter.respawn();
        gameCharacter.moveY();
        int yVelocity1 = yVelocityField.getInt(gameCharacter);
        gameCharacter.moveY();
        int yVelocity2 = yVelocityField.getInt(gameCharacter);
        assertTrue(yVelocity2 > yVelocity1, "Velocity is increasing");
    }
    @Test
    public void moveY_givenTwoConsecutiveCalls_thenYAccelerationUnchanged() throws IllegalAccessException {
        gameCharacter.respawn();
        gameCharacter.moveY();
        int yAccelaration1 = yAccelerationField.getInt(gameCharacter);
        gameCharacter.moveY();
        int yAccelaration2 = yAccelerationField.getInt(gameCharacter);
        assertTrue(yAccelaration1 == yAccelaration2, "Acceleration is not change");
    }
    @Test
    public void checkReachGameWall_givenMoveLeftUntilReachedLeftWall_thenXCoordinateIs0() throws IllegalAccessException {
        gameCharacter.respawn();
        while (gameCharacter.getX() >= 0) {
            gameCharacter.moveLeft();
            gameCharacter.moveX();
        }
        gameCharacter.checkReachGameWall();
        assertEquals(0, gameCharacter.getX(), "Reached Left Wall");
    }
    @Test
    public void checkReachGameWall_givenMoveRightUntilReachedRightWall_thenXCoordinateIsStageWidthMinusGameCharacterWidth() throws IllegalAccessException {
        gameCharacter.respawn();
        while (gameCharacter.getX() + gameCharacter.getCharacterWidth() <= GameStage.WIDTH) {
            gameCharacter.moveRight();
            gameCharacter.moveX();
        }
        gameCharacter.checkReachGameWall();
        assertEquals((GameStage.WIDTH - gameCharacter.getCharacterWidth()), gameCharacter.getX(), "Reached Right Wall");
    }
    @Test
    public void jump_givenCharacterCanJump_thenSuccessfullyJumps() throws IllegalAccessException {
        gameCharacter.respawn();
        while (gameCharacter.getY() < GameStage.GROUND) {
            gameCharacter.moveY();
        }
        gameCharacter.checkReachFloor();
        int beforeJumpY = gameCharacter.getY();
        gameCharacter.jump();
        assertEquals(yMaxVelocityField.getInt(gameCharacter), yVelocityField.getInt(gameCharacter), "yVelocity becomes yMaxVelocity");
        assertFalse(canJumpField.getBoolean(gameCharacter), "Cannot jump again");
        assertTrue(isJumpingField.getBoolean(gameCharacter), "Is jumping");
        assertFalse(isFallingField.getBoolean(gameCharacter), "Is not falling");
    }
    @Test
    public void jump_givenCharacterCannotJump_thenDoesNotJump() throws IllegalAccessException {
        gameCharacter.respawn();
        int beforeJumpY = gameCharacter.getY();
        gameCharacter.jump();
        gameCharacter.moveY();
        assertFalse(gameCharacter.getY() < beforeJumpY, "Does not Move Upward");
    }
    @Test
    public void collided_givenCollidedFromLeft_thenCharacterStopsAndNotOverlap() throws IllegalAccessException {
        gameCharacter.respawn();
        GameCharacter gc = Mockito.mock(GameCharacter.class);
        Mockito.when(gc.getX()).thenReturn(30 + gameCharacter.getCharacterWidth());
        Mockito.when(gc.getY()).thenReturn(30);
        gameCharacter.moveRight();
        gameCharacter.moveX();
        boolean collided = true;
        assertTrue(gameCharacter.getX() > gc.getX() - gameCharacter.getCharacterWidth(), "Characters collided");
        collided = gameCharacter.collided(gc);
        assertFalse(collided, "Collided returns false in horizontal collision");
        assertFalse(isMoveLeftField.getBoolean(gameCharacter) || isMoveRightField.getBoolean(gameCharacter), "Character stop moving");
        assertTrue(gameCharacter.getX() <= gc.getX() - gameCharacter.getCharacterWidth(), "Characters do not overlap");
    }
    @Test
    public void collided_givenCollidedFromAbove_thenCharacter2CollapsedAndRespawn() throws IllegalAccessException {
        gameCharacter.respawn();
        GameCharacter gc = Mockito.mock(GameCharacter.class);
        Mockito.when(gc.getX()).thenReturn(30);
        Mockito.when(gc.getY()).thenReturn(30 + gameCharacter.getCharacterHeight());
        gameCharacter.moveY();
        assertTrue(gameCharacter.getY() > gc.getY() - gameCharacter.getCharacterHeight(), "Characters collided");
        boolean collided = gameCharacter.collided(gc);
        assertTrue(collided, "Collided returns true in vertical collision");
        assertTrue(gameCharacter.getY() <= GameStage.GROUND - gameCharacter.getCharacterHeight(), "Character fall to ground or to other character's height");
        Mockito.verify(gc, Mockito.times(1)).collapsed();
        Mockito.verify(gc, Mockito.times(1)).respawn();
    }
}
