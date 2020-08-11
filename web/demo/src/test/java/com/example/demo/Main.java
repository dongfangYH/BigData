package com.example.demo;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-07-01 16:55
 **/
public class Main {

    public static int[][] matrixPlus(int[][] left, int[][] right){

        if (left.length == 0 || right.length == 0 || left[0].length != right.length){
            throw new IllegalArgumentException("");
        }

        int[][] result = new int[left.length][right[0].length];

        for (int r = 0; r < result.length; r++){
            for (int c = 0; c < result[r].length; c++){
                int rs = 0;
                for (int x = 0; x < result[r].length; x++){
                    rs += left[r][x] * right[c][x];
                }
                result[r][c] = rs;
            }
        }

        return result;
    }


    public static void main(String[] args){

        int[][] a = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
        int[][] b = {
                {1, 0 ,0},
                {0, 1, 0},
                {0, 0, 1}
        };

        int[][] result = matrixPlus(a, b);
         System.out.println(result);
    }
}
