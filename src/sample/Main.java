package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.LinkedList;

public class Main extends Application {
    private Canvas can;
    private GraphicsContext gc;
    private final LinkedList<Point> line = new LinkedList<>();
    private Slider slider;
    private static final int RADIUS = 50;
    private double angle = 0;

    @Override
    public void init() {
        final Timeline tlDraw = new Timeline(new KeyFrame(Duration.millis(1000d / 60), e -> draw()));
        tlDraw.setCycleCount(Timeline.INDEFINITE);
        tlDraw.play();
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        Scene scene = new Scene(root, 700, 400);

        primaryStage.setTitle("Sine Wave");

        can = new Canvas(scene.getWidth(), scene.getHeight());
        gc = can.getGraphicsContext2D();

        root.getChildren().add(can);

        slider = new Slider(0, 2 * Math.PI, 0.1);
        slider.setMinWidth(700);
        slider.setMaxWidth(700);
        root.getChildren().add(slider);

        scene.widthProperty().addListener((observableValue, oldVal, newVal) -> {
            can.setWidth(newVal.doubleValue());
            slider.setMinWidth(newVal.doubleValue());
            slider.setMaxWidth(newVal.doubleValue());
        });
        scene.heightProperty().addListener((observableValue, oldVal, newVal) -> can.setHeight(newVal.doubleValue()));

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void draw() {
        gc.clearRect(0, 0, can.getWidth(), can.getHeight());
        gc.setLineWidth(1);
        gc.strokeOval(10, 150, 100, 100);

        final double x = RADIUS * Math.sin(angle) + 55;
        final double y = RADIUS * Math.cos(angle) + 200;

        gc.fillOval(x, y - 5, 10, 10);
        line.addFirst(new Point(200, y));

        gc.fillOval(200, y - 5, 10, 10);
        gc.strokeLine(0, y, can.getWidth(), y);

        drawPoints();
        angle += slider.getValue();
    }

    private void drawPoints() {
        Point lastPoint = line.getLast();
        for (int i = line.size() - 1; i >= 0; i--) {
            Point linePoint = line.get(i);
            linePoint.setX(linePoint.getX() + 5);

            if (linePoint.getX() > can.getWidth()) {
                line.remove(i);
                continue;
            }

            gc.strokeLine(lastPoint.getX(), lastPoint.getY(), linePoint.getX(), linePoint.getY());
            lastPoint = linePoint;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
