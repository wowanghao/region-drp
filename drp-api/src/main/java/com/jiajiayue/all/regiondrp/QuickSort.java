package com.jiajiayue.all.regiondrp;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author WangHao
 * @date 2019/7/16 16:38
 */
public class QuickSort {

    public static void main(String[] args) {

        int[] arry = {39, 38, 2, 40, 6, 7, 232, 43, 90};
        sort(arry);
        System.out.println(Arrays.toString(arry));
    }

    public static void sort(int[] arry) {

        int low = 0;
        int high = arry.length - 1;
        quickSort(arry, low, high);

    }

    public static void quickSort(int[] arry, int low, int high) {

        if (low < high) {
            int index = partition(arry, low, high);
            quickSort(arry, index + 1, high);
            quickSort(arry, low, high - 1);
        }
    }

    public static int partition(int[] arry, int low, int high) {
        int x = arry[low];
        while (low < high) {
            while (arry[high] > x && low < high) {
                high--;
            }
            if (low < high) {
                arry[low] = arry[high];
                low++;
            }
            while (arry[low] < x && low < high) {
                low++;
            }
            if (low < high) {
                arry[high] = arry[low];
                high--;
            }
        }
        arry[high] = x;
        return high;
    }
}
