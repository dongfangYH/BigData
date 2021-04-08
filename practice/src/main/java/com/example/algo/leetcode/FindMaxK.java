package com.example.algo.leetcode;

/**
 * @author yuanhang.liu@tcl.com
 * @description [3, 2, 1, 4, 5, 6]
 *              [1, 0, 1, 1, 1]
 *              [1, 1, 1, 1, 1]
 *              [1, 2, 3, 5, 6]
 *              [110, 101, 121, 311]
 *              [0, 1, 0, 1, 0, 1]
 * @date 2020-11-26 09:02
 **/
public class FindMaxK {

    public static void main(String[] args){
        int[] data = {1, 0, 1, 1, 1};
        int k = 5;
        System.out.println(doFindMaxK(data, k));
    }

    private static int doFindMaxK(int[] data, int k) {
        if (data.length < k){
            throw new IllegalArgumentException("illegal k.");
        }

        int lmx = 0;
        int rmx = data.length - 1;

        int piv = findPiv(data, lmx, rmx);

        while ((data.length - piv) != k){
            if ((data.length - piv) > k){
                piv = findPiv(data, piv+ 1, rmx);
            }

            if ((data.length - piv) < k){
                piv = findPiv(data, lmx, piv - 1);
            }
        }
        return data[piv];
    }

    /**
     * choose rmx index element as piv value, then return the right index of the piv value
     * @param data the original data
     * @param lmx left index
     * @param rmx right index
     * @return
     */
    public static int findPiv(int[] data, int lmx, int rmx){
        if (rmx - lmx <= 1){
            if (data[rmx] < data[lmx]){
                swap(data, lmx, rmx);
            }
            return rmx;
        }

        int l = lmx, r = rmx - 1;
        int piv = rmx;
        while (r > l){
            while (data[l] < data[piv] && r > l){
                l++;
            }

            while (data[r] > data[piv] && r > l){
                r--;
            }

            if (r > l){
                swap(data, r, l);
                l++;
                r--;
            }

        }

        if (data[l] > data[piv]){
            swap(data, l, piv);
            piv = l;
        }

        return piv;
    }

    /**
     * swap two index's elements
     * @param data original data
     * @param r  right index
     * @param l  left index
     */
    private static void swap(int[] data, int r, int l) {
        int tmp = data[r];
        data[r] = data[l];
        data[l] = tmp;
    }
}
