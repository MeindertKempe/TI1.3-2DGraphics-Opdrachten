import java.awt.*;
import java.awt.geom.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spiral extends Application {
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        canvas = new Canvas(1920, 1080);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("Spiral");
        primaryStage.show();
    }
    
    
    public void draw(FXGraphics2D graphics) {
        graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        graphics.scale(1, -1);

        double res = 0.001;
        double scale = 10;
        double x1, x2, y1, y2;
        for (double i = 0; i < Math.PI * 10; i += res) {
            x1 = fx(i);
            x2 = fx(i + res);
            y1 = fy(i);
            y2 = fy(i + res);
            graphics.draw(new Line2D.Double(x1 * scale, y1 * scale, x2 * scale, y2 * scale));
        }
    }

    private double fx(double t) {
        return t * Math.cos(t);
    }

    private double fy(double t) {
        return t * Math.sin(t);
    }

    public static void main(String[] args) {
        launch(Spiral.class);
    }

}
