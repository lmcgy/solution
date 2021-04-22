package com.liu.data_structure.sout;


/**
 * 冒泡排序
 */
public class BullingSort {

    public void sort(int[] a , int n){
        if (n<=1) return;

        for (int i = 0; i < n ; i++) {
            boolean isSort = false;
            for (int j = 0; j < n-i-1; j++) {
                if (a[j]>a[j+1]){
                    int temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                    isSort = true;
                }
            }
            if (!isSort) break;

        }

    }

    public static void main(String[] args) {

        int[] a = {9,1,6,2,8,6,7,5,3,4};
        BullingSort bullingSort = new BullingSort();
        bullingSort.sort(a,a.length);

        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);

        }
    }
}
