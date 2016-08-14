package com.hughes.code.lintcode;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Created by Hughes on 2016/8/14.
 */
public class MinimumInRotatedSortedArray {
    /**
     * @param nums: a rotated sorted array
     * @return: the minimum number in the array
     */
    public int findMin(int[] nums) {
        int size = nums.length - 1;
        int l = 0;
        int r = size;
        while(l <= r) {
            int mid = l + (r - l) / 2;
            if (nums[mid] > nums[size]) {
                //left
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return nums[l];

        /*if (0 == nums.length) {
            return Integer.MAX_VALUE;
        }
        if (1 == nums.length) {
            return nums[0];
        }
        if (2 == nums.length) {
            return Math.min(nums[0], nums[1]);
        }
        int mid = nums.length / 2;
        if (nums[mid] >= nums[0] + mid) {
            return Math.min(nums[0], findMin(ArrayUtils.subarray(nums, mid + 1, nums.length)));
        } else {
            return findMin(ArrayUtils.subarray(nums, 0, mid + 1));
        }*/
    }

    public static void main(String[] args){
        int[] nums = {6,1,2,3,4,5};
        int result = new MinimumInRotatedSortedArray().findMin(nums);
        System.out.println(result);
    }
}
