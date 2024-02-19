package com.zhou.algorithmproblem;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        print(nums);
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

    /**
     * 快速排序，选择第一个元素作为分界线
     * 找到左边第一个大于的元素 和 右边第一个小于的元素，将二者进行互换
     * 然后再分别对左边和右边的元素进行同样的排序
     */
    public void fastSort(int[] array) {
        sort(0, array.length - 1, array);
    }

    private void sort(int begin, int end, int[] array) {
        // 终止条件，如果该区间只有一个值或者没有值则停止迭代
        if (begin >= end) {
            return;
        }
        int left = begin, right = end, key = array[begin], temp;
        while (left < right) {
            while (left < array.length && array[left] < key) {
                left++;
            }
            while (right >= 0 && array[right] > key) {
                right--;
            }
            temp = array[left];
            array[left] = array[right];
            array[right] = temp;
        }
        sort(begin, left - 1, array);
        sort(left + 1, end, array);
    }

    /**
     * 堆排序，首先构建一个大根堆，取出根节点
     * 对剩下的树节点进行调整，直至排序完成
     */
    public void heapSort(int[] array) {
        // array.length / 2 - 1 是最后一个非叶节点
        // 自下到上进行调整，得到一个大根堆（根节点为最大值）
        // 这一步调整很重要，保证大根堆基本有序，每一层的根节点都大于下一层的子节点
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            adjustHeap(array, i, array.length);
        }
        for (int j = array.length - 1; j > 0; j--) {
            print(array);
            // 进行交换，将最大值（根节点）移动到数组最后面
            int temp = array[0];
            array[0] = array[j];
            array[j] = temp;
            // 对剩余的节点进行调整，获取新的最大值
            adjustHeap(array, 0, j);
        }
    }

    private void adjustHeap(int[] array, int start, int length) {
        int temp = array[start];
        for (int i = 2 * start + 1; i < length; i = 2 * i + 1) {
            // 保证i是左右子树中较大的那个
            if (i + 1 < length && array[i + 1] > array[i]) {
                i++;
            }
            if (array[i] > temp) {
                int num = array[start];
                array[start] = array[i];
                array[i] = num;
                // 此时temp的值已经换到i位置
                start = i;
            } else {
                break;
            }
        }
    }

    private int target = -1;

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param a int整型一维数组
     * @param K int整型
     * @return int整型
     */
    public int findKth(int[] a, int K) {
        target = K;
        fastSort(a, 0, a.length - 1);
        return result;
    }

    @Test
    public void test1119() {
        findKth(new int[]{3,2,1,5,6,4}, 2);
    }

    private void fastSort(int[] array, int begin, int end) {
        // System.out.println(begin + " " + end);
        if (result != 0 || begin >= end) {
            return;
        }
        int key = array[begin], i = begin, j = end;
        while (i < j) {
            while (i <= end && array[i] > key) {
                i++;
            }
            while (j >= 0 && array[j] < key) {
                j--;
            }
            if (i < j && array[i] == array[j]) {
                i++;
            } else {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }
        // print(array);
        if (i + 1 == target) {
            result = array[i];
        }
        fastSort(array, begin, i - 1);
        fastSort(array, i + 1, end);
    }


    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public TreeNode reConstructBinaryTree(int[] preOrder, int[] vinOrder) {
        TreeNode root = recursion(preOrder, vinOrder);
        return root;
    }

    private TreeNode recursion(int[] preOrder, int[] vinOrder) {
        TreeNode root = new TreeNode(preOrder[0]);
        int index = 0;
        for (int i = 0; i < vinOrder.length; i++) {
            if (vinOrder[i] == preOrder[0]) {
                index = i;
            }
        }
        TreeNode leftNode = null;
        System.out.println(index);
        if (index > 0 && index + 1 < preOrder.length) {
            leftNode = recursion(Arrays.copyOfRange(preOrder, 1, index + 1), Arrays.copyOfRange(vinOrder, 0, index));
        }
        TreeNode rightNode = null;
        if (index < vinOrder.length - 1 && index + 1 < vinOrder.length) {
            rightNode = recursion(Arrays.copyOfRange(preOrder, index + 1, preOrder.length), Arrays.copyOfRange(vinOrder, index + 1, vinOrder.length));
        }
        root.left = leftNode;
        root.right = rightNode;
        return root;
    }

    public ArrayList<String> generateParenthesis(int n) {
        ArrayList<String> res = new ArrayList<>();
        recursion(0, 0, new StringBuffer(), res, n);
        return res;
    }

    private void recursion(int left, int right, StringBuffer sb, ArrayList<String> res, int n) {
        if (left == n && right == n) {
            res.add(new String(sb));
            return;
        }
        if (left < n) {
            recursion(left + 1, right, sb.append("("), res, n);
        }
        if (right < n && left > right) {
            recursion(left, right + 1, sb.append(")"), res, n);
        }
    }

    @Test
    public void test() {
        List<String> str1 = new ArrayList<>();
        String[] array1 = str1.toArray(new String[str1.size()]);

        String[] array2 = new String[]{"111"};
        List<String> str2 = Arrays.asList(array2);
    }

    public static void main(String[] args) {
        int num1 = 15;
        int num2 = 4;
        System.out.println(num2 & num1);
        System.out.println(num2 % num1);
    }

    static class ThreadTest implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                nums.add(i);
            }
        }
    }

    private static List<Integer> nums = new ArrayList<>();
}