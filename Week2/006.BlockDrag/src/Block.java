import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Block extends Rectangle2D.Double {
    private Color color;
    private double x;
    private double y;
    private double w;
    private double h;

    public Block(Color color, double x, double y, double w, double h) {
        this.color = color;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        setRect(x, y, w, h);
    }

    public void setPos(double x, double y) {
        this.x = x;
        this.y = y;
        setRect(x, y, w, h);
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}