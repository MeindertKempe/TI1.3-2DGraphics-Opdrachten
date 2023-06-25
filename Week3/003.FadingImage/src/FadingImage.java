import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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

public class FadingImage extends Application {
    private ResizableCanvas canvas;
    
    @Override
    public void start(Stage stage) throws Exception {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;
            @Override
            public void handle(long now) {
		if(last == -1)
                    last = now;
		update((now - last) / 1000000000.0);
		last = now;
		draw(g2d);
            }
        }.start();

        img1 = ImageIO.read(FadingImage.class.getResource("img/img1.png"));
        img2 = ImageIO.read(FadingImage.class.getResource("img/img2.jpg"));

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Fading image");
        stage.show();
        draw(g2d);

    }

    private BufferedImage img1;
    private BufferedImage img2;

    
    public void draw(FXGraphics2D graphics) {
        AffineTransform a = new AffineTransform();
        graphics.setTransform(a);
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int)canvas.getWidth(), (int)canvas.getHeight());


        graphics.drawImage(img1, 0, 0, null);
        if (blend != 0) {
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, blend));
            graphics.drawImage(img2, 0, 0, null);
        }
    }

    float blend = 0;
    double wait = 0;

    public void update(double deltaTime) {
        if (wait >= 0) {
            wait -= deltaTime;
            return;
        }
        blend += (deltaTime/5);

        if (blend >= 1.0) {
            blend = 0;
            wait = 2;
            BufferedImage temp = img1;
            img1 = img2;
            img2 = temp;
        }
    }

    public static void main(String[] args) {
        launch(FadingImage.class);
    }

}
