package com.fc.test.sorting;

/**
 * @ClassName: BubbleSortDemo
 * @Description: 冒泡排序
 * @Author mac
 * @Date 2019/3/21 下午2:48
 **/
public class BubbleSortDemo {

    public static void main(String[] args) {
        int[] array = {10,9,8,7};

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j+1]) {
                    int temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
            }
        }

        System.out.println("排序后的数组为：");
        for (int num : array) {
            System.out.print(num + "");
        }
    }
}
