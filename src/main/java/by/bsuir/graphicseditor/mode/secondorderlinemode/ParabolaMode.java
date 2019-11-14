package by.bsuir.graphicseditor.mode.secondorderlinemode;

import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;
import by.bsuir.graphicseditor.mode.AbstractMode;
import javafx.scene.paint.Color;

public class ParabolaMode extends AbstractMode {
    @Override
    public Segment generateSegment(Point center, int... parameters) throws IncorrectDataException {
        if (center == null || parameters == null || parameters.length == 0) {
            throw new IncorrectDataException("parameters has invalid value");
        }
        int xCenter = center.getCoordinateX();
        int yCenter = center.getCoordinateY();
        int x = 0;
        int y = 0;
        int p = parameters[0];
        int error = 1 - 2 * p;
        int limit = 500;
        Segment segment = new Segment();
        Point firstPoint = new Point(Color.BLACK, xCenter, yCenter);
        segment.add(firstPoint);
        int sign = 0;
        while (x < limit) {
            if (error > 0) {
                sign = Math.abs(error) - Math.abs(y * y - 2 * p * (x + 1));
                if (sign <= 0) {
                    x++;
                    y++;
                    error = error - 2 * p + 2 * y + 1;
                } else {
                    x++;
                    error = error - 2 * p;
                }
            } else {
                sign = Math.abs((int) Math.pow(y + 1, 2) - 2 * p * x) - Math.abs(error);
                if (sign <= 0) {
                    y++;
                    error = error + 2 * y + 1;
                } else {
                    x++;
                    y++;
                    error = error - 2 * p + 2 * y + 1;
                }
            }
            Point point = new Point(Color.BLACK, xCenter + x, yCenter + y);
            Point pointReverse = new Point(Color.BLACK, xCenter + x, yCenter - y);
            segment.add(point);
            segment.add(pointReverse);
        }
        return segment;
    }
}
