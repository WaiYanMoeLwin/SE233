package se233.chapter4.controller;

import javafx.application.Platform;
import se233.chapter4.model.GameCharacter;
import se233.chapter4.view.GameStage;

public class DrawingLoop implements Runnable {
    private GameStage gameStage;
    private int frameRate;
    private float interval;
    private boolean running;
    public DrawingLoop(GameStage gameStage) {
        this.gameStage = gameStage;
        this.frameRate = 60;
        this.interval = 1000.0f / frameRate;
        this.running = true;
    }
    private void checkDrawCollision(GameCharacter gameCharacter) {
        gameCharacter.checkReachHighest();
        gameCharacter.checkReachGameWall();
        gameCharacter.checkReachFloor();
    }
    private void paint(GameCharacter gameCharacter) {
        Platform.runLater(gameCharacter::repaint);
    }

    @Override
    public void run() {
        while (running) {
            long time = System.currentTimeMillis();
            for (GameCharacter gameCharacter : gameStage.getGameCharacters()) {
                checkDrawCollision(gameCharacter);
                paint(gameCharacter);
            }
            time = System.currentTimeMillis() - time;
            if (time < interval) {
                try {
                    Thread.sleep((long) (interval - time));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Thread.sleep((long) (interval - (interval % time)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
