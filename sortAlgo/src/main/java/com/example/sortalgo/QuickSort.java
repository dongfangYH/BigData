package com.example.sortalgo;

import java.util.Random;

/**
 * @author yuanhang.liu@tcl.com
 * @description QuickSort
 * @date 2020-05-13 14:06
 **/
public class QuickSort {

    public static final int[] quickSort(int[] source){
        doQuickSort(source, 0, source.length - 1);
        return source;
    }

    private static final void doQuickSort(int[] source, int start, int end){
        if (end - start < 1) {
            return;
        }
        int piv = end;
        int ls = start, re = end - 1;

        for (;;){
            while (source[ls] <= source[piv] && ls < re){
                ls++;
            }

            while (source[re] > source[piv] && re > ls){
                re--;
            }

            //end condition
            if (ls >= re) {
                if (source[piv] < source[re]) {
                    int tmp = source[piv];
                    source[piv] = source[re];
                    source[re] = tmp;
                    piv = re;
                }
                break;
            }

            //swap
            int tmp = source[ls];
            source[ls] = source[re];
            source[re] = tmp;
        }

        doQuickSort(source, start, piv - 1);
        doQuickSort(source, piv, end);
    }

    public static void main(String[] args){
        Random r = new Random();
        while (true){
            int[] examples = new int[20];
            for (int i = 0; i < examples.length; i++){
                examples[i] = r.nextInt(100);
            }
            quickSort(examples);
            System.out.println("---");
        }

    }
}
