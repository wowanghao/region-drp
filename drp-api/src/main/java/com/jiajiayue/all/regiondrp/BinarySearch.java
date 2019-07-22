package com.jiajiayue.all.regiondrp;

import java.util.Arrays;

/**
 * @author WangHao
 * @date 2019/7/20 11:11
 */
public class BinarySearch {
    public static void main(String[] args) {
        int[] array = {1, 22, 333, 4343, 62342, 744444, 78523333};
        System.out.println(binarySearch(array, 333));
    }

    public static int binarySearch(int[] array, int val) {
        return binarySearch(array, val, 0, array.length - 1);
    }

    public static int binarySearch(int[] array, int val, int left, int right) {
        if (left <= right) {
            int mid = (left + right) / 2;
            if (val < array[mid]) {
                return binarySearch(array, val, left, mid - 1);
            } else if (val > array[mid]) {
                return binarySearch(array, val, mid + 1, right);
            } else {
                return mid;
            }
        } else {
            return -1;
        }
    }
}
