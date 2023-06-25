import java.awt.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
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

public class Screensaver extends Application {
    private ResizableCanvas canvas;
    private Point[] points;
    private LinkedList<Point[]> history;
    private final int SPEED = 200;
    private final int HIST_SIZE = 50;

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
                if (last == -1)
                    last = now;
                update((now - last) / 1000000000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        int count = 4;
        points = new Point[count];
        for (int i = 0; i < count; i++) {
            points[i] = new Point((int) canvas.getHeight(), (int) canvas.getWidth());
        }
        history = new LinkedList<>();


        stage.setScene(new Scene(mainPane));
        stage.setTitle("Screensaver");
        stage.show();
        draw(g2d);
    }


    public void draw(FXGraphics2D graphics) {
        graphics.setTransform(new AffineTransform());
        graphics.setBackground(Color.white);
        graphics.clearRect(0, 0, (int) canvas.getWidth(), (int) canvas.getHeight());

//        drawPoints(graphics, points);
        for (Point[] points : history) {
            drawPoints(graphics, points);
        }
//        for (int i = 1; i < points.length; i++) {
//            graphics.drawLine((int) points[i - 1].getX(), (int) points[i - 1].getY(), (int) points[i].getX(), (int) points[i].getY());
//        }
//        graphics.drawLine((int) points[0].getX(), (int) points[0].getY(), (int) points[points.length - 1].getX(), (int) points[points.length - 1].getY());
    }

    private void drawPoints(FXGraphics2D graphics, Point[] points) {
        for (int i = 1; i < points.length; i++) {
            graphics.drawLine((int) points[i - 1].getX(), (int) points[i - 1].getY(), (int) points[i].getX(), (int) points[i].getY());
        }
        graphics.drawLine((int) points[0].getX(), (int) points[0].getY(), (int) points[points.length - 1].getX(), (int) points[points.length - 1].getY());
    }

    public void init() {

    }

    public void update(double deltaTime) {
        Point[] old = new Point[points.length];
        for (int i = 0; i < points.length; i++) {
            old[i] = points[i];

            double vx = old[i].getVx();
            double vy = old[i].getVy();

            if (old[i].getX() > canvas.getWidth() || old[i].getX() < 0) vx = -vx;
            if (old[i].getY() > canvas.getHeight() || old[i].getY() < 0) vy = -vy;

            points[i] = new Point(old[i].getX() + vx * deltaTime * SPEED, old[i].getY() + vy * deltaTime * SPEED, vx, vy);
        }
        history.add(old);
        if (history.size() > HIST_SIZE) history.poll();
    }

    public static void main(String[] args) {
        launch(Screensaver.class);
    }

    private class Point {
        private double x, y;
        private double vx, vy;

        public Point(int w, int h) {
            Random r = new Random();
            this.x = r.nextInt(w);
            this.y = r.nextInt(h);
            this.vx = r.nextDouble() * 2 - 1;
            this.vy = r.nextDouble() * 2 - 1;
        }

        public Point(double x, double y, double vx, double vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }

        public double getX() {
            return x;
        }

        public double getY() {
            return y;
        }

        public double getVx() {
            return vx;
        }

        public double getVy() {
            return vy;
        }
    }

}
