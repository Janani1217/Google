The median is the middle value in an ordered integer list. 
If the size of the list is even, there is no middle value, and the median is the mean of the two middle values.

For example, for arr = [2,3,4], the median is 3.
For example, for arr = [2,3], the median is (2 + 3) / 2 = 2.5.
Implement the MedianFinder class:

MedianFinder() initializes the MedianFinder object.
void addNum(int num) adds the integer num from the data stream to the data structure.
double findMedian() returns the median of all elements so far.



// USE OF 2 HEAPS **** 


class MedianFinder {
    PriorityQueue<Integer> maxHeap; // left ele in sorted order
    PriorityQueue<Integer> minHeap; // rt ele in sorted order
    
    public MedianFinder() {
        maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        minHeap = new PriorityQueue<>();
    }
    
    public void addNum(int num) {
        
        if(!maxHeap.isEmpty() && num>maxHeap.peek())
            minHeap.add(num);
        else
            maxHeap.add(num);
        
        // MAINTAIN EQUAL NUMBERS IN BOTH BLOCKSS
        if(minHeap.size() + 1 < maxHeap.size())
            minHeap.add(maxHeap.poll());
        else if(minHeap.size() > maxHeap.size() + 1)
            maxHeap.add(minHeap.poll());
    }
    
    public double findMedian() {
        if(minHeap.size() == maxHeap.size()){
            return (((double) (minHeap.peek() + maxHeap.peek()))/2);
        }
        return minHeap.size()>maxHeap.size() ? minHeap.peek() : maxHeap.peek();
    }
}

/**
 * Your MedianFinder object will be instantiated and called as such:
 * MedianFinder obj = new MedianFinder();
 * obj.addNum(num);
 * double param_2 = obj.findMedian();
 */
