import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

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

public class MovingCharacter extends Application {
    private ResizableCanvas canvas;
    private static final int TILE_SIZE = 64;

    private HashMap<State, BufferedImage[]> sprites;
    private Character character;

    @Override
    public void start(Stage stage) throws Exception {
        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());

        canvas.setOnMouseClicked(e -> {
            character.setState(State.JUMPING);
            character.setSpeed(0);
            character.setAccel(70);
            character.setSprites(sprites.get(State.JUMPING));
        });

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

        sprites = new HashMap<>();

        BufferedImage image = ImageIO.read(MovingCharacter.class.getResource("images/sprite.png"));
        sprites.put(State.WALKING, getImageRow(image, 4, 0, 8));
        sprites.put(State.JUMPING, getImageRow(image, 5, 2, 6));
        sprites.put(State.STAND, getImageRow(image, 8, 0, 1));

        character = new Character(sprites.get(State.WALKING), 0, 0);
        character.setState(State.WALKING);
        character.setSpeed(100);

        stage.setScene(new Scene(mainPane));
        stage.setTitle("Moving Character");
        stage.show();
        draw(g2d);
    }

    private BufferedImage[] getImageRow(BufferedImage image, int row, int startImage, int imageCount) {
        BufferedImage[] images = new BufferedImage[imageCount];
        for (int i = startImage; i < imageCount + startImage; i++) {
            images[i - startImage] = image.getSubimage(i * TILE_SIZE, row * TILE_SIZE, TILE_SIZE, TILE_SIZE);
        }
        return images;
    }

    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());
        graphics.translate(0, canvas.getHeight() / 2);

        graphics.drawImage(character.getSprite(), (int) character.getX(), (int) character.getY(), null);
    }


    private double change = 0;

    public void update(double deltaTime) {
        change += deltaTime;
        if (change >= 0.3) {
            change = 0;
            character.nextSprite();
        }

        if (character.getState() == State.WALKING) character.setX(character.getX() + character.getSpeed() * deltaTime);
        if (character.getState() == State.JUMPING) {
            character.setY(character.getY() - character.getSpeed() * deltaTime);
            character.setSpeed(character.getSpeed() + character.getAccel() * deltaTime);
            character.setAccel(character.getAccel() - 2);
            System.out.println(character.getAccel());
            System.out.println(character.getY());
            if (character.getY() > 0) {
                character.setState(State.WALKING);
                character.setSpeed(100);
                character.setAccel(0);
                character.setY(0);
                character.setSprites(sprites.get(State.WALKING));
            }
        }

    }

    public static void main(String[] args) {
        launch(MovingCharacter.class);
    }

    private enum State {
        WALKING,
        JUMPING,
        STAND,
    }

    private static class Character {
        private BufferedImage[] sprites;
        private int spriteIndex;
        private double x;
        private double y;
        private State state;
        private double speed;
        private double accel;

        public Character(BufferedImage[] sprites, double x, double y) {
            this.sprites = sprites;
            spriteIndex = 0;
            this.x = x;
            this.y = y;
        }

        public double getAccel() {
            return accel;
        }

        public void setAccel(double accel) {
            this.accel = accel;
        }

        public double getSpeed() {
            return speed;
        }

        public void setSpeed(double speed) {
            this.speed = speed;
        }

        public BufferedImage getSprite() {
            return sprites[spriteIndex];
        }

        public void nextSprite() {
            spriteIndex++;
            if (spriteIndex >= sprites.length) spriteIndex = 0;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public void setSprites(BufferedImage[] sprites) {
            this.sprites = sprites;
            spriteIndex = 0;
        }

        public State getState() {
            return state;
        }

        public void setState(State state) {
            this.state = state;
        }

        public void setX(double x) {
            this.x = x;
        }

        public void setY(double y) {
            this.y = y;
        }


    }
}
