package com.sylfie.util;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        double[][] data = {
                {4,8,4,3},
                {4,6,3,6},
                {7,7,2,3}
        };

        System.out.println(Arrays.toString(calculateCentroid(data)));
    }

    public static double[] calculateCentroid(double[][] X_cluster) {
        int numPoints = X_cluster.length;
        int dimensions = X_cluster[0].length;

        double[] centroid = new double[dimensions];

        for (double[] point : X_cluster) {
            for (int i = 0; i < dimensions; i++) {
                centroid[i] += point[i];
            }
        }

        for (int i = 0; i < dimensions; i++) {
            centroid[i] /= numPoints;
        }

        return centroid;
    }
}
