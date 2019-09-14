package by.bsuir.graphicseditor.entity;

import javafx.scene.paint.Color;

public class Point {
    private Color color;
    private int coordinateX;
    private int coordinateY;
    private int coordinateZ;
    private int coordinateW;

    public Point() {
        color = Color.WHITE;
        coordinateX = 0;
        coordinateY = 0;
        coordinateZ = 0;
        coordinateW = 0;
    }

    public int getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public int getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public int getCoordinateZ() {
        return coordinateZ;
    }

    public void setCoordinateZ(int coordinateZ) {
        this.coordinateZ = coordinateZ;
    }

    public int getCoordinateW() {
        return coordinateW;
    }

    public void setCoordinateW(int coordinateW) {
        this.coordinateW = coordinateW;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Point{" +
                "color=" + color +
                ", coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", coordinateZ=" + coordinateZ +
                ", coordinateW=" + coordinateW +
                '}';
    }
}
