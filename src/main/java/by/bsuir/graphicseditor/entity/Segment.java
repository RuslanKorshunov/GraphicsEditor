package by.bsuir.graphicseditor.entity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Segment {
    private List<Point> points;

    public Segment() {
        points = new ArrayList<>();
    }

    public boolean add(Point point) {
        return points.add(point);
    }

    public int size() {
        return points.size();
    }

    public boolean remove(Object o) {
        return points.remove(o);
    }

    public Point get(int i) {
        return points.get(i);
    }

    public Point set(int i, Point point) {
        return points.set(i, point);
    }

    public boolean addAll(@NotNull Collection<? extends Point> collection) {
        return points.addAll(collection);
    }


}