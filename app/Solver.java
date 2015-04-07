package app;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Created by isoard on 07/04/15.
 */
public class Solver {
    RealMatrix gauche;
    RealMatrix droite;
    RealMatrix solution;

    public Solver() {
        gauche = MatrixUtils.createRealIdentityMatrix(2);
    }

    public RealMatrix getPosition(double xp, double yp, double theta0, double theta1) {
        setMatrixRight(xp, yp, theta0, theta1);
        setMatrixLeft(theta0, theta1);
        invertMatrixLeft();
        multiplyRightLeft();
        return solution;
    }

    private void multiplyRightLeft() {
        solution = droite.multiply(gauche);
    }

    private void invertMatrixLeft() {
        //RealMatrix gaucheInverse = new LUDecomposition(gauche).getSolver().getInverse();
        gauche = MatrixUtils.inverse(gauche);
    }

    private void setMatrixLeft(double theta0, double theta1) {
        double[][] matrixData = { {Math.sin(theta0), -Math.cos(theta0)} , {Math.sin(theta1), -Math.cos(theta1)} };
        gauche = MatrixUtils.createRealMatrix(matrixData);
    }

    private void setMatrixRight(double xp, double yp, double theta0, double theta1) {
        double[][] matrixData = { {Math.sin(theta0)*xp-Math.cos(theta0)*yp, Math.sin(theta1)*xp-Math.cos(theta1)*yp} };
        droite = MatrixUtils.createRealMatrix(matrixData);
    }
}
