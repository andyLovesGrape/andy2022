package com.zhou.algorithmproblem;

import org.junit.Assert;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 滑动窗口找最长子串
 *
 * @author zhouyuanke
 * @date 2022/9/18 21:39
 */
public class LengthOfLongestSubstring {

    public static class Solution {
        public static int lengthOfLongestSubstring(String s) {
            int size = s.length();
            Set<Character> set = new HashSet();
            int right = 0, result = 0;
            for (int i = 0; i < size; ++i) {
                if (i != 0) { // 遍历整个字符串，左边指针逐渐向右移动
                    set.remove(s.charAt(i - 1));
                }
                while (right < size && !set.contains(s.charAt(right))) {
                    set.add(s.charAt(right)); // 这里两句可以合并成 set.add(s.charAt(right++));
                    ++right;
                }
                result = Math.max(result, right - i); // 注意，这里right - i没有+1，因为这个right已经是上面条件不满足的，已经+1了
            }
            return result;
        }

        public static int lengthOfLongestSubstring1(String s) {
            // 优化版，主要是用hashMap来存储每个元素的位置，当遇到重复的元素时，直接挑到最后的那个重复元位置
            // 例如 abcdece 字符串，上面的解法在遇到第一个c时，左边指针需要逐渐移动到第四个位置，也就是第一个c下一个
            int size = s.length();
            HashMap<Character, Integer> hashMap = new HashMap(size);
            int left = 0, result = 0;
            for (int right = 0; right < size; ++right) {
                if (hashMap.containsKey(s.charAt(right))) {
                    left = Math.max(left, hashMap.get(s.charAt(right)) + 1); // 注意，这里需要和left当前值进行比较，left只会向右移动，不比较会倒退
                    // 例如 abba 字符串，当右指针到达第二个a时，左指针是指向第二个b的，但是此时hashMap中是存在a元素的，如果不和left当前值2进行比较，
                    // 那么left会跳回到0，是错误的
                }
                hashMap.put(s.charAt(right), right); // 更新每个元素的最新位置
                result = Math.max(result, right - left + 1);
            }
            return result;
        }
    }

    public static void main(String[] args) {
        String s = "abba";
        Assert.assertEquals(Solution.lengthOfLongestSubstring1(s), 2);
    }
}
