package com.hughes.code.lintcode;

/**
 * Created by Hughes on 2016/8/14.
 */
public class PartitionArray {
    /**
     * @param nums: an array of integers
     * @return: nothing
     */
    public void partitionArray(int[] nums) {
        int index = nums.length - 1;
        if (index <= 0) {
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (0 == nums[i] % 2) {
                while (i < index && 0 == nums[index] % 2) {
                    index--;
                }
                if (i == index) {
                    break;
                } else if (i < index) {
                    int temp = nums[i];
                    nums[i] = nums[index];
                    nums[index--] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4};
        new PartitionArray().partitionArray(nums);
        System.out.println(nums);
    }
}
