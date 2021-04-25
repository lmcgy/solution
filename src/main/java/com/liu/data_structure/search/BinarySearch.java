package com.liu.data_structure.search;


/**
 * 二分查找
 */
public class BinarySearch {


    /**
     * 最简单二分查找
     * @param a
     * @param n
     * @param value
     * @return
     */
    public int search(int[] a,int n,int value){
        int low = 0,high=n-1;
        while (low<=high){
            int mid= (low+high)>>1;
             if (a[mid]<value){
                 low = mid+1;
             }else if (a[mid]>value){
                 high = mid-1;
             }else{
                 return mid;
             }
        }
        return -1;
    }

    /**
     * 查找第一个匹配的元素
     * @param a
     * @param n
     * @param value
     * @return
     */
    public int fSearch(int[] a,int n,int value){
        int low=0,high=n-1;
        while (low<=high){
            int mid = (low+high)>>1;
            if (a[mid]<value){
                low=mid+1;
            }else if (a[mid]>value){
                high=mid-1;
            }else{
                if (mid==0 || a[mid-1]!=value)return mid;
                else high=mid-1;
            }

        }
        return -1;
    }


    /**
     * 获取最后一个匹配元素
     * @param a
     * @param n
     * @param value
     * @return
     */
    public int lSearch(int[] a,int n,int value){
        int low = 0,high=n-1;
        while (low<=high){
            int mid=(low+high)>>1;
            if (a[mid]<value){
                low= mid+1;
            }else if (a[mid]>value){
                high=mid-1;
            }else{
                if (mid==(n-1) || a[mid+1]!=value)return mid;
                else low= mid+1;
            }
        }

        return -1;
    }

    /**
     * 查找第一个大于等于给定值的元素
     * @param a
     * @param n
     * @param value
     * @return
     */
    public int bSearchf(int[] a,int n,int value){
        int low = 0,high = n-1;
        while (low<=high){
            int mid=(low+high)>>1;
            if (a[mid]<value){
                low = mid+1;
            }else{
                if (mid==0 || a[mid-1]<value)return mid;
                else high=mid-1;
            }
        }
        return -1;
    }


    /**
     * 查找最后一个小于等于给定值的元素
     * @param a
     * @param n
     * @param value
     * @return
     */
    public int bSearchl(int[] a , int n ,int value){
        int low=0,high=n-1;
        while (low<=high){
            int mid=(low+high)>>1;
            if (a[mid]<=value){
                if (mid==(n-1) || a[mid+1]>value)return mid;
                else low = mid+1;
            }else{
                high=mid-1;
            }
        }
        return -1;
    }




    public static void main(String[] args) {

    }
}
