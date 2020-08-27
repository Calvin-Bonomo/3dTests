public class Vector4 {
    public double x, y, z, w;

    public Vector4(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public static double combine(Vector4 vec) {
        return vec.x + vec.y + vec.z + vec.w;
    }

    public static Vector4 scalarMult(double scalar, Vector4 vec) {
        return new Vector4(scalar * vec.x, scalar * vec.y, scalar * vec.z, scalar * vec.w);
    }
}
