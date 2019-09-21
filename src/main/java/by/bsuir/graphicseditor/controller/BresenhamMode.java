package by.bsuir.graphicseditor.controller;

import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class BresenhamMode extends AbstractMode {
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
        int deltaX = Math.abs(end.getCoordinateX() - begin.getCoordinateX());
        int deltaY = Math.abs(end.getCoordinateY() - begin.getCoordinateY());
        int length = Math.max(deltaX, deltaY);
        int coordinateX = begin.getCoordinateX();
        int coordinateY = begin.getCoordinateY();
        int error = (deltaY <= deltaX) ? 2 * deltaY - deltaX : 2 * deltaX - deltaY;
        int signX = MathController.sign(end.getCoordinateX() - begin.getCoordinateX());
        int signY = MathController.sign(end.getCoordinateY() - begin.getCoordinateY());
        Point firstPoint = new Point(Color.BLACK, begin.getCoordinateX(), begin.getCoordinateY());
        segment.add(firstPoint);

        int i = 1;
        while (i <= length) {
            if (error >= 0) {
                if (deltaX >= deltaY) {
                    coordinateY += signY;
                    error -= 2 * deltaX;
                } else {
                    coordinateX += signX;
                    error -= 2 * deltaY;
                }
            }
            if (deltaX >= deltaY) {
                coordinateX += signX;
                error += 2 * deltaY;
            } else {
                coordinateY += signY;
                error += 2 * deltaX;
            }
            i++;
            Point point = new Point(Color.BLACK, coordinateX, coordinateY);
            segment.add(point);
        }

        return segment;
    }
}