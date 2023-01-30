import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

public class House extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Canvas canvas = new Canvas(1920, 1080);
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
        primaryStage.setScene(new Scene(new Group(canvas)));
        primaryStage.setTitle("House");
        primaryStage.show();
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setStroke(new BasicStroke(2));
        graphics.draw(new Line2D.Double(150, 50, 50, 190));
        graphics.draw(new Line2D.Double(150, 50, 250, 190));
        graphics.draw(new Line2D.Double(50, 190, 50, 320));
        graphics.draw(new Line2D.Double(250, 190, 250, 320));
        graphics.draw(new Line2D.Double(50, 320, 250, 320));
        graphics.setStroke(new BasicStroke(1));
        graphics.draw(new Rectangle2D.Double(80, 240, 50, 80));
        graphics.draw(new Rectangle2D.Double(150, 220, 90, 60));
    }



    public static void main(String[] args) {
        launch(House.class);
    }

}
