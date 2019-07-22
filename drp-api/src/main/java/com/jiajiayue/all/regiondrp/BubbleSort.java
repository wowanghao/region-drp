package com.jiajiayue.all.regiondrp;

import java.util.Arrays;

/**
 * @author WangHao
 * @date 2019/7/19 11:45
 */
public class BubbleSort {

    public static void main(String[] args) {
        int[] arry = {342, 45, 76, 1, 34, 657, 57};
        System.out.println(Arrays.toString(arry));
        bubbuleSort(arry);
        System.out.println(Arrays.toString(arry));
    }

    public static void bubbuleSort(int[] arry) {

        for (int i = 0; i < arry.length - 1; i++) {
            for (int j = 0; j < arry.length - 1 - i; j++) {
                int temp = 0;
                if (arry[j + 1] < arry[j]) {
                    temp = arry[j + 1];
                    arry[j + 1] = arry[j];
                    arry[j] = temp;
                }
            }
        }
    }
}
