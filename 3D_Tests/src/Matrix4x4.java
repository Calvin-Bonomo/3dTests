public class Matrix4x4 {
    public Vector4[] rows;

    public Matrix4x4(Vector4[] rows) {
        this.rows = rows;
    }

    public static Matrix4x4 subtractMats(Matrix4x4 mat1, Matrix4x4 mat2) {
        Vector4[] rows = new Vector4[4];
        for (int i = 0; i < 4; i ++) {
            Vector4 row = new Vector4(mat1.rows[i].x - mat2.rows[i].x,
                                      mat1.rows[i].y - mat2.rows[i].y,
                                      mat1.rows[i].z - mat2.rows[i].z,
                                      mat1.rows[i].w - mat2.rows[i].w);

            rows[i] = row;
        }

        return new Matrix4x4(rows);
    }

    public static Matrix4x4 inverse(Matrix4x4 mat) {
        double inverseDet = determinant(mat);
        Matrix4x4 newMat = adjugate(getMatrixOfCofactors(getMatrixOfMinors(mat)));

        Vector4 row1 = Vector4.scalarMult(inverseDet, newMat.rows[0]);
        Vector4 row2 = Vector4.scalarMult(inverseDet, newMat.rows[1]);
        Vector4 row3 = Vector4.scalarMult(inverseDet, newMat.rows[2]);
        Vector4 row4 = Vector4.scalarMult(inverseDet, newMat.rows[3]);

        return new Matrix4x4(new Vector4[] {row1, row2, row3, row4});
    }

    static Matrix4x4 adjugate(Matrix4x4 mat) {
        Vector4 row1 = new Vector4(mat.rows[0].x, mat.rows[1].x, mat.rows[2].x, mat.rows[3].x);
        Vector4 row2 = new Vector4(mat.rows[0].y, mat.rows[1].y, mat.rows[1].z, mat.rows[3].y);
        Vector4 row3 = new Vector4(mat.rows[0].z, mat.rows[2].y, mat.rows[2].z, mat.rows[3].z);
        Vector4 row4 = new Vector4(mat.rows[0].w, mat.rows[1].w, mat.rows[2].w, mat.rows[3].w);

        return new Matrix4x4(new Vector4[] {row1, row2, row3, row4});
    }

    static Matrix4x4 getMatrixOfCofactors(Matrix4x4 mat) {
        boolean startPositive = true;

        Vector4[] vecs = new Vector4[4];

        for (int i = 0; i < 4; i ++) {
            if (startPositive) {
                vecs[i] = new Vector4(mat.rows[i].x, mat.rows[i].y*-1, mat.rows[i].z, mat.rows[i].w*-1);
            } else {
                vecs[i] = new Vector4(mat.rows[i].x*-1, mat.rows[i].y, mat.rows[i].z*-1, mat.rows[i].w);
            }
            startPositive = !startPositive;
        }

        return new Matrix4x4(vecs);
    }

    static Matrix4x4 getMatrixOfMinors(Matrix4x4 mat) {

        Vector4[] points = new Vector4[4];

        for (int i = 0; i < 4; i ++) {
            int multRow = (i == 1)? 0 : 1;
            int topRow = (i == 0)? 2 : 0;
            int bottomRow = (i == 3)? 2 : 3;

            double a = mat.rows[multRow].y*((mat.rows[topRow].z*mat.rows[bottomRow].w) - (mat.rows[topRow].w*mat.rows[bottomRow].z))-
                       mat.rows[multRow].z*((mat.rows[topRow].y*mat.rows[bottomRow].w) - (mat.rows[topRow].w*mat.rows[bottomRow].y))+
                       mat.rows[multRow].w*((mat.rows[topRow].y*mat.rows[bottomRow].z) - (mat.rows[topRow].z*mat.rows[bottomRow].y));

            double b = mat.rows[multRow].x*((mat.rows[topRow].z*mat.rows[bottomRow].w) - (mat.rows[topRow].w*mat.rows[bottomRow].z))-
                       mat.rows[multRow].z*((mat.rows[topRow].x*mat.rows[bottomRow].w) - (mat.rows[topRow].w*mat.rows[bottomRow].x))+
                       mat.rows[multRow].w*((mat.rows[topRow].x*mat.rows[bottomRow].z) - (mat.rows[topRow].z*mat.rows[bottomRow].x));

            double c = mat.rows[multRow].x*((mat.rows[topRow].y*mat.rows[bottomRow].w) - (mat.rows[topRow].w*mat.rows[bottomRow].y))-
                       mat.rows[multRow].y*((mat.rows[topRow].x*mat.rows[bottomRow].w) - (mat.rows[topRow].w*mat.rows[bottomRow].x))+
                       mat.rows[multRow].w*((mat.rows[topRow].x*mat.rows[bottomRow].y) - (mat.rows[topRow].y*mat.rows[bottomRow].x));

            double d = mat.rows[multRow].x*((mat.rows[topRow].y*mat.rows[bottomRow].z) - (mat.rows[topRow].z*mat.rows[bottomRow].y))-
                       mat.rows[multRow].y*((mat.rows[topRow].x*mat.rows[bottomRow].z) - (mat.rows[topRow].z*mat.rows[bottomRow].x))+
                       mat.rows[multRow].z*((mat.rows[topRow].x*mat.rows[bottomRow].y) - (mat.rows[topRow].y*mat.rows[bottomRow].x));

            points[i] = new Vector4(a, b, c, d);
        }

        return new Matrix4x4(points);
    }

    static double determinant(Matrix4x4 mat) {
        double aDet = mat.rows[1].y*((mat.rows[2].z*mat.rows[3].w) - (mat.rows[2].w*mat.rows[3].z))-
                      mat.rows[1].z*((mat.rows[2].y*mat.rows[3].w) - (mat.rows[2].w*mat.rows[3].y))+
                      mat.rows[1].w*((mat.rows[2].y*mat.rows[3].z) - (mat.rows[2].z*mat.rows[3].y));
        double a = mat.rows[0].x * aDet;

        double bDet = mat.rows[1].x*((mat.rows[2].z*mat.rows[3].w) - (mat.rows[2].w*mat.rows[3].z))-
                      mat.rows[1].z*((mat.rows[2].x*mat.rows[3].w) - (mat.rows[2].w*mat.rows[3].x))+
                      mat.rows[1].w*((mat.rows[2].x*mat.rows[3].z) - (mat.rows[2].z*mat.rows[3].x));
        double b = mat.rows[0].y * bDet;

        double cDet = mat.rows[1].x*((mat.rows[2].y*mat.rows[3].w) - (mat.rows[2].w*mat.rows[3].y))-
                      mat.rows[1].y*((mat.rows[2].x*mat.rows[3].w) - (mat.rows[2].w*mat.rows[3].x))+
                      mat.rows[1].w*((mat.rows[2].x*mat.rows[3].y) - (mat.rows[2].y*mat.rows[3].x));
        double c = mat.rows[0].z * cDet;

        double dDet = mat.rows[1].x*((mat.rows[2].y*mat.rows[3].z) - (mat.rows[2].z*mat.rows[3].y))-
                      mat.rows[1].y*((mat.rows[2].x*mat.rows[3].z) - (mat.rows[2].z*mat.rows[3].x))+
                      mat.rows[1].z*((mat.rows[2].x*mat.rows[3].y) - (mat.rows[2].y*mat.rows[3].x));
        double d = mat.rows[0].w * dDet;

        return (a - b + c - d);
    }

    static Vector4 mult(Vector4 vec, Matrix4x4 mat) {
        double newX = vec.x * mat.rows[0].x + vec.y * mat.rows[1].x + vec.z * mat.rows[2].x + vec.w * mat.rows[3].x;
        double newY = vec.x * mat.rows[0].y + vec.y * mat.rows[1].y + vec.z * mat.rows[2].y + vec.w * mat.rows[3].y;
        double newZ = vec.x * mat.rows[0].z + vec.y * mat.rows[1].z + vec.z * mat.rows[2].z + vec.w * mat.rows[3].z;
        double newW = vec.x * mat.rows[0].w + vec.y * mat.rows[1].w + vec.z * mat.rows[2].w + vec.w * mat.rows[3].w;

        if (newW != 0) {
            newX /= newW; newY /= newW; newZ /= newW;
        }

        return new Vector4(newX, newY, newZ, newW);
    }
}
