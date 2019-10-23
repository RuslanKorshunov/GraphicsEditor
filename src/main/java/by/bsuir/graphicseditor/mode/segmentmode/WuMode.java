package by.bsuir.graphicseditor.mode.segmentmode;

import by.bsuir.graphicseditor.entity.BresenhamData;
import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;
import javafx.scene.paint.Color;

public class WuMode extends BresenhamMode {

    @Override
    protected Segment generateSegment(BresenhamData bresenhamData, Segment segment) throws IncorrectDataException {
        if (bresenhamData == null) {
            throw new IncorrectDataException("bresenhamData can't be null");
        }
        if (segment == null) {
            throw new IncorrectDataException("segment can't be null");
        }
        int deltaX = bresenhamData.getDeltaX();
        int deltaY = bresenhamData.getDeltaY();
        if (deltaX == 0 || deltaY == 0 || deltaX == deltaY) {
            segment = super.generateSegment(bresenhamData, segment);
        } else {
            int length = bresenhamData.getLength();
            double error = (deltaY <= deltaX) ? (double) deltaY / deltaX : (double) deltaX / deltaY;
            int coordinateX = bresenhamData.getCoordinateX();
            int coordinateY = bresenhamData.getCoordinateY();
            int signX = bresenhamData.getSignX();
            int signY = bresenhamData.getSignY();
            int i = 1;
            while (i <= length) {
                Point point = new Point(Color.BLACK, coordinateX, coordinateY);
                segment.add(point);
                if (error >= 0.5) {
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
                if (deltaX >= deltaY) {
                    if (coordinateY - 1 >= 0) {
                        Point pointAddFirst = new Point(findColor(1 - error), coordinateX, coordinateY - 1);
                        segment.add(pointAddFirst);
                    }
                    Point pointAddSecond = new Point(findColor(error), coordinateX, coordinateY + 1);
                    segment.add(pointAddSecond);
                } else {
                    if (coordinateX - 1 >= 0) {
                        Point pointAddFirst = new Point(findColor(error), coordinateX - 1, coordinateY);
                        segment.add(pointAddFirst);
                    }
                    Point pointAddSecond = new Point(findColor(1 - error), coordinateX + 1, coordinateY);
                    segment.add(pointAddSecond);
                }
                i++;
            }
            Point point = new Point(findColor(error), coordinateX, coordinateY);
            segment.add(point);
        }
        return segment;
    }

    private Color findColor(double error) {
        if (error <= 0) {
            error = 0;
        }
        if (error > 1) {
            error = 1;
        }
        return Color.gray(error);
    }
}