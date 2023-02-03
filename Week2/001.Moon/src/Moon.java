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

public class Moon extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Moon");
        primaryStage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.translate(this.canvas.getWidth() / 2, this.canvas.getHeight() / 2);
        graphics.scale(1, -1);

        Area moon = new Area(new Ellipse2D.Double(-100, -100, 200, 200));
        Area cutout = new Area(new Ellipse2D.Double(-100 - 30, -100 + 30, 200, 200));
        Area crescent = new Area(moon);
        crescent.subtract(cutout);

        graphics.fill(crescent);

//        GeneralPath myShape = new GeneralPath();
//        myShape.moveTo(0, -100);
////        myShape.quadTo(200, -200, 0, -300);
//        myShape.curveTo(200, -100, 200, -300, 0, -300);
//        myShape.moveTo(0, -100);
//        myShape.curveTo(-200, -100, -200, -300, 0, -300);
//        myShape.closePath();
//        graphics.draw(myShape);

//        graphics.draw(new Arc2D.Double(canvas.getWidth()/2, canvas.getHeight()/2, 100, 100, 3, 300, Arc2D.OPEN));
    }


    public static void main(String[] args) {
        launch(Moon.class);
    }

}
