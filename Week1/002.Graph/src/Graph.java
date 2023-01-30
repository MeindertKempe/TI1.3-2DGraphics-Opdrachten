import java.awt.*;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Graph extends Application {
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        canvas = new Canvas(1920, 1080);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Graph");
        primaryStage.show();
    }

    public void draw(FXGraphics2D graphics) {
        graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        graphics.scale(1, -1);

        graphics.drawLine(0, 0, 1000, 0);
        graphics.drawLine(0, 0, -1000, 0);
        graphics.drawLine(0, 0, 0, 1000);
        graphics.drawLine(0, 0, 0, -1000);

        double scale = 200;
        double resolution = 0.001;
        double x = -100 - resolution;
        double y = Math.pow(x, 3);
        for (; x < 100; x += resolution) {
            double lasty = y;
            y = Math.pow(x, 3);
            graphics.draw(new Line2D.Double((x - resolution) * scale, lasty * scale, x * scale, y * scale));
        }
    }

    public static void main(String[] args) {
        launch(Graph.class);
    }

}
