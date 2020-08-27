import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class Cube {

    Matrix4x4 localTransform;

    Pgon[] faces = new Pgon[6];

    public Cube(double xpos, double ypos, double zpos) {

        localTransform = new Matrix4x4(new Vector4[] {new Vector4(1, 0, 0, 0),
                                                      new Vector4(0, 1, 0, 0),
                                                      new Vector4(0, 0, 1, 0),
                                                      new Vector4(xpos, ypos, zpos, 1)});



        faces[0] = new Pgon(Color.red, new Vector4(0, 0, 0, 1), new Vector4(0, 1, 0, 1), new Vector4(1, 1, 0, 1), new Vector4(1, 0, 0, 1));
        faces[1] = new Pgon(Color.green, new Vector4(1, 0, 0, 1), new Vector4(1, 1, 0, 1), new Vector4(1, 1, 1, 1), new Vector4(1, 0, 1, 1));
        faces[2] = new Pgon(Color.blue, new Vector4(1, 0, 1, 1), new Vector4(0, 0, 1, 1), new Vector4(0, 0, 0, 1), new Vector4(1, 0, 0, 1));
        faces[3] = new Pgon(Color.white, new Vector4(0, 0, 0, 1), new Vector4(0, 0, 1, 1), new Vector4(0, 1, 1, 1), new Vector4(0, 1, 0, 1));
        faces[4] = new Pgon(Color.cyan, new Vector4(0, 0, 1, 1), new Vector4(1, 0, 1, 1), new Vector4(1, 1, 1, 1), new Vector4(0, 1, 1, 1));
        faces[5] = new Pgon(Color.magenta, new Vector4(0, 1, 1, 1), new Vector4(1, 1, 1, 1), new Vector4(1, 1, 0, 1), new Vector4(0, 1, 0, 1));
    }

    public void render(Graphics2D g) {
        for (Pgon poly : faces) {
            poly.render(g);
        }
    }

    public void rotateBy(double x, double y, double z) {
        if (x != 0) {
            Matrix4x4 rotationMatrix = new Matrix4x4(new Vector4[] {new Vector4(1, 0, 0, 0),
                                                                    new Vector4(0, Math.cos(Math.toRadians(x)), -Math.sin(Math.toRadians(x)), 0),
                                                                    new Vector4(0, Math.sin(Math.toRadians(x)), Math.cos(Math.toRadians(x)), 0),
                                                                    new Vector4(0, 0, 0, 1)});
            for (Pgon poly : faces) {
                for (int i = 0; i < poly.points.length; i ++) {
                    Vector4 newPoint = Matrix4x4.mult(poly.points[i], rotationMatrix);
                    poly.points[i] = newPoint;
                }
            }
        }

        if (y != 0) {
            Matrix4x4 rotationMatrix = new Matrix4x4(new Vector4[] {new Vector4(Math.cos(Math.toRadians(y)), 0, Math.sin(Math.toRadians(y)), 0),
                                                                    new Vector4(0, 1, 0, 0),
                                                                    new Vector4(-Math.sin(Math.toRadians(y)), 0, Math.cos(Math.toRadians(y)), 0),
                                                                    new Vector4(0, 0, 0, 1)});
            for (Pgon poly : faces) {
                for (int i = 0; i < poly.points.length; i ++) {
                    Vector4 newPoint = Matrix4x4.mult(poly.points[i], rotationMatrix);
                    poly.points[i] = newPoint;
                }
            }
        }

        if (z != 0) {
            Matrix4x4 rotationMatrix = new Matrix4x4(new Vector4[] {new Vector4(Math.cos(Math.toRadians(z)), -Math.sin(Math.toRadians(z)), 0, 0),
                                                                    new Vector4(Math.sin(Math.toRadians(z)), Math.cos(Math.toRadians(z)), 0, 0),
                                                                    new Vector4(0, 0, 1, 0),
                                                                    new Vector4(0, 0, 0, 1)});
            for (Pgon poly : faces) {
                for (int i = 0; i < poly.points.length; i ++) {
                    Vector4 newPoint = Matrix4x4.mult(poly.points[i], rotationMatrix);
                    poly.points[i] = newPoint;
                }
            }
        }
    }
}
