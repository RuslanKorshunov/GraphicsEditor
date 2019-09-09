package by.bsuir.graphicseditor.controller;

import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.exception.IncorrectDataException;

import java.util.ArrayList;
import java.util.List;

public class DDAMode extends AbstractMode {
    @Override
    public List<Point> generateSegment(Point begin, Point end) throws IncorrectDataException {
        if (begin == null) {
            throw new IncorrectDataException("begin can't be null");
        }
        if (end == null) {
            throw new IncorrectDataException("end can't be null");
        }
        List<Point> segment = new ArrayList<>();
        double deltaX = Math.abs(end.getCoordinateX() - begin.getCoordinateX());
        double deltaY = Math.abs(end.getCoordinateY() - begin.getCoordinateY());
        double length = Math.max(deltaX, deltaY);
        double dX = deltaX > deltaY ? 1 : deltaX / deltaY;
        double dY = deltaY > deltaX ? 1 : deltaY / deltaX;

        int i = 0;
        segment.add(begin);
        while (i <= length) {
            Point newPoint = new Point();
            Point oldPoint = segment.get(i);
            int coordinateX = (int) Math.floor(oldPoint.getCoordinateX() + dX);
            int coordinateY = (int) Math.floor(oldPoint.getCoordinateY() + dY);
            newPoint.setCoordinateX(coordinateX);
            newPoint.setCoordinateY(coordinateY);
            segment.add(newPoint);
            i++;
        }
        return segment;
    }
}
