package by.bsuir.graphicseditor.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

public class Chart extends BorderPane {
    private static final int WIDTH = 300;
    private static final int HEIGHT = 300;
    private static final double RECTANGLE_SIDE = 20;
    private static final double MAX_INCREASE = 5;
    private static final double MIN_INCREASE = -5;
    private double currentIncrease = 1;
    private int numberStep = 2;

    private Canvas canvas;
    private GraphicsContext gc;

    public Chart() {
        canvas = new Canvas(WIDTH, HEIGHT);
        gc = canvas.getGraphicsContext2D();
        drawShapes();
        setCenter(canvas);

        createSlider();
    }

    private void drawShapes() {
        gc.clearRect(0, 0, WIDTH, HEIGHT);
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLUE);
        gc.setLineWidth(0.5);
        for (double i = 0; i < HEIGHT; i += RECTANGLE_SIDE * currentIncrease) {
            for (double j = 0; j < WIDTH; j += RECTANGLE_SIDE * currentIncrease) {
                gc.strokeRoundRect(i, j, RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * currentIncrease, 0, 0);
            }
        }
        drawAbscissa();
        drawOrdinate();
    }

    private void drawAbscissa() {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * currentIncrease, WIDTH, RECTANGLE_SIDE * currentIncrease);
        gc.strokeLine(WIDTH - RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE / 2 * currentIncrease, WIDTH, RECTANGLE_SIDE * currentIncrease);
        gc.strokeLine(WIDTH - RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * 1.5 * currentIncrease, WIDTH, RECTANGLE_SIDE * currentIncrease);
        int number = 0;
        gc.setLineWidth(1);
        for (double coordinateX = (RECTANGLE_SIDE - 2) * currentIncrease; coordinateX < WIDTH - RECTANGLE_SIDE * currentIncrease; number++, coordinateX += RECTANGLE_SIDE * currentIncrease) {
            gc.strokeText(String.valueOf(number), coordinateX, RECTANGLE_SIDE * currentIncrease);
        }
    }

    private void drawOrdinate() {
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(2);
        gc.strokeLine(RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * currentIncrease, HEIGHT);
        gc.strokeLine(RECTANGLE_SIDE / 2 * currentIncrease, HEIGHT - RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * currentIncrease, HEIGHT);
        gc.strokeLine(RECTANGLE_SIDE * 1.5 * currentIncrease, HEIGHT - RECTANGLE_SIDE * currentIncrease, RECTANGLE_SIDE * currentIncrease, HEIGHT);
        int number = 1;
        gc.setLineWidth(1);
        for (double coordinateY = (RECTANGLE_SIDE * 2 - 2) * currentIncrease; coordinateY < HEIGHT - RECTANGLE_SIDE * currentIncrease; number++, coordinateY += RECTANGLE_SIDE * currentIncrease) {
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

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                double newIncrease = (double) newValue;
                if (newIncrease >= -1 && newIncrease <= 1) {
                    currentIncrease = 1;
                } else if (newIncrease < 0) {
                    currentIncrease = 1 / Math.abs(newIncrease);
                } else {
                    currentIncrease = newIncrease;
                }
                drawShapes();
            }
        });

        setBottom(slider);
    }
}