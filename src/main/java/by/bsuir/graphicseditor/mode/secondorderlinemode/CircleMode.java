package by.bsuir.graphicseditor.mode.secondorderlinemode;

import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;
import by.bsuir.graphicseditor.mode.AbstractMode;
import javafx.scene.paint.Color;

public class CircleMode extends AbstractMode {
    @Override
    public Segment generateSegment(Point center, int... parameters) throws IncorrectDataException {
        int R = parameters[0];
        int x = 0;
        int y = R;
        int limit = 0;
        int error = 2 * (1 - R);
        Segment segment = new Segment();
        Point firstPoint = new Point(Color.BLACK, x, y);
        segment.add(firstPoint);
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
            Point point = new Point(Color.BLACK, x, y);
            segment.add(point);
        }
        return super.generateSegment(center, parameters);
    }
}
