package Ihs.SortsAlgo;

import Ihs.SortingVisualizer;


public class MergeSort implements Runnable {

    public void run() {
        Integer[] toBeSorted = SortingVisualizer.toBeSorted;
        inPlaceSort(toBeSorted);
        SortingVisualizer.isSorting = false;
    }
    public void inPlaceSort(Integer[] x) {
        inPlaceSort(x, 0, x.length - 1);
    }

    private void inPlaceSort(Integer[] x, int first, int last) {
        int mid, left, right;
        int temp;

        if(first >= last) return;

        mid = (first + last) / 2;

        inPlaceSort(x,  first, mid);
        inPlaceSort(x, mid+1, last);

        left = first;
        right = mid + 1;

        if(x[mid] <= x[right])
            return;

        while(left <= mid && right <= last) {
            if(x[left] <= x[right])
                left++;
            else
            {
                temp = x[right]; // it will move to [left]
                for(int i = right-left;i > 0; i--) {
                    x[left + i] = x[left + i - 1];
                }
                x[left] = temp;
                // Evertyhing has moved up by one
                left++; mid++; right++;

            }
            SortingVisualizer.frame.reDrawArray(x, mid, right, left);
            try {
                Thread.sleep(SortingVisualizer.sleep);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
