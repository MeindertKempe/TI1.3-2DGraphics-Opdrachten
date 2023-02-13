import java.awt.*;
import java.awt.font.GlyphVector;
import java.awt.geom.*;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class Rainbow extends Application {
    private ResizableCanvas canvas;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        stage.setScene(new Scene(mainPane));
        stage.setTitle("Rainbow");
        stage.show();
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

        Font font = graphics.getFont();

        /* Colouring this is hard. */
//        GlyphVector vector = font.createGlyphVector(graphics.getFontRenderContext(), "regenboog");
//        for (int i = 0; i < vector.getNumGlyphs(); i++) {
//            AffineTransform a = new AffineTransform();
//            a.scale(10, 10);
//            a.rotate((-0.5 * Math.PI) + (Math.PI / (vector.getNumGlyphs() - 1) * i));
//            vector.setGlyphTransform(i, a);
//        }
//
//        graphics.fill(vector.getOutline((float) canvas.getWidth() / 2, (float) canvas.getHeight() / 2));

        char[] chars = "regenboog".toCharArray();
        for (int i = 0; i < chars.length; i++) {
            GlyphVector vector = font.createGlyphVector(graphics.getFontRenderContext(), Character.toString(chars[i]));
            AffineTransform a = new AffineTransform();
            a.scale(10, 10);
            a.rotate((-0.5 * Math.PI) + (Math.PI / (chars.length - 1) * i));
            Rectangle2D bounds = vector.getLogicalBounds();
            a.translate(-bounds.getWidth() / 2, -(bounds.getHeight() / 2) - 7);

            Shape letter = a.createTransformedShape(vector.getOutline());
            letter = AffineTransform.getTranslateInstance(canvas.getWidth() / 2, canvas.getHeight() / 2).createTransformedShape(letter);
            
            graphics.setColor(Color.getHSBColor((1f / chars.length) * i, 1, 1));
            graphics.fill(letter);
            graphics.setColor(Color.black);
            graphics.draw(letter);
        }
    }


    public static void main(String[] args) {
        launch(Rainbow.class);
    }

}
