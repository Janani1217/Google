Given the root of a binary tree, each node in the tree has a distinct value.

After deleting all nodes with a value in to_delete, we are left with a forest (a disjoint union of trees).

Return the roots of the trees in the remaining forest. You may return the result in any order.

 

// APPROACH : DFS 

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        List<TreeNode> result = new ArrayList<>();
        Set<Integer> toDeleteSet = new HashSet<>();
        for (int val : to_delete) {
            toDeleteSet.add(val);
        }

        dfs(root, toDeleteSet, result);

        if (!toDeleteSet.contains(root.val)) {
            result.add(root);
        }

        return result;
    }

    private TreeNode dfs(TreeNode node, Set<Integer> toDeleteSet, List<TreeNode> result) {
        if (node == null) {
            return null;
        }

        node.left = dfs(node.left, toDeleteSet, result);
        node.right = dfs(node.right, toDeleteSet, result);

        if (toDeleteSet.contains(node.val)) {
            if (node.left != null) {
                result.add(node.left);
            }
            if (node.right != null) {
                result.add(node.right);
            }
            return null; // Remove the current node from the tree
        }

        return node;
    }
}
