import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Application;

import static javafx.application.Application.launch;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

public class BlockDrag extends Application {
    ResizableCanvas canvas;
    ArrayList<Block> blocks = new ArrayList<>();
    Block selected;
    double dx;
    double dy;

    private static final Color[] DEFAULT_COLORS;

    static {
        DEFAULT_COLORS = new Color[]{
                Color.BLACK,
                Color.BLUE,
                Color.CYAN,
                Color.DARK_GRAY,
                Color.GRAY,
                Color.GREEN,
                Color.LIGHT_GRAY,
                Color.MAGENTA,
                Color.ORANGE,
                Color.PINK,
                Color.RED,
                Color.WHITE,
                Color.YELLOW
        };
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        primaryStage.setScene(new Scene(mainPane));
        primaryStage.setTitle("Block Dragging");
        primaryStage.show();

        canvas.setOnMousePressed(e -> mousePressed(e));
        canvas.setOnMouseReleased(e -> mouseReleased(e));
        canvas.setOnMouseDragged(e -> mouseDragged(e));

        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            blocks.add(new Block(DEFAULT_COLORS[random.nextInt(DEFAULT_COLORS.length)],
                    random.nextDouble() * 1820,
                    random.nextDouble() * 980,
                    100, 100));
        }
        
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.draw((new Rectangle2D.Double()));
        Color c = graphics.getColor();
        for (Block block : blocks) {
            graphics.setColor(block.getColor());
            graphics.fill(block);
            graphics.setColor(Color.BLACK);
            graphics.draw(block);
        }
    }

    public static void main(String[] args) {
        launch(BlockDrag.class);
    }

    private void mousePressed(MouseEvent e) {
        for (Block block : blocks) {
            if (block.contains(e.getX(), e.getY())) {
                selected = block;
                dx = selected.getX() - e.getX();
                dy = selected.getY() - e.getY();
            }
        }
    }

    private void mouseReleased(MouseEvent e) {
        selected = null;
    }

    private void mouseDragged(MouseEvent e) {
        if (selected != null) {
            selected.setPos(e.getX() + dx, e.getY() + dy);
        }
        draw(new FXGraphics2D(canvas.getGraphicsContext2D()));
    }

}
