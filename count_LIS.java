Given an integer array nums, return the number of longest increasing subsequences.

Notice that the sequence has to be strictly increasing.



// DP : 2 arrays : watch LC editiorail diagram for clarification



  class Solution {
    public int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return n;
        }

        int[] lengths = new int[n];
        int[] counts = new int[n];

        Arrays.fill(lengths, 1);
        Arrays.fill(counts, 1);

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    if (lengths[j] + 1 > lengths[i]) {
                        lengths[i] = lengths[j] + 1;
                        counts[i] = counts[j];
                    } else if (lengths[j] + 1 == lengths[i]) {
                        counts[i] += counts[j];
                    }
                }
            }
        }

        int maxLen = 0;
        for (int len : lengths) {
            maxLen = Math.max(maxLen, len);
        }

        int result = 0;
        for (int i = 0; i < n; i++) {
            if (lengths[i] == maxLen) {
                result += counts[i];
            }
        }

        return result;
    }
}
