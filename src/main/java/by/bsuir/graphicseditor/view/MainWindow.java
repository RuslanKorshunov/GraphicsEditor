package by.bsuir.graphicseditor.view;

import by.bsuir.graphicseditor.view.panel.AbstractPanel;
import by.bsuir.graphicseditor.view.panel.SecondOrderLinePanel;
import by.bsuir.graphicseditor.view.panel.SegmentPanel;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

public class MainWindow extends Application {
    private static final String TITLE = "Graphics Editor";
    private static final String SEGMENT_TITLE = "Segment";
    private static final String MENU_TITLE = "Menu";
    private static final String INFO_TITLE = "Info";
    private static final String CIRCLE_TITLE = "Circle";
    private static final int WIDTH_WINDOW = 500;
    private static final int HEIGHT_WINDOW = 600;
    private static final int GAP = 10;
    private AbstractPanel segmentPanel;
    private AbstractPanel secondOrderLinePanel;

    private BorderPane root;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() {
        root = new BorderPane();
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

        MenuBar menuBar = createMenuBar();
        root.setTop(menuBar);

        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    @NotNull
    private MenuBar createMenuBar() {
        MenuBar menuBar = new MenuBar();

        Menu mainMenu = new Menu(MENU_TITLE);

        MenuItem segmentItem = new MenuItem(SEGMENT_TITLE);
        segmentItem.setOnAction((ActionEvent event) -> {
            if (segmentPanel == null) {
                segmentPanel = new SegmentPanel();
            }
            root.setCenter(segmentPanel);
        });
        MenuItem circleItem = new MenuItem(CIRCLE_TITLE);
        circleItem.setOnAction((ActionEvent event) -> {
            if (secondOrderLinePanel == null) {
                secondOrderLinePanel = new SecondOrderLinePanel();
            }
            root.setCenter(secondOrderLinePanel);
        });
        MenuItem infoItem = new MenuItem(INFO_TITLE);
        infoItem.setOnAction((ActionEvent event) -> {
        });
        SeparatorMenuItem separator = new SeparatorMenuItem();

        mainMenu.getItems().addAll(segmentItem, circleItem, separator, infoItem);

        menuBar.getMenus().addAll(mainMenu);

        return menuBar;
    }
}