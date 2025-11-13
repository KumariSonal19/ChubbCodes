package com.example.search;

import java.util.Arrays;

public class BinarySearchOperation {

    public int binarySearch(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }

        int left = 0;
        int right = arr.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1; 
    }

    
    public int searchInUnsortedArray(int[] arr, int target) {
        if (arr == null) return -1;
        Arrays.sort(arr);
        return binarySearch(arr, target);
    }
}
