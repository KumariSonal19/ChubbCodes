public class LinearSearch {
    public static void main(String[] args) {
        int[] arr = {2, 6, 7, 17, 29, 31, 67, 89, 101};
        int target = 8;
        int index = -1;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) {
                index = i;
                break;
            }
        }

        if (index != -1)
            System.out.println("Linear Search: Found at index " + index);
        else
            System.out.println("Linear Search: Not found");
    }
}
