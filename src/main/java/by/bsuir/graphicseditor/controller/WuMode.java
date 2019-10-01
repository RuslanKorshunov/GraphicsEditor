package by.bsuir.graphicseditor.controller;

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
            int coordinateXAdditional = (deltaX >= deltaY) ? coordinateX : coordinateX - 1;
            int coordinateYAdditional = (deltaY > deltaX) ? coordinateY : coordinateY + 1;
            int signX = bresenhamData.getSignX();
            int signY = bresenhamData.getSignY();
            int i = 1;
            while (i <= length) {
                Point point = new Point(Color.BLACK, coordinateX, coordinateY);
                segment.add(point);
                if (coordinateXAdditional >= 0 && coordinateYAdditional >= 0) {
                    Point pointAdd = new Point(findColor(1 - error), coordinateXAdditional, coordinateYAdditional);
                    segment.add(pointAdd);
                }
                if (error >= 0.5) {
                    if (deltaX >= deltaY) {
                        coordinateY += signY;
                        coordinateYAdditional += signY;
                        error -= 1;
                    } else {
                        coordinateX += signX;
                        coordinateXAdditional += signX;
                        error -= 1;
                    }
                }
                if (deltaX >= deltaY) {
                    coordinateX += signX;
                    coordinateXAdditional += signX;
                    error += (double) deltaY / deltaX;
                } else {
                    coordinateY += signY;
                    coordinateYAdditional += signY;
                    error += (double) deltaX / deltaY;
                }
                i++;
            }
            Point point = new Point(findColor(error), coordinateX, coordinateY);
            segment.add(point);
        }
        return segment;
    }

    private Color findColor(double error) {
        Color color = null;
        if (error >= 0.9) {
            color = Color.rgb(105, 105, 105, 0.99);
        } else if (error >= 0.7 && error < 0.9) {
            color = Color.rgb(128, 128, 128, 0.99);
        } else if (error >= 0.6 && error < 0.7) {
            color = Color.rgb(169, 169, 169, 0.99);
        } else if (error >= 0.4 && error < 0.6) {
            color = Color.rgb(192, 192, 192, 0.99);
        } else if (error >= 0.1 && error < 0.4) {
            color = Color.rgb(211, 211, 211, 0.99);
        } else if (error < 0.1) {
            color = Color.rgb(220, 220, 220, 0.99);
        }
        return color;
    }
}