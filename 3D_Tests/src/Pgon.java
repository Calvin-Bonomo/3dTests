import java.awt.*;

public class Pgon {
    Vector4[] points;
    Color c;

    public Pgon(Color c, Vector4... points) {
        this.c = c;
        this.points = points;
    }

    public void render(Graphics2D g) {
        Polygon polygon = new Polygon();

        g.setColor(c);
        for (Vector4 p : points) {
            Point point = PointConverter.worldToRasterConversion(p);
            polygon.addPoint(point.x, point.y);
        }

        g.fillPolygon(polygon);
    }
}
