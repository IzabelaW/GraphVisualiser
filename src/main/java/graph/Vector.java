package graph;

/**
 * Created by izabelawojciak on 27/11/2018.
 */
public class Vector {

    private double X;
    private double Y;

    public Vector(double X, double Y) {
        this.X = X;
        this.Y = Y;
    }

    public void setX(double X) {
        this.X = X;
    }

    public void setY(double Y) {
        this.Y = Y;
    }

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public void add(Vector vector) {
        this.X += vector.getX();
        this.Y += vector.getY();
    }

    public void subtract(Vector vector) {
        this.X -= vector.getX();
        this.Y -= vector.getY();
    }

    public double norm() {
        return Math.sqrt(Math.pow(this.X,2) + Math.pow(this.Y,2));
    }

    public void multiplyByAScalar(double factor) {
        this.X *= factor;
        this.Y *= factor;
    }

    public void divideByAScalar(double factor) {
        this.X /= factor;
        this.Y /= factor;
    }
}
