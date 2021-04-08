package com.example.algo.sort;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-04-26 08:56
 **/
public class MergeSort {

    private static final void sort(int[] arr){
        if (arr.length <= 1){
            return;
        }
        int[] tmp = new int[arr.length];
        sort(0, arr.length - 1, arr, tmp);
    }

    private static final void sort(int start, int end, int[] arr, int[] tmp){
        if (start == end) return;

        int mid = (end - start) / 2 + start;
        // left
        sort(start, mid, arr, tmp);
        // right
        sort(mid+1, end, arr, tmp);

        // merge left and right
        merge(start, mid, end, arr, tmp);
    }

    private static void merge(int start, int mid, int end, int[] arr, int[] tmp) {
        int idxL = start;
        int idxR = mid + 1;
        for (int i = start; i <= end; i++){
            if (idxL > mid){
                tmp[i] = arr[idxR++];
                continue;
            }
            if (idxR > end){
                tmp[i] = arr[idxL++];
                continue;
            }

            if (arr[idxL] > arr[idxR]){
                tmp[i] = arr[idxR++];
            }else {
                tmp[i] = arr[idxL++];
            }
        }

        for (int i = start; i <= end; i++){
            arr[i] = tmp[i];
        }
    }

    public static void main(String[] args){
        int[] arr = {9,1,7,6,2,4,3,2,3};
        sort(arr);
        System.out.println("finished.");
    }
}
