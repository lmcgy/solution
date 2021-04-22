package com.liu.data_structure.sout;

/**
 * 插入排序
 */
public class InsertSort {

    public void sort(int[] a , int n){
        if (n<=1) return;

        for (int i = 1; i < n; i++) {
            int value = a[i];
            int j = i-1;
            for (; j>=0 ; j--) {
                if (a[j]>value){
                    a[j+1]=a[j];
                }else{
                    break;
                }
            }
            a[j+1]= value;
        }
    }

    public static void main(String[] args) {

        int[] a = {9,1,6,2,8,6,7,5,3,4};
        InsertSort insertSort = new InsertSort();
        insertSort.sort(a,a.length);

        for (int i : a) {
            System.out.println(i);
        }
    }
}
