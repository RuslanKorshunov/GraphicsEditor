package by.bsuir.graphicseditor.controller;

import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class DDAMode extends AbstractMode {
    @Override
    public Segment generateSegment(Point begin, Point end) throws IncorrectDataException {
        if (begin == null) {
            throw new IncorrectDataException("begin can't be null");
        }
        if (end == null) {
            throw new IncorrectDataException("end can't be null");
        }
        Segment segment = new Segment();
        List<Point> points = new ArrayList<>();
        begin.setColor(Color.BLACK);
        points.add(begin);
        double deltaX = Math.abs(end.getCoordinateX() - begin.getCoordinateX());
        double deltaY = Math.abs(end.getCoordinateY() - begin.getCoordinateY());
        double length = Math.max(deltaX, deltaY);
        double dX = deltaX > deltaY ? 1 : (end.getCoordinateX() - begin.getCoordinateX()) / length;
        double dY = deltaY > deltaX ? 1 : (end.getCoordinateY() - begin.getCoordinateY()) / length;

        Point firstPoint = new Point();
        double xValue = begin.getCoordinateX() + 0.5 * sign(dX);
        double yValue = begin.getCoordinateY() + 0.5 * sign(dY);
        int coordinateXFirstPoint = (int) Math.floor(xValue);
        int coordinateYFirstPoint = (int) Math.floor(yValue);
        firstPoint.setCoordinateX(coordinateXFirstPoint);
        firstPoint.setCoordinateY(coordinateYFirstPoint);
        firstPoint.setColor(Color.BLACK);
        points.add(firstPoint);

        int i = 1;
        while (i <= length) {
            Point newPoint = new Point();
            xValue += dX;
            yValue += dY;
            int coordinateX = (int) Math.floor(xValue);
            int coordinateY = (int) Math.floor(yValue);
            newPoint.setCoordinateX(coordinateX);
            newPoint.setCoordinateY(coordinateY);
            newPoint.setColor(Color.BLACK);
            points.add(newPoint);
            i++;
        }
        segment.addAll(points);

        return segment;
    }

    private int sign(double number) {
        int result = 0;
        if (number < 0) {
            result = -1;
        } else if (number > 0) {
            result = 1;
        }
        return result;
    }
}
