package by.bsuir.graphicseditor.view;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class MainWindow extends Application {
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
    private static final int WIDTH_WINDOW = 400;
    private static final int HEIGHT_WINDOW = 400;
    private static final int X_COORDINATE = 400;
    private static final int Y_COORDINATE = 100;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(TITLE);
        stage.setWidth(WIDTH_WINDOW);
        stage.setHeight(HEIGHT_WINDOW);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

        Label label = new Label("Hello");
        Button button = new Button("Click");

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                button.setText("Clicked");
            }
        });

        Pane modePane=createModePanel();
        Chart chart=new Chart();

        BorderPane root=new BorderPane();
        root.setCenter(chart);

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    private Pane createModePanel() {
        Label modeLabel=new Label(MODE_TITLE);

        RadioButton modeDDA = new RadioButton(DDA_TITLE);
        modeDDA.fire();
        RadioButton modeBresenham = new RadioButton(BRESENHAM_TITLE);
        RadioButton modeWu = new RadioButton(WU_TITLE);

        ToggleGroup modeGroup=new ToggleGroup();
        modeDDA.setToggleGroup(modeGroup);
        modeBresenham.setToggleGroup(modeGroup);
        modeWu.setToggleGroup(modeGroup);

        modeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                RadioButton newValue=(RadioButton)t1;
                //etc
            }
        });

        FlowPane modePanel=new FlowPane(Orientation.VERTICAL, 10,10);
        modePanel.setPadding(new Insets(10));
        modePanel.getChildren().addAll(modeLabel, modeDDA, modeBresenham, modeWu);
        modePanel.setMaxHeight(Double.MAX_VALUE);
        modePanel.setMaxWidth(Double.MAX_VALUE);

        return modePanel;
    }
}