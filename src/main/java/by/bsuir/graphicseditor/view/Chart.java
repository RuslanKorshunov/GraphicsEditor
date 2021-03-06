package by.bsuir.graphicseditor.view;

import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.InternalException;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Chart extends BorderPane {
    private static final Logger logger = LogManager.getLogger(Chart.class);
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;
    private static final double RECTANGLE_SIDE = 1;
    private static final double MAX_INCREASE = 9;
    private static final double MIN_INCREASE = 1;
    private double currentIncrease = 1;

    private Canvas canvas;
    private GraphicsContext gc;

    private List<List<Point>> points;

    public Chart() {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();

        points = new ArrayList<>();

        drawShapes();
        setCenter(canvas);

        createSlider();
    }

    private void drawShapes() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.WHITE);
        double width = currentIncrease < 0 ? 0.5 / Math.abs(currentIncrease) : 0.5;
        gc.setLineWidth(width);
        int row = 0;
        for (double i = RECTANGLE_SIDE * currentIncrease; i < HEIGHT; i += RECTANGLE_SIDE * currentIncrease, row++) {
            int column = 0;
            List<Point> rowPoints;
            if (points.size() - 1 >= row) {
                rowPoints = points.get(row);
            } else {
                rowPoints = new ArrayList<>();
                points.add(rowPoints);
            }
            for (double j = RECTANGLE_SIDE * currentIncrease; j < WIDTH; j += RECTANGLE_SIDE * currentIncrease, column++) {
                Point point;
                if (rowPoints.size() - 1 >= column) {
                    point = rowPoints.get(column);
                } else {
                    point = new Point();
                    point.setCoordinateX(column);
                    point.setCoordinateX(row);
                    rowPoints.add(point);
                }
                Color color = point.getColor();
                if (color.equals(Color.WHITE)) {
                    gc.strokeRoundRect(i, j, RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * currentIncrease, 0, 0);
                } else {
                    gc.setFill(color);
                    gc.fillRoundRect(i, j, RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * currentIncrease, 0, 0);
                }
            }
        }
//        drawAbscissa();
//        drawOrdinate();
    }

    private void drawAbscissa() {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * currentIncrease, WIDTH, RECTANGLE_SIDE * currentIncrease);
        gc.strokeLine(WIDTH - RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE / 2 * currentIncrease, WIDTH, RECTANGLE_SIDE * currentIncrease);
        gc.strokeLine(WIDTH - RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * 1.5 * currentIncrease, WIDTH, RECTANGLE_SIDE * currentIncrease);
        int number = 0;
        gc.setLineWidth(1);
        for (double coordinateX = (RECTANGLE_SIDE + 1.1) * currentIncrease; coordinateX < WIDTH - RECTANGLE_SIDE * currentIncrease; number++, coordinateX += RECTANGLE_SIDE * currentIncrease) {
            gc.strokeText(String.valueOf(number), coordinateX, RECTANGLE_SIDE * currentIncrease);
        }
    }

    private void drawOrdinate() {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * currentIncrease, HEIGHT);
        gc.strokeLine(RECTANGLE_SIDE / 2 * currentIncrease, HEIGHT - RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * currentIncrease, HEIGHT);
        gc.strokeLine(RECTANGLE_SIDE * 1.5 * currentIncrease, HEIGHT - RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * currentIncrease, HEIGHT);
        int number = 0;
        gc.setLineWidth(1);
        for (double coordinateY = (RECTANGLE_SIDE * 2 - 5) * currentIncrease; coordinateY < HEIGHT - RECTANGLE_SIDE * currentIncrease; number++, coordinateY += RECTANGLE_SIDE * currentIncrease) {
            gc.strokeText(String.valueOf(number), 0, coordinateY);
        }
    }

    private void createSlider() {
        Slider slider = new Slider(MIN_INCREASE, MAX_INCREASE, currentIncrease);
        slider.setShowTickMarks(false);
        slider.setShowTickLabels(true);
        slider.setMajorTickUnit(2);
        slider.setBlockIncrement(1);
        slider.setSnapToTicks(true);

        slider.valueProperty().addListener((ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) -> {
            double newIncrease = (double) newValue;
            if (newIncrease >= -1 && newIncrease <= 1) {
                currentIncrease = 1;
            } else if (newIncrease < 0) {
                currentIncrease = 1 / Math.abs(newIncrease);
            } else {
                currentIncrease = newIncrease;
            }
            drawShapes();
        });

        setBottom(slider);
    }

    public void setPoints(@NotNull Segment segment) {
        clearChart();
        for (int i = 0; i < segment.size(); i++) {
            Point point = segment.get(i);
            if (point.getCoordinateX() >= 0 && point.getCoordinateY() >= 0 &&
                    point.getCoordinateX() < WIDTH && point.getCoordinateY() < HEIGHT) {
                setPoint(point);
            } else {
                logger.warn(point+" wasn't showed because its coordinates out of range.");
            }
        }
    }

    public void setPoint(@NotNull Point pointSegment) {
        int row = pointSegment.getCoordinateX();
        int column = pointSegment.getCoordinateY();
        Color color = pointSegment.getColor();
        Point point = this.points.get(row).get(column);
        if (point != null) {
            point.setColor(color);
            drawShapes();
        }
    }

    public void clearChart() {
        for (List<Point> row : points) {
            for (Point point : row) {
                point.setColor(Color.WHITE);
            }
        }
        drawShapes();
    }

    public void clearPoint(@NotNull Point point) throws InternalException {
        try {
            Point newPoint = point.clone();
            newPoint.setColor(Color.WHITE);
            setPoint(newPoint);
            drawShapes();
        } catch (CloneNotSupportedException e) {
            throw new InternalException(e);
        }
    }
}