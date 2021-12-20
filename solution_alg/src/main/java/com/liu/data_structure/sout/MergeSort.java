package com.liu.data_structure.sout;


/**
 * 归并排序
 */
public class MergeSort {


    /**
     *  归并排序
     * @param a 数组
     * @param p 排序范围区间起点
     * @param r 排序范围区间终点
     */
    public void mergeSort(int[] a,int p,int r){
        if (p>=r)return;
        int q =p + (r-p)/2;
        mergeSort(a,p,q);
        mergeSort(a,q+1,r);

        merge(a,p,q,r);

    }

    /**
     * 合并
     * @param a
     * @param p
     * @param q
     * @param r
     */
    public void merge(int[] a,int p,int q,int r){
        int i=p,j=q+1,k=0;
        int[] temp=new int[r-p+1];
        while (i<=q && j<=r){
            if (a[i]<a[j]){
                temp[k++]=a[i++];
            }else{
                temp[k++] = a[j++];
            }
        }
        int s=i,e=q;
        if (r>=j){
            s=j;e=r;
        }
        while (s<=e){
            temp[k++] = a[s++];
        }

        for (int l = 0; l < k; ++l) {
            a[p+l] = temp[l];
        }
    }


    public static void main(String[] args) {
        int[] a = {1,2,9,8,4,3,7,6,5};

        MergeSort mergeSort = new MergeSort();
        mergeSort.mergeSort(a,0,a.length-1);

        for (int i : a) {
            System.out.println(i);
        }
    }
}
