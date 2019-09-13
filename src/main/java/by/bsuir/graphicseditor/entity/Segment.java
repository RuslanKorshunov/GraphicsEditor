package by.bsuir.graphicseditor.entity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Segment {
    private List<Pixel> pixels;

    public Segment() {
        pixels = new ArrayList<>();
    }

    public boolean add(Pixel pixel) {
        return pixels.add(pixel);
    }

    public int size() {
        return pixels.size();
    }

    public boolean remove(Object o) {
        return pixels.remove(o);
    }

    public Pixel get(int i) {
        return pixels.get(i);
    }

    public Pixel set(int i, Pixel pixel) {
        return pixels.set(i, pixel);
    }

    public boolean addAll(@NotNull Collection<? extends Pixel> collection) {
        return pixels.addAll(collection);
    }
}
