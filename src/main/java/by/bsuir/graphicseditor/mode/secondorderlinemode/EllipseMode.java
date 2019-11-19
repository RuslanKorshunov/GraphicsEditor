package by.bsuir.graphicseditor.mode.secondorderlinemode;

import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;
import by.bsuir.graphicseditor.mode.AbstractMode;
import javafx.scene.paint.Color;

public class EllipseMode extends AbstractMode {
    @Override
    public Segment generateSegment(Point center, int... parameters) throws IncorrectDataException {
        if (center == null || parameters == null || parameters.length == 0) {
            throw new IncorrectDataException("parameters has invalid value");
        }
        int a = parameters[0];
        int b = parameters[1];
        int xCenter = center.getCoordinateX();
        int yCenter = center.getCoordinateY();
        int x = 0;
        int y = b;
        int limit = 0;
        int error = a * a + b * b - 2 * a * a * b;
        Segment segment = new Segment();
        Point firstPoint = new Point(Color.BLACK, xCenter, yCenter + b);
        Point firstPointReverse = new Point(Color.BLACK, xCenter, yCenter - b);
        segment.add(firstPoint);
        segment.add(firstPointReverse);
        while (y > limit) {
            if (error > 0) {
                int delta = 2 * (error - b * b * x) - 1;
                if (delta > 0) {
                    y--;
                    error += a * a * (1 - 2 * y);
                } else {
                    x++;
                    y--;
                    error += b * b * (2 * x + 1) + a * a * (1 - 2 * y);
                }
            } else if (error < 0) {
                int delta = 2 * (error + a * a * y) - 1;
                if (delta > 0) {
                    x++;
                    y--;
                    error += b * b * (2 * x + 1) + a * a * (1 - 2 * y);
                } else {
                    x++;
                    error += b * b * (2 * x + 1);
                }
            } else {
                x++;
                y--;
                error += b * b * (2 * x + 1) + a * a * (1 - 2 * y);
            }
            Point pointFirstOctant = new Point(Color.BLACK, xCenter + x, yCenter + y);
            Point pointSecondOctant = new Point(Color.BLACK, xCenter - x, yCenter + y);
            Point pointThirdOctant = new Point(Color.BLACK, xCenter - x, yCenter - y);
            Point pointForthOctant = new Point(Color.BLACK, xCenter + x, yCenter - y);
            segment.add(pointFirstOctant);
            segment.add(pointSecondOctant);
            segment.add(pointThirdOctant);
            segment.add(pointForthOctant);
        }
        return segment;
    }
}
