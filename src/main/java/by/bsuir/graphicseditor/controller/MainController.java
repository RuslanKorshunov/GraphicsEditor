package by.bsuir.graphicseditor.controller;

import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;

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
        segment = mode.generateSegment(begin, end);
        return segment;
    }
}
