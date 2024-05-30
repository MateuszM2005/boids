package com.company;
import java.util.ArrayList;
public class Vector {
    double xMagnitude;
    double yMagnitude;
    double weight;

    Vector(double xMagnitude, double yMagnitude, double weight){
        this.xMagnitude = xMagnitude;
        this.yMagnitude = yMagnitude;
        this.weight = weight;
    }

    public static double[] combineVectors(ArrayList<Vector> list){
        double xChange = 0;
        double yChange = 0;
        double sum = 0;
        for(Vector vector : list){
            sum += vector.weight;
        }
        for (Vector vector : list) {
            xChange += vector.xMagnitude * vector.weight/sum;
            yChange += vector.yMagnitude * vector.weight/sum;
        }
        return new double[]{xChange,yChange};
    }
}
