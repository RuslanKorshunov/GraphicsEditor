package by.bsuir.graphicseditor.entity;

import javafx.scene.paint.Color;

public class Pixel {
    private Color color;
    private Point point;

    public Pixel() {
        color = Color.BLACK;
        point = new Point();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
