package by.bsuir.graphicseditor.controller;

import by.bsuir.graphicseditor.entity.BresenhamData;
import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;
import javafx.scene.paint.Color;
import org.jetbrains.annotations.NotNull;

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
        BresenhamData bresenhamData = createBresenhamData(begin, end);
        begin.setColor(Color.BLACK);
        segment.add(begin);
        segment = generateSegment(bresenhamData, segment);

        return segment;
    }

    protected BresenhamData createBresenhamData(@NotNull Point begin, @NotNull Point end) {
        BresenhamData bresenhamData = new BresenhamData();

        int deltaX = Math.abs(end.getCoordinateX() - begin.getCoordinateX());
        bresenhamData.setDeltaX(deltaX);
        int deltaY = Math.abs(end.getCoordinateY() - begin.getCoordinateY());
        bresenhamData.setDeltaY(deltaY);
        int length = Math.max(deltaX, deltaY);
        bresenhamData.setLength(length);
        int coordinateX = begin.getCoordinateX();
        bresenhamData.setCoordinateX(coordinateX);
        int coordinateY = begin.getCoordinateY();
        bresenhamData.setCoordinateY(coordinateY);
        int error = (deltaY <= deltaX) ? 2 * deltaY - deltaX : 2 * deltaX - deltaY;
        bresenhamData.setError(error);
        int signX = MathController.sign(end.getCoordinateX() - begin.getCoordinateX());
        bresenhamData.setSignX(signX);
        int signY = MathController.sign(end.getCoordinateY() - begin.getCoordinateY());
        bresenhamData.setSignY(signY);

        return bresenhamData;
    }

    protected Segment generateSegment(BresenhamData bresenhamData, Segment segment) throws IncorrectDataException {
        if (bresenhamData == null) {
            throw new IncorrectDataException("bresenhamData can't be null");
        }
        if (segment == null) {
            throw new IncorrectDataException("segment can't be null");
        }
        int length = bresenhamData.getLength();
        int error = bresenhamData.getError();
        int deltaX = bresenhamData.getDeltaX();
        int deltaY = bresenhamData.getDeltaY();
        int coordinateX = bresenhamData.getCoordinateX();
        int coordinateY = bresenhamData.getCoordinateY();
        int signX = bresenhamData.getSignX();
        int signY = bresenhamData.getSignY();
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