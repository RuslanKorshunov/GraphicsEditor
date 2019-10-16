package by.bsuir.graphicseditor.view;

import by.bsuir.graphicseditor.controller.MainController;
import by.bsuir.graphicseditor.controller.ModeName;
import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;
import by.bsuir.graphicseditor.exception.InternalException;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainWindow extends Application {
    private static final Logger logger = LogManager.getLogger(MainWindow.class);
    private static final String TITLE = "Graphics Editor";
    private static final String SEGMENT_TITLE = "Segment";
    private static final String MENU_TITLE = "Menu";
    private static final String DDA_TITLE = "DDA";
    private static final String BRESENHAM_TITLE = "Bresenham";
    private static final String MODE_TITLE = "Modes";
    private static final String WU_TITLE = "Wu";
    private static final String X_FIRST_TITLE = "x1";
    private static final String Y_FIRST_TITLE = "y1";
    private static final String X_SECOND_TITLE = "x2";
    private static final String Y_SECOND_TITLE = "y2";
    private static final String COORDINATES_TITLE = "Coordinates";
    private static final String START_TITLE = "Start";
    private static final String TEXT_FIELD = "^\\d+$";
    private static final String DEBUG_TITLE = "Debug";
    private static final String FORWARD_TITLE = "Forward";
    private static final String BACK_TITLE = "Back";
    private static final int WIDTH_WINDOW = 500;
    private static final int HEIGHT_WINDOW = 600;
    private static final int X_COORDINATE = 400;
    private static final int Y_COORDINATE = 100;
    private static final int GAP = 10;
    private MainController controller;
    private ModeName modeName = ModeName.DDA;
    private Segment segment;
    private int step = -1;

    private Chart chart;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        controller = new MainController();
    }

    @Override
    public void start(@NotNull Stage stage) {
        stage.setTitle(TITLE);
        stage.setWidth(WIDTH_WINDOW);
        stage.setHeight(HEIGHT_WINDOW);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

        Pane menuPane = new FlowPane(Orientation.VERTICAL, GAP, GAP);
        menuPane.setPadding(new Insets(GAP));
        menuPane.setMaxHeight(Double.MAX_VALUE);
        menuPane.setMaxWidth(Double.MAX_VALUE);

        try {
            createModePanel(menuPane);
            createCoordinatesPanel(menuPane);
        } catch (IncorrectDataException e) {
            logger.error(e);
        }

        chart = new Chart();

        BorderPane root = new BorderPane();
        root.setCenter(chart);
        root.setLeft(menuPane);

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @Contract("null -> fail")
    private void createModePanel(Pane pane) throws IncorrectDataException {
        if (pane == null) {
            throw new IncorrectDataException("pane can't be null");
        }

        Label modeLabel = new Label(MODE_TITLE);

        RadioButton modeDDA = new RadioButton(DDA_TITLE);
        modeDDA.fire();
        RadioButton modeBresenham = new RadioButton(BRESENHAM_TITLE);
        RadioButton modeWu = new RadioButton(WU_TITLE);

        ToggleGroup modeGroup = new ToggleGroup();
        modeDDA.setToggleGroup(modeGroup);
        modeBresenham.setToggleGroup(modeGroup);
        modeWu.setToggleGroup(modeGroup);

        modeGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observableValue, Toggle oldToggle, Toggle newToggle) -> {
            RadioButton radioButton = (RadioButton) modeGroup.getSelectedToggle();
            String radioButtonValue = radioButton.getText().toUpperCase();
            modeName = ModeName.valueOf(radioButtonValue);
        });

        pane.getChildren().addAll(modeLabel, modeDDA, modeBresenham, modeWu);
    }

    @Contract("null -> fail")
    private void createCoordinatesPanel(Pane pane) throws IncorrectDataException {
        if (pane == null) {
            throw new IncorrectDataException("pane can't be null");
        }

        Label coordinatesLabel = new Label(COORDINATES_TITLE);
        Label xFirstLabel = new Label(X_FIRST_TITLE);
        Label yFirstLabel = new Label(Y_FIRST_TITLE);
        Label xSecondLabel = new Label(X_SECOND_TITLE);
        Label ySecondLabel = new Label(Y_SECOND_TITLE);
        Label debugLabel = new Label(DEBUG_TITLE);

        TextField xFirstField = new TextField();
        TextField yFirstField = new TextField();
        TextField xSecondField = new TextField();
        TextField ySecondField = new TextField();
        List<TextField> textFields = new ArrayList<>();
        textFields.add(xFirstField);
        textFields.add(yFirstField);
        textFields.add(xSecondField);
        textFields.add(ySecondField);

        GridPane fields = new GridPane();
        fields.getColumnConstraints().add(new ColumnConstraints(20));
        fields.getColumnConstraints().add(new ColumnConstraints(50));
        fields.add(xFirstLabel, 0, 0);
        fields.add(xFirstField, 1, 0);
        fields.add(yFirstLabel, 0, 1);
        fields.add(yFirstField, 1, 1);
        fields.add(xSecondLabel, 0, 2);
        fields.add(xSecondField, 1, 2);
        fields.add(ySecondLabel, 0, 3);
        fields.add(ySecondField, 1, 3);

        Button forwardButton = new Button(FORWARD_TITLE);
        forwardButton.setOnAction((ActionEvent actionEvent) -> {
            if (step == -1) {
                chart.clearChart();
                step = 0;
            }
            if (step <= segment.size() - 1) {
                Point point = segment.get(step);
                chart.setPoint(point);
                step += 1;
            }
        });

        Button backButton = new Button(BACK_TITLE);
        backButton.setOnAction((ActionEvent actionEvent) -> {
            if (step != -1) {
                try {
                    Point point = segment.get(step);
                    chart.clearPoint(point);
                    step--;
                } catch (InternalException e) {
                    logger.error(e);
                }
            }
        });

        Button createButton = new Button(START_TITLE);
        createButton.setOnAction((ActionEvent actionEvent) -> {
            boolean isCorrect = true;
            for (TextField textField : textFields) {
                if (!textField.getText().matches(TEXT_FIELD)) {
                    isCorrect = false;
                }
            }
            if (isCorrect) {
                int xFirstCoordinate = Integer.parseInt(xFirstField.getText());
                int yFirstCoordinate = Integer.parseInt(yFirstField.getText());
                int xSecondCoordinate = Integer.parseInt(xSecondField.getText());
                int ySecondCoordinate = Integer.parseInt(ySecondField.getText());
                Point begin = new Point();
                begin.setCoordinateX(xFirstCoordinate);
                begin.setCoordinateY(yFirstCoordinate);
                Point end = new Point();
                end.setCoordinateX(xSecondCoordinate);
                end.setCoordinateY(ySecondCoordinate);
                try {
                    segment = controller.generateSegment(modeName, begin, end);
                    chart.setPoints(segment);
                    step = segment.size() - 1;
                } catch (IncorrectDataException e) {
                    logger.error(e);
                }
            }
        });


        pane.getChildren().addAll(coordinatesLabel, fields, createButton, debugLabel, backButton, forwardButton);
    }
}