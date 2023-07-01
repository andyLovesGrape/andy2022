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
                start = i;
            } else {
                break;
            }
        }
    }

    @Test
    public void test() {
        int[] array = new int[]{2,5,7,3,4,1,8};
        heapSort(array);
        print(array);
    }
}
