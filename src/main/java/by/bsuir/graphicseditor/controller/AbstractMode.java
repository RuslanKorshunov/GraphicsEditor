package by.bsuir.graphicseditor.controller;

import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;

public abstract class AbstractMode {
    public abstract Segment generateSegment(Point begin, Point end) throws IncorrectDataException;
}
