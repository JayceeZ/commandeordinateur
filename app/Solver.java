package app;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * Created by isoard on 07/04/15.
 */
public class Solver {
    int count;

    RealMatrix gauche;
    RealMatrix droite;
    RealMatrix solution;

    double[][] matrixDroiteData;
    double[][] matrixGaucheData;

    public Solver() {
        gauche = MatrixUtils.createRealIdentityMatrix(4);
        matrixGaucheData = new double[4][4];
        matrixDroiteData = new double[4][1];
        count = 0;
    }

    public double[] getPosition(double xp, double yp, double theta, double t) {
        setMatrixLeft(theta, t);
        setMatrixRight(xp, yp, theta);
        if (count < 4) {
            double[] result = {0, 0, 0, 0};
            count++;
            return result;
        }
        invertMatrixLeft();
        multiplyRightLeft();
        double[] result = {solution.getEntry(0, 0) + solution.getEntry(1, 0) * t, solution.getEntry(2, 0) + solution.getEntry(3, 0) * t,
                solution.getEntry(1, 0), solution.getEntry(3, 0)};
        return result;
    }

    private void displayMatrix(RealMatrix matrice) {
        for (int i = 0; i < matrice.getRowDimension(); i++) {
            System.out.print("[");
            for (int j = 0; j < matrice.getColumnDimension(); j++) {
                System.out.print(matrice.getEntry(i, j) + ",");
            }
            System.out.println("]");
        }
    }

    private void multiplyRightLeft() {
        solution = gauche.multiply(droite);
    }

    private void invertMatrixLeft() {
        //RealMatrix gaucheInverse = new LUDecomposition(gauche).getSolver().getInverse();
        gauche = MatrixUtils.inverse(gauche);
    }

    private void setMatrixLeft(double theta, double t) {
        double[][] matrixData = {
                {matrixGaucheData[1][0], matrixGaucheData[1][1], matrixGaucheData[1][2], matrixGaucheData[1][3]},
                {matrixGaucheData[2][0], matrixGaucheData[2][1], matrixGaucheData[2][2], matrixGaucheData[2][3]},
                {matrixGaucheData[3][0], matrixGaucheData[3][1], matrixGaucheData[3][2], matrixGaucheData[3][3]},
                {Math.sin(theta), t * Math.sin(theta), -Math.cos(theta), -t * Math.cos(theta)}
        };
        matrixGaucheData = matrixData;
        gauche = MatrixUtils.createRealMatrix(matrixGaucheData);
    }

    private void setMatrixRight(double xp, double yp, double theta) {
        double[][] matrixData = {
                {matrixDroiteData[1][0]},
                {matrixDroiteData[2][0]},
                {matrixDroiteData[3][0]},
                {Math.sin(theta) * xp - Math.cos(theta) * yp}
        };
        matrixDroiteData = matrixData;
        droite = MatrixUtils.createRealMatrix(matrixDroiteData);
    }
}
