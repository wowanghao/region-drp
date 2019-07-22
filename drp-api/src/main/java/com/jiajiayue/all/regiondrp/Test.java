package com.jiajiayue.all.regiondrp;

import java.util.Arrays;


/**
 * @author WangHao
 * @date 2019/6/28 10:38
 */
public class Test extends Thread {

    public static void main(String args[]) throws InterruptedException {

        int[] arr = {40, 19, 344, 23, 100, 3, 95, 293, 29, 38, 44, 582, 39};
        int[] arr1 = {1, 2, 3, 4, 5, 6};
        System.out.println(Arrays.toString(arr));

        quickSort(arr);

        System.out.println(Arrays.toString(arr));
    }

    public static void quickSort(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        quickSort(arr, low, high);
    }

    public static void quickSort(int[] arr, int low, int high) {

        if (low < high) {
            int index = partition(arr, low, high);
            quickSort(arr, low, index - 1);
            quickSort(arr, index + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
        // 经常判断 low < high 原因是防止 int[] arr1 = {1, 2, 3, 4, 5, 6}; 如果high = low 的时候，不需要再换位置了
        int x = arr[low];
        while (low < high) {
            while (arr[high] > x && low < high) {
                high--;
            }
            if (low < high) {
                arr[low] = arr[high];
            }
            while (arr[low] < x && low < high) {
                low++;
            }
            if (low < high) {
                arr[high] = arr[low];
            }
        }
        arr[high] = x;
        return high;
    }

}

