package com.zhou.algorithmproblem;

import org.junit.Assert;

import java.util.*;

/**
 * 求两数之和，使用hashMap来记录元素
 *
 * @author zhouyuanke
 * @date 2022/9/17 18:22
 */
public class TwoSum {

    public static class Solution {
        public static int[] twoSum(int[] nums, int target) {
            int size = nums.length;
            int[] result = new int[2];
            Map<Integer, Integer> hashMap = new HashMap(size);
            for (int i = 0; i < size; ++i) {
                // 在循环的时候进行查询，不用先全部存下来再查询
                if (hashMap.containsKey(nums[i])) {
                    result[0] = hashMap.get(nums[i]);
                    result[1] = i;
                    return result;
                }
                hashMap.put(target - nums[i], i);
            }
            return result;
        }
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new ArrayDeque<>();

        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        for (int i : Arrays.copyOfRange(nums, 0, 2)) {
            System.out.println(i);
        }
        //Assert.assertArrayEquals(new int[]{0, 1}, Solution.twoSum(nums, target));
    }

    private List<Integer> addList() {
        List<Integer> nums = new ArrayList<>();
        return nums;
    }
}
