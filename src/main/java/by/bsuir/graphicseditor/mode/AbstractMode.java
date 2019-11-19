package by.bsuir.graphicseditor.mode;

import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;

public abstract class AbstractMode {
    public Segment generateSegment(Point begin, Point end) throws IncorrectDataException {
        return null;
    }

    public Segment generateSegment(Point center, int... parameters) throws IncorrectDataException {
        return null;
    }
}
