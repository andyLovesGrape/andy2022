package com.zhou.interview;

import java.util.*;

/**
 * @author: zhouyuanke
 * @createTime: 2023-11-18 16:33
 */
public class Methods {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        subarraySum(new int[]{1, 1, 1}, 2);
    }

    /**
     * L300 最长递增子序列
     *
     * @param nums
     * @return
     */
    public int lengthOfLIS(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = 1;
        int maxNum = 1;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = 1;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxNum = Math.max(maxNum, dp[i]);
        }
        return maxNum;
    }

    /**
     * L148 排序链表 给你链表的头结点 head ，请将其按 升序 排列并返回 排序后的链表 要求时间复杂度为O(nlogn) 空间复杂度为 O(1)
     * 归并排序 将链表分为两部分，递归排序，然后合并有序链表
     *
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        return sortList(head, null);
    }

    public ListNode sortList(ListNode head, ListNode tail) {
        if (head == null) {
            return null;
        }
        if (head.next == tail) {
            // 此时只有一个节点 不包括tail 是递归的结束条件
            head.next = null;
            return head;
        }
        ListNode slow = head, fast = head;
        while (fast != tail) {
            slow = slow.next;
            fast = fast.next;
            if (fast != tail) {
                fast = fast.next;
            }
        }
        ListNode mid = slow;
        ListNode list1 = sortList(head, mid);
        ListNode list2 = sortList(mid, tail);
        return merge(list1, list2);
    }

    public ListNode merge(ListNode head1, ListNode head2) {
        // 合并两个有序链表
        ListNode dummyHead = new ListNode(0);
        ListNode temp = dummyHead, temp1 = head1, temp2 = head2;
        while (temp1 != null && temp2 != null) {
            if (temp1.val <= temp2.val) {
                temp.next = temp1;
                temp1 = temp1.next;
            } else {
                temp.next = temp2;
                temp2 = temp2.next;
            }
            temp = temp.next;
        }
        if (temp1 != null) {
            temp.next = temp1;
        } else if (temp2 != null) {
            temp.next = temp2;
        }
        return dummyHead.next;
    }

    /**
     * L53 最大子数组和
     * 动态规划 dp[i] = max(dp[i-1]+nums[i], nums[i])
     *
     * @param nums
     * @return
     */
    public int maxSubArray(int[] nums) {
        int length = nums.length;
        int[] dp = new int[length];
        dp[0] = nums[0];
        int result = dp[0];
        for (int i = 1; i < length; i++) {
            dp[i] = Math.max(nums[i], dp[i - 1] + nums[i]);
            result = Math.max(dp[i], result);
        }
        return result;
    }

    class Interval {
        int start;
        int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * N89 L56 合并区间
     * 对区间进行排序，然后顺序遍历
     *
     * @param intervals
     * @return
     */
    public ArrayList<Interval> merge(ArrayList<Interval> intervals) {
        int n = intervals.size();
        if (n <= 1) {
            return intervals;
        }
        ArrayList<Interval> result = new ArrayList<>();
        Collections.sort(intervals, Comparator.comparingInt(o -> o.start));
        int start = intervals.get(0).start, end = intervals.get(0).end;
        for (Interval interval : intervals) {
            if (interval.start > end) {
                result.add(new Interval(start, end));
                start = interval.start;
                end = interval.end;
            } else {
                end = Math.max(end, interval.end);
            }
        }
        result.add(new Interval(start, end));
        return result;
    }

    /**
     * L3 无重复字符的最长子串
     * 使用双指针遍历数组，右边指针遍历，如果存在重复的，左指针开始往右，直到找到重复的位置
     * 子串（要求连续）、子序列、子数组
     *
     * @param arr
     * @return
     */
    public int maxLen(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        Map<Integer, Integer> hashMap = new HashMap<>(arr.length);
        int result = 0;
        for (int left = 0, right = 0; right < arr.length; right++) {
            if (!hashMap.containsKey(arr[right])) {
                hashMap.put(arr[right], 1);
            } else {
                hashMap.put(arr[right], hashMap.get(arr[right]) + 1);
            }
            while (hashMap.get(arr[right]) > 1) {
                hashMap.put(arr[left], hashMap.get(arr[left]) - 1);
                left++;
            }
            result = Math.max(right - left + 1, result);
        }
        return result;
    }

    public int lengthOfLongestSubString(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        int result = 0;
        char[] chars = s.toCharArray();
        Map<Character, Integer> hashMap = new HashMap<>(s.length());
        for (int left = 0, right = 0; right < s.length(); right++) {
            if (hashMap.containsKey(chars[right])) {
                hashMap.put(chars[right], hashMap.get(chars[right]) + 1);
            } else {
                hashMap.put(chars[right], 1);
            }
            while (hashMap.get(chars[right]) > 1) {
                hashMap.put(chars[left], hashMap.get(chars[left]) - 1);
                left++;
            }
            result = Math.max(result, right - left + 1);
        }
        return result;
    }

    /**
     * L128 最长连续序列
     *
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int result = 0;
        for (int num : nums) {
            if (!set.contains(num - 1)) {
                int curNum = num, curLen = 1;
                while (set.contains(++curNum)) {
                    curLen++;
                }
                result = Math.max(curLen, result);
            }
        }
        return result;
    }

    /**
     * L560 和为 K 的子数组
     * 前缀和
     *
     * @param nums
     * @param k
     * @return
     */
    public static int subarraySum(int[] nums, int k) {
        int count = 0, pre = 0;
        Map<Integer, Integer> hashMap = new HashMap<>(nums.length);
        hashMap.put(0, 1);
        for (int num : nums) {
            pre += num;
            if (hashMap.containsKey(pre - k)) {
                count += hashMap.get(pre - k);
            }
            hashMap.put(pre, hashMap.getOrDefault(pre, 0) + 1);
        }
        return count;
    }

    /**
     * L5 最长回文子串
     * 动态规划
     * 状态转移方程 dp[i][j] = dp[i + 1][j - 1] and s[i] == s[j]
     * 由于 dp[i][j] 的值依赖于左下角的值，因此从左向右一列一列填写
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        int length = s.length();
        if (length < 2) {
            return s;
        }

        boolean[][] dp = new boolean[length][length];
        for (int i = 0; i < length; i++) {
            dp[i][i] = true;
        }

        int maxLen = 1;
        int begin = 0;
        char[] charArray = s.toCharArray();
        // 从左向右一列一列填写
        for (int j = 1; j < length; j++) {
            for (int i = 0; i < j; i++) {
                if (charArray[i] != charArray[j]) {
                    dp[i][j] = false;
                } else {
                    // j - 1 - (i + 1) + 1 < 2
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }
                // 记录回文子串的位置
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    /**
     * N66 最长公共子串
     * dp[i][j] = dp[i - 1]dp[j - 1] and s[i] == s[j]
     *
     * @param str1
     * @param str2
     * @return
     */
    public String LCS(String str1, String str2) {
        char[] char1 = str1.toCharArray();
        char[] char2 = str2.toCharArray();
        int n1 = char1.length, n2 = char2.length;
        int maxLen = 0, index = 0;
        int[][] matrix = new int[n1 + 1][n2 + 1];
        for (int i = 0; i < n1; i++) {
            for (int j = 0; j < n2; j++) {
                if (char1[i] == char2[j]) {
                    matrix[i + 1][j + 1] = matrix[i][j] + 1;
                    if (matrix[i + 1][j + 1] > maxLen) {
                        maxLen = matrix[i + 1][j + 1];
                        index = i;
                    }
                }
            }
        }
        return str1.substring(index - maxLen + 1, index + 1);
    }

    /**
     * L1143 最长公共子序列
     * 和最长公共子串类似 使用动态规划 区别在于子序列是不连续的 当两个字符不相等时 dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
     * 而子串是连续的 当两个字符不相等时 dp[i][j] = 0
     *
     * @param text1
     * @param text2
     * @return
     */
    public int longestCommonSubsequence(String text1, String text2) {
        char[] chars1 = text1.toCharArray();
        char[] chars2 = text2.toCharArray();
        int[][] dp = new int[chars1.length + 1][chars2.length + 1];
        for (int i = 1; i <= chars1.length; i++) {
            for (int j = 1; j <= chars2.length; j++) {
                if (chars1[i - 1] == chars2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[chars1.length][chars2.length];
    }

    /**
     * L39 组合总和
     * 动态规划 每次遍历全部数组判断是否满足要求
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> combination = new ArrayList<>();
        dfs(candidates, 0, target, result, combination);
        return result;
    }

    public void dfs(int[] candidates, int index, int target, List<List<Integer>> result, List<Integer> combination) {
        if (target == 0) {
            result.add(new ArrayList<>(combination));
        }
        if (target < 0) {
            return;
        }
        for (int i = index; i < candidates.length; i++) {
            combination.add(candidates[i]);
            dfs(candidates, i, target - candidates[i], result, combination);
            combination.remove(combination.size() - 1);
        }
    }

    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    /**
     * N7 链表中环的入口结点
     * 快慢指针判断是否有环
     *
     * @param pHead
     * @return
     */
    public ListNode EntryNodeOfLoop(ListNode pHead) {
        if (pHead == null) {
            return null;
        }

        ListNode slow = pHead, fast = pHead;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                break;
            }
        }
        if (fast == null || fast.next == null) {
            return null;
        }
        // 2p - p = n * L -> p = n * L
        // x + m * L + y = p
        // x + m * L + y = n * L
        // x = (n - m - 1) * L + (L - y)
        // 最终一定会在环的入口处相遇
        ListNode node = pHead;
        while (node != slow) {
            node = node.next;
            slow = slow.next;
        }
        return node;
    }

    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * L102 二叉树层序遍历, 使用队列
     *
     * @param root
     * @return
     */
    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new ArrayDeque<>();
        int size;
        queue.add(root);
        while (!queue.isEmpty()) {
            size = queue.size();
            ArrayList<Integer> layer = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                layer.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
            }
            result.add(layer);
        }
        return result;
    }

    /**
     * L20 有效的括号
     * 使用栈
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            if (aChar == '(' || aChar == '[' || aChar == '{') {
                stack.push(aChar);
                continue;
            }
            if (stack.isEmpty()) {
                return false;
            }
            if (aChar == ')') {
                char c = stack.pop();
                if (c != '(') {
                    return false;
                }
            } else if (aChar == ']') {
                char c = stack.pop();
                if (c != '[') {
                    return false;
                }
            } else {
                char c = stack.pop();
                if (c != '{') {
                    return false;
                }
            }
        }
        if (stack.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * L215 数组中的第K个最大元素
     * 需要O(n)时间复杂度, 使用快排
     *
     * @return
     */
    public void findKthLargest() {
        int[] nums = new int[]{3,2,1,5,6,4};
        fastSort(nums, 0 , nums.length - 1, 2);
    }

    public void print(int[] nums) {
        System.out.println(String.join(",", Arrays.stream(nums).mapToObj(String::valueOf).toArray(String[]::new)));
    }

    private boolean flag = false;

    private void fastSort(int[] nums, int begin, int end, int k) {
        if (begin > end) {
            return;
        }
        int target = nums[begin];
        int i = begin, j = end;
        while (i < j) {
            while (i <= end && nums[i] > target) {
                i++;
            }
            while (j >= 0 && nums[j] < target) {
                j--;
            }
            swap(nums, i, j);
        }
        if (i + 1 == k) {
            System.out.println(nums[i]);
            flag = true;
            return;
        }
        //print(nums);
        if (!flag) {
            fastSort(nums, begin, i - 1, k);
        }
        if (!flag) {
            fastSort(nums, i + 1, end, k);
        }
    }

    private void swap(int[] nums, int index1, int index2) {
        int temp = nums[index1];
        nums[index1] = nums[index2];
        nums[index2] = temp;
    }
}
