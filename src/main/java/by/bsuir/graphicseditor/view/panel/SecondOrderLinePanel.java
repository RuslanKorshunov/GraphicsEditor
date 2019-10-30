package by.bsuir.graphicseditor.view.panel;

import by.bsuir.graphicseditor.controller.MainController;
import by.bsuir.graphicseditor.entity.Point;
import by.bsuir.graphicseditor.entity.Segment;
import by.bsuir.graphicseditor.exception.IncorrectDataException;
import by.bsuir.graphicseditor.exception.InternalException;
import by.bsuir.graphicseditor.mode.ModeName;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.List;

public class SecondOrderLinePanel extends AbstractPanel {
    private static final Logger logger = LogManager.getLogger(SecondOrderLinePanel.class);
    private static final String CIRCLE_TITLE = "Circle";
    private static final String ELLIPSE_TITLE = "Ellipse";
    private static final String PARAMETERS_TITLE = "Parameters";
    private static final String X_TITLE = "x";
    private static final String Y_TITLE = "y";
    private static final String R_TITLE = "R";
    private static final String A_TITLE = "a";
    private static final String B_TITLE = "b";

    private MainController controller;
    private Pane modePane;
    private Pane parameterPane;
    private ModeName modeName;
    private Segment segment;
    private int step = -1;

    public SecondOrderLinePanel() {
        super();
        controller = new MainController();
        modeName = ModeName.CIRCLE;
        try {
            createModePanel();
            createParametersPanel(modeName);
        } catch (IncorrectDataException e) {
        }
    }

    @Contract("null -> fail")
    private void createModePanel() throws IncorrectDataException {
        if (menuPane == null) {
            throw new IncorrectDataException("pane can't be null");
        }
        if (modePane == null) {
            modePane = new FlowPane(Orientation.VERTICAL, GAP, GAP);
        }
        Label modeLabel = new Label(MODE_TITLE);

        RadioButton modeCircle = new RadioButton(CIRCLE_TITLE);
        modeCircle.fire();
        RadioButton modeEllipse = new RadioButton(ELLIPSE_TITLE);

        ToggleGroup modeGroup = new ToggleGroup();
        modeCircle.setToggleGroup(modeGroup);
        modeEllipse.setToggleGroup(modeGroup);

        modeGroup.selectedToggleProperty().addListener((ObservableValue<? extends Toggle> observableValue, Toggle oldToggle, Toggle newToggle) -> {
            try {
                RadioButton radioButton = (RadioButton) modeGroup.getSelectedToggle();
                String radioButtonValue = radioButton.getText().toUpperCase();
                modeName = ModeName.valueOf(radioButtonValue);
                createParametersPanel(modeName);
            } catch (IncorrectDataException e) {
                logger.error(e);
            }
        });

        modePane.getChildren().addAll(modeLabel, modeCircle, modeEllipse);
        menuPane.getChildren().addAll(modePane);
    }

    @Contract("null -> fail")
    private void createParametersPanel(ModeName modeName) throws IncorrectDataException {
        if (menuPane == null) {
            throw new IncorrectDataException("pane can't be null");
        }
        Label parametersLabel = new Label(PARAMETERS_TITLE);
        Label debugLabel = new Label(DEBUG_TITLE);

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

        GridPane fields = new GridPane();
        fields.getColumnConstraints().add(new ColumnConstraints(20));
        fields.getColumnConstraints().add(new ColumnConstraints(50));

        Label xLabel = new Label(X_TITLE);
        Label yLabel = new Label(Y_TITLE);

        TextField xField = new TextField();
        TextField yField = new TextField();

        List<TextField> textFields = new ArrayList<>();
        textFields.add(xField);
        textFields.add(yField);

        fields.add(xLabel, 0, 0);
        fields.add(xField, 1, 0);
        fields.add(yLabel, 0, 1);
        fields.add(yField, 1, 1);

        switch (modeName) {
            case CIRCLE:
                Label radiusLabel = new Label(R_TITLE);

                TextField radiusField = new TextField();

                fields.add(radiusLabel, 0, 2);
                fields.add(radiusField, 1, 2);

                createButton.setOnAction((ActionEvent actionEvent) -> {
                    boolean isCorrect = true;
                    for (TextField textField : textFields) {
                        if (!textField.getText().matches(TEXT_FIELD)) {
                            isCorrect = false;
                        }
                    }
                    if (isCorrect) {
                        int xCoordinate = Integer.parseInt(xField.getText());
                        int yCoordinate = Integer.parseInt(yField.getText());
                        int radius = Integer.parseInt(radiusField.getText());
                        Point center = new Point();
                        center.setCoordinateX(xCoordinate);
                        center.setCoordinateY(yCoordinate);
                        try {
                            segment = controller.generateSecondOrderLine(modeName, center, radius);
                            chart.setPoints(segment);
                            step = segment.size() - 1;
                        } catch (IncorrectDataException e) {
                            logger.error(e);
                        }
                    }
                });
                break;
            case ELLIPSE:
                Label aLabel = new Label(A_TITLE);
                Label bLabel = new Label(B_TITLE);

                TextField aField = new TextField();
                TextField bField = new TextField();

                fields.add(aLabel, 0, 2);
                fields.add(aField, 1, 2);
                fields.add(bLabel, 0, 3);
                fields.add(bField, 1, 3);

                createButton.setOnAction((ActionEvent event) -> {
                    boolean isCorrect = true;
                    for (TextField textField : textFields) {
                        if (!textField.getText().matches(TEXT_FIELD)) {
                            isCorrect = false;
                        }
                    }
                    if (isCorrect) {
                        int xCoordinate = Integer.parseInt(xField.getText());
                        int yCoordinate = Integer.parseInt(yField.getText());
                        int a = Integer.parseInt(aField.getText());
                        int b = Integer.parseInt(bField.getText());
                        Point center = new Point();
                        center.setCoordinateX(xCoordinate);
                        center.setCoordinateY(yCoordinate);
                        try {
                            segment = controller.generateSecondOrderLine(modeName, center, a, b);
                            chart.setPoints(segment);
                            step = segment.size() - 1;
                        } catch (IncorrectDataException e) {
                            logger.error(e);
                        }
                    }
                });
                break;
            default:
                throw new IncorrectDataException("modeName has invalid value");
        }
        if (parameterPane != null) {
            menuPane.getChildren().remove(parameterPane);
        }
        parameterPane = new FlowPane(Orientation.VERTICAL, GAP, GAP);
        parameterPane.getChildren().addAll(parametersLabel, fields, createButton, debugLabel, backButton, forwardButton);
        menuPane.getChildren().addAll(parameterPane);
    }
}
