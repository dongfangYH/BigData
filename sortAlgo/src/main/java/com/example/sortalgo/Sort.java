package com.example.sortalgo;

/**
 * @author
 * @description
 * @date 2020-05-09 16:42
 **/
public class Sort {

    /**
     *  冒泡排序
     * 时间复杂度: o(n^2)
     * 空间复杂度: o(1)
     * 平均时间复杂度: o(n^2)
     * @param source
     * @return
     */
    public static final int[] bubbleSort(int[] source){
        doBubbleSort(source, 0, source.length - 1);
        return source;
    }

    private static final void doBubbleSort(int[] source, int start, int end){
        if (start < 0) start = 1;
        if (source.length - 1 < end) end = source.length - 1;
        if (source.length < 1 || (end - start) < 1)
            return;

        for (int x = start; x < end; x++){
            boolean flag = false;
            for (int y = start; y < end - x ; y++){
                if (source[y] > source[y+1]){
                    // swap to element
                    int h = source[y];
                    source[y] = source[y+1];
                    source[y+1] = h;
                    flag = true;
                }
            }
            if (!flag) break;
        }
    }

    /**
     * 插入排序
     * 时间复杂度: o(n^2)
     * 空间复杂度: o(1)
     * 平均时间复杂度: o(n^2)
     * @param source
     * @return
     */
    public static final int[] insertSort(int[] source){
        if (source.length > 1) {
            for (int x = 1; x <= source.length - 1; x++){
                int v = source[x];
                int y = x - 1;
                for (; y >= 0; y--){
                    if (source[y] > v){
                        source[y + 1] = source[y];
                    }else {
                        break;
                    }
                }
                source[y + 1] = v;
            }
        }
        return source;
    }

    /**
     * 选择排序
     * 每次会从未排序区间中找到最小的元素，将其放到已排序区间的末尾
     * 时间复杂度: o(n^2)
     * 空间复杂度: o(1)
     * 平均时间复杂度: o(n^2)
     * @param source
     * @return
     */
    public static final int[] selectSort(int[] source){

        for (int x = 0; x < source.length; x++){
            int min = Integer.MAX_VALUE;
            int minIdx = -1;
            for (int y = x; y < source.length; y++){
                if (min > source[y]){
                    min = source[y];
                    minIdx = y;
                }
            }
            source[minIdx] = source[x];
            source[x] = min;
        }

        return source;
    }

    public static void main(String[] args){
        int[] test = {6, 5, 4, 3, 2, 1};
        test = selectSort(test);
        System.out.println(test);
    }
}
