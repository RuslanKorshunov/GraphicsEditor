package by.bsuir.graphicseditor.mode.secondorderlinemode;

import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;
import by.bsuir.graphicseditor.mode.AbstractMode;
import javafx.scene.paint.Color;

public class CircleMode extends AbstractMode {
    @Override
    public Segment generateSegment(Point center, int... parameters) throws IncorrectDataException {
        if (center == null || parameters == null || parameters.length == 0) {
            throw new IncorrectDataException("parameters has invalid value");
        }
        int R = parameters[0];
        int xCenter = center.getCoordinateX();
        int yCenter = center.getCoordinateY();
        int x = 0;
        int y = R;
        int limit = 0;
        int error = 2 * (1 - R);
        Segment segment = new Segment();
        Point firstPoint = new Point(Color.RED, xCenter, yCenter + R);
        Point firstPointReverse = new Point(Color.BLACK, xCenter, yCenter - R);
        segment.add(firstPoint);
        segment.add(firstPointReverse);
        while (y > limit) {
            if (error > 0) {
                int delta = 2 * error - 2 * x - 1;
                if (delta > 0) {
                    y--;
                    error += 1 - 2 * y;
                } else {
                    x++;
                    y--;
                    error += 2 * x - 2 * y + 2;
                }
            } else if (error < 0) {
                int delta = 2 * error + 2 * y - 1;
                if (delta > 0) {
                    x++;
                    y--;
                    error += 2 * x - 2 * y + 2;
                } else {
                    x++;
                    error += 2 * x + 1;
                }
            } else {
                x++;
                y--;
                error += 2 * x - 2 * y + 2;
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
