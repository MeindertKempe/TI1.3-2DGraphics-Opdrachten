import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

public class Spirograph extends Application {

    private Button randomise;
    private Random random = new Random();
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        canvas = new Canvas(1920, 1080);

        VBox mainBox = new VBox();
        HBox topBar = new HBox();
        mainBox.getChildren().add(topBar);
        mainBox.getChildren().add(new Group(canvas));
        topBar.getChildren().add(randomise = new Button("Randomise"));
        randomise.setOnAction(e -> draw(new FXGraphics2D(canvas.getGraphicsContext2D())));


        FXGraphics2D graphics = new FXGraphics2D(canvas.getGraphicsContext2D());
        graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        graphics.scale(1, -1);
        draw(graphics);
        primaryStage.setScene(new Scene(mainBox));
        primaryStage.setTitle("Spirograph");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        //you can use Double.parseDouble(v1.getText()) to get a double value from the first textfield
        //feel free to add more textfields or other controls if needed, but beware that swing components might clash in naming
        clear(graphics);

        for (int i = 0; i < random.nextInt(5) + 1; i++) {
            spiroGraph(graphics);
        }

    }

    private void spiroGraph(FXGraphics2D graphics) {
        double a = random.nextInt(100) + 150;
        double b = random.nextInt(100);
        double c = random.nextInt(100) + 150;
        double d = random.nextInt(100);
        graphics.setColor(Color.getHSBColor(random.nextFloat(), 1, 1));

        double res = 0.001;
        double scale = 1;
        double x1, x2, y1, y2;
        for (double i = 0; i < Math.PI * 2; i += res) {
            x1 = fx(i, a, b, c, d);
            x2 = fx(i + res, a, b, c, d);
            y1 = fy(i, a, b, c, d);
            y2 = fy(i + res, a, b, c, d);
            Line2D.Double line = new Line2D.Double(x1 * scale, y1 * scale, x2 * scale, y2 * scale);
            graphics.draw(line);
        }
    }

    private void clear(FXGraphics2D graphics) {
        graphics.setBackground(Color.WHITE);
        graphics.clearRect((int) (0 - this.canvas.getWidth() / 2), (int) (0 - this.canvas.getHeight() / 2), (int) this.canvas.getWidth(), (int) this.canvas.getHeight());
        graphics.setBackground(Color.BLACK);
    }

    private double fx(double t, double a, double b, double c, double d) {
        return a * Math.cos(b * t) + c * Math.cos(d * t);
    }

    private double fy(double t, double a, double b, double c, double d) {
        return a * Math.sin(b * t) + c * Math.sin(d * t);
    }

    public static void main(String[] args) {
        launch(Spirograph.class);
    }

}
