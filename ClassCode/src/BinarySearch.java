public class BinarySearch {
    public static void main(String[] args) {
        int[] arr = {2, 6, 7, 17, 29, 31, 67, 89, 101};
        int target = 8;
        
        int left = 0;
        int right = arr.length - 1;
        int index = -1;

        while (left <= right) {
            int mid = ( left + right ) / 2;

            if (arr[mid] == target) {
                index = mid;
                break;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        if (index != -1)
            System.out.println("Binary Search: Found at index " + index);
        else
            System.out.println("Binary Search: Not found");
    }
}
