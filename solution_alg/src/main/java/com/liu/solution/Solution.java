package com.liu.solution;

/**
 * 两数之和
 */
public class Solution {

    public int[] twoSum(int[] nums, int target) {
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (nums[i]+nums[j]==target){
                    return new int[]{i,j};
                }
            }
        }
        return new int[0];
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] tom = new int[]{12,3,4,5,6,7,9,20};
        int[] ints = s.twoSum(tom, 9);
        for (int i = 0; i < ints.length; i++) {
            System.out.println(ints[i]);
        }

    }

}
