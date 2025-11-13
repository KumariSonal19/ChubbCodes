package com.example.search;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class BinarySearchTest {

    @Test
    public void testElementFound() {
        BinarySearchOperation obj = new BinarySearchOperation();
        int[] arr = {1, 3, 5, 7, 9, 11};
        int index = obj.binarySearch(arr, 7);
        assertEquals(3, index, "7 should be found at index 3");
    }

    @Test
    public void testElementNotFound() {
        BinarySearchOperation obj = new BinarySearchOperation();
        int[] arr = {2, 4, 6, 8, 10};
        int index = obj.binarySearch(arr, 5);
        assertEquals(-1, index, "5 is not present in the array");
    }

    @Test
    public void testEmptyArray() {
        BinarySearchOperation obj = new BinarySearchOperation();
        int[] arr = {};
        int index = obj.binarySearch(arr, 3);
        assertEquals(-1, index);
    }

    @Test
    public void testNullArray() {
        BinarySearchOperation obj = new BinarySearchOperation();
        int index = obj.binarySearch(null, 3);
        assertEquals(-1, index);
    }

    @Test
    public void testUnsortedArraySearch() {
        BinarySearchOperation obj = new BinarySearchOperation();
        int[] arr = {8, 2, 9, 1, 5};
        int index = obj.searchInUnsortedArray(arr, 5);
        assertTrue(index >= 0, "Element 5 should be found after sorting");
    }

    @Test
    public void testNotNullInstance() {
        BinarySearchOperation obj = new BinarySearchOperation();
        assertNotNull(obj);
    }
}
