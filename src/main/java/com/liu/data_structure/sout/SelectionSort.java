package com.liu.data_structure.sout;

/**
 * 选择排序
 */
public class SelectionSort {

    public void sort(int[] a,int n){
        if (n<=1)return;

        for (int i = 0; i < n; i++) {

            int value = a[i];
            int sert = i;
            for (int j = i; j < n ; j++) {
                if (a[j]<value){
                    value = a[j];
                    sert = j;
                }
            }

            int temp = a[sert];
            a[sert] = a[i];
            a[i] = temp;

        }

    }

    public static void main(String[] args) {

        int[] a = {9,1,6,2,8,7,5,3,4};
        SelectionSort selectionSort = new SelectionSort();
        selectionSort.sort(a,a.length);

        for (int i : a) {
            System.out.println(i);
        }

    }

}
