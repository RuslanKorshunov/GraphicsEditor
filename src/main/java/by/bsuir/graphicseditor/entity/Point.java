package by.bsuir.graphicseditor.entity;

public class Point {
    private int coordinateX;
    private int coordinateY;
    private int coordinateZ;
    private int coordinateW;

    public Point() {
        coordinateX = 0;
        coordinateY = 0;
        coordinateZ = 0;
        coordinateW = 0;
    }

    public double getCoordinateX() {
        return coordinateX;
    }

    public void setCoordinateX(int coordinateX) {
        this.coordinateX = coordinateX;
    }

    public double getCoordinateY() {
        return coordinateY;
    }

    public void setCoordinateY(int coordinateY) {
        this.coordinateY = coordinateY;
    }

    public double getCoordinateZ() {
        return coordinateZ;
    }

    public void setCoordinateZ(int coordinateZ) {
        this.coordinateZ = coordinateZ;
    }

    public double getCoordinateW() {
        return coordinateW;
    }

    public void setCoordinateW(int coordinateW) {
        this.coordinateW = coordinateW;
    }

    @Override
    public String toString() {
        return "Point{" +
                "coordinateX=" + coordinateX +
                ", coordinateY=" + coordinateY +
                ", coordinateZ=" + coordinateZ +
                ", coordinateW=" + coordinateW +
                '}';
    }
}
