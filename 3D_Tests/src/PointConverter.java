import java.awt.*;

public class PointConverter {

    static int screenWidth;
    static int screenHeight;

    public PointConverter(int screenWidth, int screenHeight) {
        PointConverter.screenWidth = screenWidth;
        PointConverter.screenHeight = screenHeight;
    }

    public static Point worldToRasterConversion(Vector4 p) {
        Vector4 translatedPoint = Matrix4x4.mult(p, World.GLOBAL_MATRIX);
        Vector4 normalizedPoint = Matrix4x4.mult(translatedPoint, Camera.projectionMatrix);

        normalizedPoint.x += 1.0;
        normalizedPoint.y += 1.0;

        normalizedPoint.x *= 0.5 * screenWidth;
        normalizedPoint.y *= 0.5 * screenHeight;

        return new Point((int)normalizedPoint.x, (int)normalizedPoint.y);
    }
}
