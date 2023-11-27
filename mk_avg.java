Finding MK Average

You are given two integers, m and k, and a stream of integers. 
You are tasked to implement a data structure that calculates the MKAverage for the stream.

The MKAverage can be calculated using these steps:

If the number of the elements in the stream is less than m you should consider the MKAverage 
to be -1. Otherwise, copy the last m elements of the stream to a separate container.
Remove the smallest k elements and the largest k elements from the container.

Calculate the average value for the rest of the elements rounded down to the nearest integer.

Implement the MKAverage class:

MKAverage(int m, int k) Initializes the MKAverage object with an empty stream and the two integers m and k.
void addElement(int num) Inserts a new element num into the stream.
int calculateMKAverage() Calculates and returns the MKAverage for the current stream rounded down to the nearest integer.




// focus on the ds : removedCount : handle the duplicates: Gives TLE on huge test cases :
class MKAverage {
    List<Integer> list;
    int m;
    int k;
    PriorityQueue<Integer> pq_min;
    PriorityQueue<Integer> pq_max;
    Map<Integer, Integer> removedCount; // Map to track the count of removed elements

    public MKAverage(int m, int k) {
        pq_min = new PriorityQueue<>();
        pq_max = new PriorityQueue<>(Collections.reverseOrder());

        list = new ArrayList<>();
        this.m = m;
        this.k = k;
        removedCount = new HashMap<>();
    }

    public void addElement(int num) {
        list.add(num);
    }

    public int calculateMKAverage() {
        int sz = list.size();
        if (sz < m)
            return -1;

        int s = sz - m;
        int e = sz - 1;

        findSmallest(list, s, e, k);
        findLargest(list, s, e, k);

        int sum = 0;
        int cnt = 0;

        for (int i = s; i <= e; i++) {
            int ele = list.get(i);
            if (removedCount.containsKey(ele) && removedCount.get(ele) > 0) {
                removedCount.put(ele, removedCount.get(ele) - 1);
            } else {
                sum += ele;
                cnt++;
            }
        }

        return (cnt > 0) ? sum / cnt : 0;
    }

    private void findSmallest(List<Integer> list, int s, int e, int k) {
        pq_min.clear();
        removedCount.clear();

        for (int i = s; i <= e; i++) {
            pq_min.add(list.get(i));
        }

        for (int i = 0; i < k; i++) {
            int removed = pq_min.poll();
            removedCount.put(removed, removedCount.getOrDefault(removed, 0) + 1);
        }
    }

    private void findLargest(List<Integer> list, int s, int e, int k) {
        pq_max.clear();

        for (int i = s; i <= e; i++) {
            pq_max.add(list.get(i));
        }

        for (int i = 0; i < k; i++) {
            int removed = pq_max.poll();
            removedCount.put(removed, removedCount.getOrDefault(removed, 0) + 1);
        }
    }
}













// OPTIMIZED APPROACH using treeset 
import java.util.*;

public class MKAverage {

	private int time = 0;

	private static class Node implements Comparable<Node> {

		private int val, time;

		Node(int val, int time) {
			this.val = val;
			this.time = time;
		}

		public int compareTo(Node other) {
			return this.val != other.val ? Integer.compare(this.val, other.val) : Integer.compare(this.time, other.time);
		}

		public String toString() {
			return String.format("val: %d, time: %d", val, time);
		}
	}

	private int m, k;
	private int sum;

	private Deque<Node> queue = new LinkedList<>();
	private TreeSet<Node> set = new TreeSet<>();
	private Node leftK, rightK;

	public MKAverage(int m, int k) {
		this.m = m;
		this.k = k;
	}

	private void addNode(Node elem) {
		queue.offerLast(elem);
		set.add(elem);

		if (queue.size() <= m) return;

		// if added element > leftK and added element < rightK
        // ele is within the range which contributes to the mkavg
		if (elem.compareTo(leftK) > 0 && elem.compareTo(rightK) < 0) {
			sum += elem.val;
		}
		// if added element > rightK
		if (elem.compareTo(rightK) > 0) {
			sum += rightK.val;
			rightK = set.higher(rightK);
		}
		// if added element < leftK
		if (elem.compareTo(leftK) < 0) {
			sum += leftK.val;
			leftK = set.lower(leftK);
		}
	}

	private void removeNode() {

		if (queue.size() <= m) return;

		Node elem = queue.pollFirst();

		// if removed element > leftK and removed element < rightK
		if (elem.compareTo(leftK) > 0 && elem.compareTo(rightK) < 0) {
			sum -= elem.val;
		}
		// if removed element >= rightK
		if (elem.compareTo(rightK) >= 0) {
			rightK = set.lower(rightK);
			sum -= rightK.val;
		}
		// if removed element < leftK
		if (elem.compareTo(leftK) <= 0) {
			leftK = set.higher(leftK);
			this.sum -= leftK.val;
		}

		set.remove(elem);
	}

	private void init() {
		int counter = 0;
		for (Node node : set) {
			++counter; // track of the pos of curr ele in set
			if (counter < k) {
				continue;
			} else if (counter == k) {
				leftK = node;
			} else if (counter > k && counter < m - k + 1) {
				sum += node.val;
			} else if (counter == m - k + 1) {
				rightK = node;
			} else {
				break;
			}
		}
	}

	public void addElement(int num) {

		Node elem = new Node(num, ++time);
		addNode(elem);
		removeNode();

		if (time == m) init();
	}

	public int calculateMKAverage() {
		return queue.size() < m ? -1 : sum / (m - 2 * k); // among m ele , smallest k and largest k will be removed = total ele contributing to avg
	}
}
