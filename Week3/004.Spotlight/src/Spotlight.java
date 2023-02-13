import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Spotlight extends Application {
    private ResizableCanvas canvas;
    private BufferedImage image;
    private double x = 500;
    private double y = 500;

    @Override
    public void start(Stage stage) throws Exception {
        image = ImageIO.read(Paths.get("Week3/004.Spotlight/res/1.png").toFile());

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        canvas.setOnMouseDragged(e -> {
            x = e.getX();
            y = e.getY();
            draw(g2d);
        });

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Spotlight");
        stage.show();
        draw(g2d);
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Shape shape = new Ellipse2D.Double(x - 100, y - 100, 200, 200);
        Shape clip = graphics.getClip();
        graphics.setClip(shape);
        graphics.drawImage(image, new AffineTransform(), null);
        graphics.setClip(clip);

    }

    public void init() {

    }

    public void update(double deltaTime) {
    }

    public static void main(String[] args) {
        launch(Spotlight.class);
    }

}
