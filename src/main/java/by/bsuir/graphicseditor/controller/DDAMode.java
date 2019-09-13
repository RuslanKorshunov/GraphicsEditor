package by.bsuir.graphicseditor.controller;

import by.bsuir.graphicseditor.entity.Pixel;
import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;

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
        List<Pixel> pixels = new ArrayList<>();
        double deltaX = Math.abs(end.getCoordinateX() - begin.getCoordinateX());
        double deltaY = Math.abs(end.getCoordinateY() - begin.getCoordinateY());
        double length = Math.max(deltaX, deltaY);
        double dX = deltaX > deltaY ? 1 : deltaX / deltaY;
        double dY = deltaY > deltaX ? 1 : deltaY / deltaX;

        Point firstPoint = new Point();
        int coordinateXFirstPoint = (int) Math.floor(begin.getCoordinateX() + 0.5 * dX);
        int coordinateYFirstPoint = (int) Math.floor(begin.getCoordinateY() + 0.5 * dY);
        firstPoint.setCoordinateX(coordinateXFirstPoint);
        firstPoint.setCoordinateY(coordinateYFirstPoint);
        Pixel firstPixel = new Pixel();
        firstPixel.setPoint(firstPoint);

        int i = 1;
        while (i <= length) {
            Point newPoint = new Point();
            Point oldPoint = pixels.get(i - 1).getPoint();
            int coordinateX = (int) Math.floor(oldPoint.getCoordinateX() + dX);
            int coordinateY = (int) Math.floor(oldPoint.getCoordinateY() + dY);
            newPoint.setCoordinateX(coordinateX);
            newPoint.setCoordinateY(coordinateY);
            Pixel pixel = new Pixel();
            pixel.setPoint(newPoint);
            pixels.add(pixel);
            i++;
        }
        return segment;
    }
}
