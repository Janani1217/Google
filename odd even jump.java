You are given an integer array arr. From some starting index, you can make a series of jumps. 
The (1st, 3rd, 5th, ...) jumps in the series are called odd-numbered jumps, and the (2nd, 4th, 6th, ...) 
jumps in the series are called even-numbered jumps. Note that the jumps are numbered, not the indices.

You may jump forward from index i to index j (with i < j) in the following way:

During odd-numbered jumps (i.e., jumps 1, 3, 5, ...), you jump to the index j such that arr[i] <= arr[j] 
and arr[j] is the smallest possible value. If there are multiple such indices j, you can only jump 
to the smallest such index j.

During even-numbered jumps (i.e., jumps 2, 4, 6, ...), you jump to the index j such that 
arr[i] >= arr[j] and arr[j] is the largest possible value. If there are multiple such indices j, 
you can only jump to the smallest such index j.

It may be the case that for some index i, there are no legal jumps.
A starting index is good if, starting from that index, you can reach the end of the array 
(index arr.length - 1) by jumping some number of times (possibly 0 or more than once).

Return the number of good starting indices.




// TREEMAP : USAGE instead of monotonic stack :

class Solution {
    public int oddEvenJumps(int[] arr) {
        // Array size
        int length = arr.length;
        
        // Count good jumps
        int count = 1;
        
        // Jump possibilities table. Init false for All
        boolean[] canJumpToLargest = new boolean[length];
        boolean[] canJumpToSmaller = new boolean[length];
        
        // Define a Map to store already visited elements and its indexes
        TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
        
        // Configure last element
        map.put(arr[length-1], length-1);        
        canJumpToLargest[length-1] = true;
        canJumpToSmaller[length-1] = true;
        
        
        // Traverse the array and calculates possibilities backward.
        for (int i = length-2; i >= 0; --i) {
            // Current value
            Integer element = Integer.valueOf(arr[i]);
            
            // Calculate possibility to Jump to a Largest element
            Map.Entry<Integer, Integer> largestElement = map.ceilingEntry(element);
            if (largestElement != null)
                canJumpToLargest[i] = canJumpToSmaller[(int)largestElement.getValue()];
            
            // Calculate possibility to Jump to a Smaller element
            Map.Entry<Integer, Integer> smallerElement = map.floorEntry(element);
            if (smallerElement != null)
                canJumpToSmaller[i] = canJumpToLargest[(int)smallerElement.getValue()];            
            
            if (canJumpToLargest[i])
                count++;
            
            map.put(element, i);
        }
        
        return count;
        
    }
}
