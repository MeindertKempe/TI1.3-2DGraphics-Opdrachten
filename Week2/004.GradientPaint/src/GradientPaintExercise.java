import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class GradientPaintExercise extends Application {
    private ResizableCanvas canvas;
    private Point2D.Double center;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        center = new Point2D.Double(canvas.getWidth() / 2, canvas.getHeight() / 2);
        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        canvas.setOnMouseDragged(e -> {
            center = new Point2D.Double(e.getX(), e.getY());
            draw(graphics);
        });
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("GradientPaint");
        primaryStage.show();
        draw(graphics);
    }


    public void draw(FXGraphics2D graphics) {
        RadialGradientPaint paint = new RadialGradientPaint(center, 200,
                new float[]{
                        0.1f,
                        0.2f,
                        0.3f,
                        0.4f,
                        0.5f,
                        0.6f,
                        0.7f,
                        0.8f
                },
                new Color[]{
                        Color.BLUE,
                        Color.CYAN,
                        Color.GREEN,
                        Color.MAGENTA,
                        Color.ORANGE,
                        Color.PINK,
                        Color.RED,
                        Color.YELLOW
                });
        graphics.setPaint(paint);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
    }


    public static void main(String[] args) {
        launch(GradientPaintExercise.class);
    }
}
