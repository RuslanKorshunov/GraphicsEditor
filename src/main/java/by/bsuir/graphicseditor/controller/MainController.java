package by.bsuir.graphicseditor.controller;

import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;
import by.bsuir.graphicseditor.mode.AbstractMode;
import by.bsuir.graphicseditor.mode.ModeName;
import by.bsuir.graphicseditor.mode.secondorderlinemode.CircleMode;
import by.bsuir.graphicseditor.mode.segmentmode.BresenhamMode;
import by.bsuir.graphicseditor.mode.segmentmode.DDAMode;
import by.bsuir.graphicseditor.mode.segmentmode.WuMode;

public class MainController {

    public MainController() {
    }

    public Segment generateSegment(ModeName modeName, Point begin, Point end) throws IncorrectDataException {
        AbstractMode mode;
        Segment segment;
        switch (modeName) {
            case DDA:
                mode = new DDAMode();
                break;
            case WU:
                mode = new WuMode();
                break;
            case BRESENHAM:
                mode = new BresenhamMode();
                break;
            default:
                throw new IncorrectDataException("modeName has incorrect value");
        }
        return mode.generateSegment(begin, end);
    }

    public Segment generateSecondOrderLine(ModeName modeName, Point center, int... parameters) throws IncorrectDataException {
        AbstractMode mode;
        Segment segment;
        switch (modeName) {
            case CIRCLE:
                mode = new CircleMode();
                break;
            default:
                throw new IncorrectDataException("modeName has incorrect value");
        }
        return mode.generateSegment(center, parameters);
    }
}
