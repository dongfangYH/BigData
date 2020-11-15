package com.example.leetcode;

public class Main {

    /**
     * [1,3,-1,-3,5,3,6,7]
     * 3
     * @param nums 8
     * @param k 3
     * @return
     */
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        for (int i = 0; i < result.length; i++){
            result[i] = Integer.MIN_VALUE;
        }
        int leftIndex = 0;
        int rightIndex = (k - 1) > 0 ? k - 1 : k;

        for (int i = 0; i < result.length; i++){
            int tempIndex = leftIndex;
            while (tempIndex <= rightIndex){
                result[i] = Math.max(result[i], nums[tempIndex]);
                tempIndex++;
            }
            leftIndex++;
            rightIndex++;
        }

        return result;

    }

    public static void main(String[] args) {

    }
}
