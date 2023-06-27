package com.zhou.algorithmproblem;

import org.junit.Test;

/**
 * @author zhouyuanke
 * @date 2023/6/27
 */
public class SortMethods {

    private int result = 0;

    public void print(int[] nums) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int num : nums) {
            stringBuffer.append(" ");
            stringBuffer.append(num);
        }
        System.out.println(stringBuffer);
    }

    /**
     * 归并排序，可以分为两步
     * 1. 将数组分为两部分分别进行排序
     * 2. 将两部分进行合并
     */
    public int[] mergeSort(int[] nums) {
        int left = 0, right = nums.length - 1;
        int[] temporaryArray = new int[nums.length];
        sort(left, right, nums, temporaryArray);
        return nums;
    }

    private void sort(int left, int right, int[] nums, int[] temporaryArray) {
        if (left >= right) {
            return;
        }
        int mid = (left + right) / 2;
        // 对左边进行排序
        sort(left, mid, nums, temporaryArray);
        // 对右边进行排序
        sort(mid + 1, right, nums, temporaryArray);
        // 合并两部分
        merge(left, right, nums, temporaryArray);
    }

    private void merge(int left, int right, int[] nums, int[] temporaryArray) {
        int i = left, p1 = left, mid = (left + right) / 2;
        int p2 = mid + 1;
        while (p1 <= mid && p2 <= right) {
            if (nums[p1] <= nums[p2]) {
                temporaryArray[i++] = nums[p1++];
            } else {
                temporaryArray[i++] = nums[p2++];
                result += (mid - p1 + 1);
            }
        }
        while (p1 <= mid) {
            temporaryArray[i++] = nums[p1++];
        }
        while (p2 <= right) {
            temporaryArray[i++] = nums[p2++];
        }
        for (int j = left; j <= right; j++) {
            nums[j] = temporaryArray[j];
        }
    }

    @Test
    public void test() {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 0};
        mergeSort(nums);
        print(nums);
        System.out.println(result);
    }
}
