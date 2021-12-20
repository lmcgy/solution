package com.liu.data_structure.sout;

/**
 * 快速排序
 * 核心思想分区
 * 分区点
 * 核心思想-分治思想
 */
public class QuickSort {

    public void quickSort(int[] a,int p,int r){
        if (p>=r)return;
        int q = partion(a,p,r);
        quickSort(a,p,q-1);
        quickSort(a,q+1,r);

    }

    public int partion(int[] a,int p,int r){
        int pivot = a[r];
        int j = p;
        for (int i = p; i < r; i++) {
            if (a[i]<pivot){
                if (j==i){
                    j++;
                }else{
                    int temp = a[i];
                    a[i]=a[j];
                    a[j++] = temp;
                }
            }
        }

        a[r]  = a[j];
        a[j] = pivot;
        return j;

    }

    public static void main(String[] args) {
        int[] a = {9,1,8,2,7,3,6,4,5};
        QuickSort quickSort = new QuickSort();
        quickSort.quickSort(a,0,a.length-1);


        for (int i : a) {
            System.out.println(i);
        }
    }



}
