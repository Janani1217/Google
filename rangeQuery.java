A Range Module is a module that tracks ranges of numbers. 
Design a data structure to track the ranges represented as half-open intervals and query about them.

A half-open interval [left, right) denotes all the real numbers x where left <= x < right.

Implement the RangeModule class:

RangeModule() Initializes the object of the data structure.

void addRange(int left, int right) Adds the half-open interval [left, right), tracking every real number in that interval. 
      Adding an interval that partially overlaps with currently tracked numbers should add any numbers in the interval 
      [left, right) that are not already tracked.

boolean queryRange(int left, int right) 
    Returns true if every real number in the interval [left, right) is currently being tracked, and false otherwise.


void removeRange(int left, int right) Stops tracking every real number currently being tracked in the half-open interval [left, right).



// SEGMENT TREE APPRACH !!!! 

class SegmentTreeNode {
    int lval, rval;
    SegmentTreeNode left, right;
    boolean isTracked;

    public SegmentTreeNode(int l, int r){
        lval = l;
        rval = r;
    }

    public SegmentTreeNode(int l, int r, boolean tracked){
        lval = l;
        rval = r;
        isTracked = tracked;
    }
}


class RangeModule {
    SegmentTreeNode head;
    int MAX = 1000000000;

    public RangeModule() {
    head = new SegmentTreeNode(0, MAX);
    }

    public void addRange(int left, int right) {
        addToRange(left, right, head);
    }

    public boolean queryRange(int left, int right) {
        return queryToTree(left, right, head);
    }

    public void removeRange(int left, int right) {
        removeRangeInTree(left, right, head);
    }

    public boolean queryToTree(int left, int right, SegmentTreeNode root){
        if(root == null){
            return false;
        }
        if(root.isTracked){
            return true;
        }
        
        if(root.lval == left && root.rval == right){
            return root.isTracked;
        }
        
        int mid = (root.rval+root.lval)/2;
        
        if(left >= mid ){
            return queryToTree(left, right, root.right);
        } else if(right <= mid){
            return queryToTree(left, right, root.left);
        } else{
            return queryToTree(left, mid, root.left) && queryToTree(mid, right, root.right);
        }
    }

    public void removeRangeInTree(int left, int right, SegmentTreeNode root){
        
        if(root.lval == left && root.rval == right && root.left == null){
            root.isTracked = false;
            return;
        }
        
        int mid = (root.rval+root.lval)/2;
        if(root.left == null){
            root.left = new SegmentTreeNode(root.lval, mid, root.isTracked);
            root.right = new SegmentTreeNode(mid, root.rval, root.isTracked);
        }
        
        if(left >= mid){
            removeRangeInTree(left, right, root.right);
        } else if(right <= mid && root.right != null){
            removeRangeInTree(left, right, root.left);
        } else{
            removeRangeInTree(left, mid, root.left);
            removeRangeInTree(mid, right, root.right);
        }
        
        root.isTracked = root.left.isTracked && root.right.isTracked;
    }

    public void addToRange(int left, int right, SegmentTreeNode root){
        if(root.isTracked){
            return;
        }
        
        if(root.lval == left && root.rval == right && root.left == null){
            root.isTracked = true;
            return;
        }
        
        int mid = (root.rval+root.lval)/2;
        if(root.left == null){
            root.left = new SegmentTreeNode(root.lval, mid);
            root.right = new SegmentTreeNode(mid, root.rval);
        }
        
        if(left >= mid ){
            addToRange(left, right, root.right);
        } else if(right <= mid){
            addToRange(left, right, root.left);
        } else{
            addToRange(left, mid, root.left);
            addToRange(mid, right, root.right);
        }
        
        root.isTracked = root.left.isTracked && root.right.isTracked;
    }
}

/**
 * Your RangeModule object will be instantiated and called as such:
 * RangeModule obj = new RangeModule();
 * obj.addRange(left,right);
 * boolean param_2 = obj.queryRange(left,right);
 * obj.removeRange(left,right);
 */
