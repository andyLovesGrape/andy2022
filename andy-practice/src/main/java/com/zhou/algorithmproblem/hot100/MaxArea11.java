package com.zhou.algorithmproblem.hot100;

/**
 * 当容器选择左右两端的柱子时，底是最大的。然后将左边边界右移、右边边界左移，以找到更大的高，获得更大的面积
 *
 * @author zhouyuanke
 * @date 2022/10/24 09:22
 */
public class MaxArea11 {

    private int maxArea(int[] height) {
        int i = 0, j = height.length - 1, area = 0;
        while (i < j) {
            area = Math.max(area, Math.min(height[i], height[j]) * (j - i));
            if (height[i] < height[j]) {
                ++i;
            } else {
                --j;
            }
        }
        return area;
    }
}
