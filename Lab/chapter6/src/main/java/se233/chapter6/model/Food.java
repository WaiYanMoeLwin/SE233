package se233.chapter6.model;

import javafx.geometry.Point2D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import se233.chapter6.view.GameStage;

import java.util.Random;

public class Food {
    private Point2D position;
    private Random rn;
    private static final Logger logger = LogManager.getLogger(Food.class);

    public Food(Point2D position) {
        this.rn = new Random();
        this.position = position;
    }
    public Food() {
        this.rn = new Random();
        this.position = new Point2D(rn.nextInt(GameStage.WIDTH), rn.nextInt(GameStage.HEIGHT));
    }
    public void respawn() {
        Point2D prev_position = this.position;
        do {
            this.position = new Point2D(rn.nextInt(GameStage.WIDTH), rn.nextInt(GameStage.HEIGHT));
        } while (prev_position == this.position);
        logger.info("food: x:{} y:{}", this.position.getX(), this.position.getY());
    }
    public Point2D getPosition() {
        return position;
    }
}

