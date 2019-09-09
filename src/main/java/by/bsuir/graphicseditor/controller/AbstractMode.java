package by.bsuir.graphicseditor.controller;

import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.exception.IncorrectDataException;

import java.util.List;

public abstract class AbstractMode {
    public abstract List<Point> generateSegment(Point begin, Point end) throws IncorrectDataException;
}
