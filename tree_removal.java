
You are given the root of a binary tree with n nodes. Each node is assigned a unique value from 1 to n. 
You are also given an array queries of size m.

You have to perform m independent queries on the tree where in the ith query you do the following:

Remove the subtree rooted at the node with the value queries[i] from the tree. 
It is guaranteed that queries[i] will not be equal to the value of the root.

Return an array answer of size m where answer[i] is the height of the tree after performing the ith query.

Note:

The queries are independent, so the tree returns to its initial state after each query.
The height of a tree is the number of edges in the longest simple path from the root to some node in the tree.



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
    private Map<Integer, Integer> leftht = new HashMap<>();
    private Map<Integer, Integer> rightht = new HashMap<>();
    private Map<Integer, Integer> removed = new HashMap<>();

    public int[] treeQueries(TreeNode root, int[] queries) {
        int m = queries.length;
        int[] arr = new int[m];

        populateHeights(root, 0);
        calculateRemovedHeight(root, 0);

        for(int i=0; i<m; i++) {
            arr[i] = removed.get(queries[i]);
        }
        return arr;
    }
    
    private void calculateRemovedHeight(TreeNode node, int ht) {
        if(node == null)
            return;
        removed.put(node.val, ht); // if this node is removed : whta is the ht of the tree = ht

        calculateRemovedHeight(node.left, Math.max(rightht.get(node.val) , ht));
        calculateRemovedHeight(node.right, Math.max(leftht.get(node.val) , ht));
    }

    private int populateHeights(TreeNode root, int ht) {
        if(root == null)
            return ht-1;
        
        leftht.put(root.val, populateHeights(root.left, ht+1));
        rightht.put(root.val, populateHeights(root.right, ht+1));

        return Math.max(leftht.get(root.val) , rightht.get(root.val));
    }
}
