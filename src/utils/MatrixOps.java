/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;
/**
 *
 * @author nawshad
 */
public class MatrixOps {
    public static double[][] MatrixMult(double[][] matrix, double factor){     
        for(int i=0; i<matrix.length; i++){
            for(int j=0; j<matrix[i].length; j++){
                 matrix[i][j]*=factor;
            }
        }
        return matrix;
    }    
}
