package se233.chapter4.model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.chapter4.Launcher;
import se233.chapter4.view.GameStage;

public class GameCharacter extends Pane {
    public static final int DEFAULT_WIDTH = 32;
    public static final int DEFAULT_HEIGHT = 64;
    private static final Logger logger = LogManager.getLogger(GameCharacter.class);
    private Image gameCharacterImage;
    private AnimatedSprite imageView;
    private int x, y;
    private KeyCode leftKey, rightKey, upKey;
    int characterWidth = DEFAULT_WIDTH;
    int characterHeight = DEFAULT_HEIGHT;
    int xVelocity = 0;
    int yVelocity = 0;
    int xAcceleration = 1;
    int yAcceleration = 1;
    int xMaxVelocity = 7;
    int yMaxVelocity = 17;
    int turnRight = 1;
    boolean isMoveLeft = false;
    boolean isMoveRight = false;
    boolean isFalling = true;
    boolean canJump = false;
    boolean isJumping = false;
    public GameCharacter(int x, int y, int offsetX, int offsetY, KeyCode leftKey, KeyCode rightKey, KeyCode upKey) {
        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);
        this.gameCharacterImage = new Image(Launcher.class.getResourceAsStream("assets/MarioSheet.png"));
        this.imageView = new AnimatedSprite(gameCharacterImage, 4, 4, 1, offsetX, offsetY, 16, 32);
        imageView.setFitWidth(DEFAULT_WIDTH);
        imageView.setFitHeight(DEFAULT_HEIGHT);
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.getChildren().add(this.imageView);
    }

    public void moveLeft() {
        setScaleX(-turnRight);
        isMoveLeft = true;
        isMoveRight = false;
        if (x <= 0) {
            logger.debug("Collided with left wall");
        }
    }
    public void moveRight() {
        setScaleX(turnRight);
        isMoveLeft = false;
        isMoveRight = true;
        if (x + characterWidth >= GameStage.WIDTH) {
            logger.debug("Collided with right wall");
        }
    }
    public void stop() {
        isMoveLeft = false;
        isMoveRight = false;
        xVelocity = 0;
    }
    public void moveX() {
        setTranslateX(x);
        if (isMoveLeft) {
            xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xVelocity + xAcceleration;
            x -= xVelocity;
        }
        if (isMoveRight) {
            xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xVelocity + xAcceleration;
            x += xVelocity;
        }
    }
    public void moveY() {
        setTranslateY(y);
        if (isFalling) {
            yVelocity = yVelocity >= yMaxVelocity ? yMaxVelocity : yVelocity + yAcceleration;
            y = y + yVelocity;
        } else if (isJumping) {
            yVelocity = yVelocity <= 0 ? 0 : yVelocity - yAcceleration;
            y = y - yVelocity;
        }
    }
    public void jump() {
        if (canJump) {
            yVelocity = yMaxVelocity;
            canJump = false;
            isJumping = true;
            isFalling = false;
        }
    }
    public void checkReachHighest() {
        if (isJumping && yVelocity <= 0) {
            isJumping = false;
            isFalling = true;
            yVelocity = 0;
        }
    }
    public void checkReachGameWall() {
        if (x <= 0) {
            x = 0;
        } else if (x + characterWidth >= GameStage.WIDTH) {
            x = GameStage.WIDTH - characterWidth;
        }
    }
    public void checkReachFloor() {
        if (isFalling && y >= GameStage.GROUND - characterHeight){
            y = GameStage.GROUND - characterHeight;
            isFalling = false;
            canJump = true;
            yVelocity = 0;
        }
    }
    public void repaint() {
        moveX();
        moveY();
    }

    public void trace() {
        logger.info("x:{} y:{} vx:{} vy:{}", x, y, xVelocity, yVelocity);
    }

    // Getters & Setters

    public KeyCode getUpKey() {
        return upKey;
    }

    public KeyCode getRightKey() {
        return rightKey;
    }

    public KeyCode getLeftKey() {
        return leftKey;
    }

    public AnimatedSprite getImageView() {
        return imageView;
    }

    public void setxAcceleration(int xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    public void setyAcceleration(int yAcceleration) {
        this.yAcceleration = yAcceleration;
    }

    public void setxMaxVelocity(int xMaxVelocity) {
        this.xMaxVelocity = xMaxVelocity;
    }

    public void setyMaxVelocity(int yMaxVelocity) {
        this.yMaxVelocity = yMaxVelocity;
    }

    // Methods for character customization
    public void setGameCharacter(String imgpath, int columns, int rows, int offsetX, int offsetY, int width, int height, int resizedWidth, int resizedHeight, boolean turnSide) {
        setGameCharacter(imgpath, offsetX, offsetY, offsetX, offsetY, width, height, true, resizedWidth, resizedHeight, turnSide);
    }

    public void setGameCharacter(String imgpath, int columns, int rows, int offsetX, int offsetY, int width, int height, boolean defaultCharacterSize, int resizedWidth, int resizedHeight, boolean turnSide) {
        this.getChildren().remove(this.imageView);
        this.gameCharacterImage = new Image(Launcher.class.getResourceAsStream(imgpath));
        this.imageView = new AnimatedSprite(gameCharacterImage, columns*rows, columns, rows, offsetX, offsetY, width, height);
        if (!defaultCharacterSize) {
            characterWidth = resizedWidth;
            characterHeight = resizedHeight;
        }
        if (turnSide) {
            imageView.setScaleX(-1);
            turnRight = -1;
        }
        imageView.setFitWidth(characterWidth);
        imageView.setFitHeight(characterHeight);

        this.getChildren().add(this.imageView);
    }

}
