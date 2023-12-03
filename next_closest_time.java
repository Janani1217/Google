Given a time represented in the format "HH:MM", form the next closest time by reusing the current digits. 
There is no limit on how many times a digit can be reused.

You may assume the given input string is always valid. 
For example, "01:34", "12:09" are all valid. "1:34", "12:9" are all invalid.

 

Example 1:

Input: time = "19:34"
Output: "19:39"
Explanation: The next closest time choosing from digits 1, 9, 3, 4, is 19:39, which occurs 5 minutes later.
It is not 19:33, because this occurs 23 hours and 59 minutes later.
Example 2:

Input: time = "23:59"
Output: "22:22"
Explanation: The next closest time choosing from digits 2, 3, 5, 9, is 22:22.
It may be assumed that the returned time is next day's time since it is smaller than the input time numerically.







class Solution {
    public String nextClosestTime(String time) {
        HashSet<Integer> hs = new HashSet<>();
        int hh = Integer.parseInt(time.substring(0,2));
        int mm = Integer.parseInt(time.substring(3));

        // store all int digits into the set:
        hs.add(hh/10);
        hs.add(hh%10);
        hs.add(mm/10);
        hs.add(mm%10);

        // 1. find the mm closest :
        int mmClosest = solve(mm, hs, 60);
        int hhClosest = hh;
        if(mmClosest <= mm) {
            // u have moved to next day -> change the hour:
            hhClosest = solve(hh, hs, 24);
        }

        String mRes = (mmClosest <= 9 ? "0" : "") + mmClosest;
        String hRes = (hhClosest <= 9 ? "0" : "") + hhClosest;

        return hRes + ":" + mRes;
    }

    private int solve(int t, HashSet<Integer>hs, int max) {
        while(true) {
            t++; // make sure it moves in incr order till max number
            if(t == max)
                t = 0; // start from 0 again

            if(hs.contains(t/10) && hs.contains(t%10))
                return t;
        }
    }
}
