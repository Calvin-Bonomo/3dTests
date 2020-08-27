import javax.swing.*;
import java.awt.*;

public class Main {
    static JFrame f;
    static Graphics2D g;

    public static void main(String[] args) {
        f = new JFrame("3D Test");

        JPanel p = (JPanel) f.getContentPane();
        p.setSize(400, 400);
        p.setLayout(null);

        Canvas c = new Canvas();
        c.setSize(400, 400);
        c.setIgnoreRepaint(true);
        p.add(c);

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(true);
        f.setUndecorated(false);
        f.pack();
        f.setSize(400, 400);
        f.setVisible(true);

        g = (Graphics2D) c.getGraphics();

        PointConverter converter = new PointConverter(400, 400);
        Camera camera = new Camera(90);

        render();
    }

    static void render() {
        g.clearRect(0, 0, 400, 400);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 400, 400);

        Cube cube = new Cube(0, 0, 0);
        cube.rotateBy(45, 0, 0);
        cube.render(g);
//        Pgon p = new Pgon(new Vector4(0, 0, 1, 1), new Vector4(100, 0, 1, 1), new Vector4(100, 100, 1, 1), new Vector4(0, 100, 1, 1));
//        p.render(g);
    }
}

class World {
    public static final Matrix4x4 GLOBAL_MATRIX = new Matrix4x4(new Vector4[] {new Vector4(1, 0, 0, 0),
                                                                               new Vector4(0, 1, 0, 0),
                                                                               new Vector4(0, 0, 1, 0),
                                                                               new Vector4(0, 0, 4, 1)});
}

class Camera {
    double n = 0.1;
    double f = 1000;

    public static Matrix4x4 projectionMatrix;

    public Camera(double fovAngle) {
        double s = 1.0/Math.tan((fovAngle/2) * (Math.PI/180));

        projectionMatrix = new Matrix4x4(new Vector4[] {new Vector4(s, 0, 0, 0),
                                                        new Vector4(0, s, 0, 0),
                                                        new Vector4(0, 0, f/(f-n), 1),
                                                        new Vector4(0, 0, (-f*n)/(f-n), 0)});
    }
}