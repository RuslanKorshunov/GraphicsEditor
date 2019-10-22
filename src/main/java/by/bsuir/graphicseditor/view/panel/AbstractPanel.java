package by.bsuir.graphicseditor.view.panel;

import by.bsuir.graphicseditor.view.Chart;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

public abstract class AbstractPanel extends BorderPane {
    private static final int GAP = 10;
    protected static final String MODE_TITLE = "Modes";

    protected Chart chart;
    protected Pane menuPane;

    public AbstractPanel() {
        chart = new Chart();
        menuPane = new FlowPane(Orientation.VERTICAL, GAP, GAP);
        menuPane.setPadding(new Insets(GAP));
        menuPane.setMaxHeight(Double.MAX_VALUE);
        menuPane.setMaxWidth(Double.MAX_VALUE);
        setCenter(chart);
        setLeft(menuPane);
    }
}
