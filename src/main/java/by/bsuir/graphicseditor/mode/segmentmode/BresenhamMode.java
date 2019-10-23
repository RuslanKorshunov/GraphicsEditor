package by.bsuir.graphicseditor.mode.segmentmode;

import by.bsuir.graphicseditor.mode.AbstractMode;
import by.bsuir.graphicseditor.controller.MathController;
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

        if (deltaX >= deltaY) {
            if (!(end.getCoordinateY() - begin.getCoordinateY() <= 0 && end.getCoordinateX() - begin.getCoordinateX() <= 0) &&
                    !(end.getCoordinateY() - begin.getCoordinateY() <= 0 && end.getCoordinateX() - begin.getCoordinateX() >= 0)) {
                Point pointTemporary = begin;
                begin = end;
                end = pointTemporary;
            }
        } else {
            if (!(end.getCoordinateY() - begin.getCoordinateY() >= 0 && end.getCoordinateX() - begin.getCoordinateX() >= 0) &&
                    !(end.getCoordinateY() - begin.getCoordinateY() <= 0 && end.getCoordinateX() - begin.getCoordinateX() >= 0)) {
                Point pointTemporary = begin;
                begin = end;
                end = pointTemporary;
            }
        }

        bresenhamData.setLength(length);
        int coordinateX = begin.getCoordinateX();
        bresenhamData.setCoordinateX(coordinateX);
        int coordinateY = begin.getCoordinateY();
        bresenhamData.setCoordinateY(coordinateY);
        double error = (deltaY <= deltaX) ? (double) deltaY / deltaX - 0.5 : (double) deltaX / deltaY - 0.5;
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
        double error = bresenhamData.getError();
        int deltaX = bresenhamData.getDeltaX();
        int deltaY = bresenhamData.getDeltaY();
        int coordinateX = bresenhamData.getCoordinateX();
        int coordinateY = bresenhamData.getCoordinateY();
        int signX = bresenhamData.getSignX();
        int signY = bresenhamData.getSignY();
        int i = 1;
        while (i <= length) {
            Point point = new Point(Color.BLACK, coordinateX, coordinateY);
            segment.add(point);
            while (error >= 0) {
                if (deltaX >= deltaY) {
                    coordinateY += signY;
                    error -= 1;
                } else {
                    coordinateX += signX;
                    error -= 1;
                }
            }
            if (deltaX >= deltaY) {
                coordinateX += signX;
                error += (double) deltaY / deltaX;
            } else {
                coordinateY += signY;
                error += (double) deltaX / deltaY;
            }
            i++;
        }
        Point point = new Point(Color.BLACK, coordinateX, coordinateY);
        segment.add(point);
        return segment;
    }
}