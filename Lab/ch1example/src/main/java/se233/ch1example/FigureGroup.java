package se233.ch1example;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class FigureGroup {
    private StackPane figure;
    private int originX, originY;
    public FigureGroup(int x, int y, int radius, Color color, String text) {
        Circle circle = new Circle(radius, color);
        Text txt = new Text(text);
        figure = new StackPane(circle, txt);
        figure.setLayoutX(x);
        figure.setLayoutY(y);
        originX = x;
        originY = y;
    }

    public void setBackOrigin() {
        figure.setLayoutX(originX);
        figure.setLayoutY(originY);
    }

    public void setPosition(double x, double y) {
        figure.setLayoutX(figure.getLayoutX() + x);
        figure.setLayoutY(figure.getLayoutY() + y);
    }

    public StackPane getFigure() {
        return figure;
    }
}

