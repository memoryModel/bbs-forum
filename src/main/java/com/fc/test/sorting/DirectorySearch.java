package com.fc.test.sorting;

/**
 * @ClassName: DirectorySearch
 * @Description: 二分查找算法
 * @Author mac
 * @Date 2019/3/15 下午3:51
 **/
public class DirectorySearch {

    private static final int[] ARRAY = {1,2,3,4,5,6,7,8,9,10};

    public static void main(String[] args) {
        System.out.println(binSearch1(ARRAY, 6, 0, ARRAY.length -1));

    }

    public static int binSearch(int[] array, int key) {
        int start = 0;
        int mid;
        int end = array.length -1;
        while (start<=end) {
            mid = (end - start) / 2 + start;
            if (key < array[mid]) {
                end = mid -1;
            } else if (key > array[mid]) {
                start = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }


    /**
     * @param array 操作数组
     * @param key 查找元素
     * @param start 开始下标
     * @param end 结束下标
     * @return 元素下标
     */
    public static int binSearch1(int[] array,int key,int start,int end){

        int mid = (end - start) / 2 + start;

        if (key == array[mid]) {
            return mid;
        }
        else if (start >= end) {
            return -1;
        }
        else if (key < array[mid]) {
            return binSearch1(array, key, start, mid -1);
        }
        else if (key > array[mid]) {
            return binSearch1(array, key, mid + 1, mid);
        }

        return -1;
    }
}
