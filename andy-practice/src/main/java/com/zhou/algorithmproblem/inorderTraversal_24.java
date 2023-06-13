package com.zhou.algorithmproblem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyuanke
 * @date 2023/6/13
 */
public class inorderTraversal_24 {
    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public class Solution {
        public int[] inorderTraversal(TreeNode root) {
            if (root == null) {
                return new int[]{};
            }
            List<Integer> nums = new ArrayList<>();
            traversal(nums, root);
            return nums.stream().mapToInt(Integer::intValue).toArray();
        }

        private void traversal(List<Integer> nums, TreeNode root) {
            if (root.left != null) {
                traversal(nums, root.left);
            }
            nums.add(root.val);
            if (root.right != null) {
                traversal(nums, root.right);
            }
        }
    }
}