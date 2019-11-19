package by.bsuir.graphicseditor.mode.segmentmode;

import by.bsuir.graphicseditor.mode.AbstractMode;
import by.bsuir.graphicseditor.controller.MathController;
import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;
import javafx.scene.paint.Color;

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
        int deltaX = Math.abs(end.getCoordinateX() - begin.getCoordinateX());
        int deltaY = Math.abs(end.getCoordinateY() - begin.getCoordinateY());
        int length = Math.max(deltaX, deltaY);
        double dX = (double) (end.getCoordinateX() - begin.getCoordinateX()) / length;
        double dY = (double) (end.getCoordinateY() - begin.getCoordinateY()) / length;

        double xValue = begin.getCoordinateX() + 0.5 * MathController.sign(dX);
        double yValue = begin.getCoordinateY() + 0.5 * MathController.sign(dY);
        int coordinateXFirstPoint = (int) Math.floor(xValue);
        int coordinateYFirstPoint = (int) Math.floor(yValue);
        Point firstPoint = new Point(Color.BLACK, coordinateXFirstPoint, coordinateYFirstPoint);
        segment.add(firstPoint);

        int i = 1;
        while (i <= length) {
            xValue += dX;
            yValue += dY;
            int coordinateX = (int) Math.floor(xValue);
            int coordinateY = (int) Math.floor(yValue);
            Point newPoint = new Point(Color.BLACK, coordinateX, coordinateY);
            segment.add(newPoint);
            i++;
        }

        return segment;
    }
}
