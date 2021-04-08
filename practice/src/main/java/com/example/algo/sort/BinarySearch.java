package com.example.algo.sort;

/**
 * 二分法查找
 */
public class BinarySearch {

    public static final int search(int target, int[] arr){
        int start = 0, end = arr.length - 1;
        while (end >= start){
            int mid = (end + start)/2;
            if (arr[mid] > target){
                end = mid - 1;
            }else if (arr[mid] < target){
                start = mid + 1;
            }else {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args){
        int[] arr = {1, 2, 3, 4, 5, 6, 7, 8};
        System.out.println(search(7, arr));
    }
}
