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

public class Mirror extends Application {
    ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Mirror");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        graphics.scale(1, -1);

        graphics.draw(new Line2D.Double(0, -canvas.getHeight() / 2, 0, canvas.getHeight() / 2));
        graphics.draw(new Line2D.Double(-canvas.getWidth() / 2, 0, canvas.getWidth() / 2, 0));

        graphics.draw(new Line2D.Double(-canvas.getWidth() / 2, 2.5 * (-canvas.getWidth() / 2), canvas.getWidth() / 2, 2.5 * (canvas.getWidth() / 2)));
        graphics.draw(new Rectangle2D.Double(-50, 100, 100, 100));
        AffineTransform a = graphics.getTransform();
        AffineTransform b = new AffineTransform(a);
        b.rotate(Math.atan(2.5));
        b.scale(1, -1);
        b.rotate(-Math.atan(2.5));
        graphics.setTransform(b);
        graphics.draw(new Rectangle2D.Double(-50, 100, 100, 100));
        graphics.setTransform(a);
    }


    public static void main(String[] args) {
        launch(Mirror.class);
    }

}
