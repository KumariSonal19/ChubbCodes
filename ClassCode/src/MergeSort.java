public class MergeSort {

    public static void merge(int[] arr, int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        
        int[] left = new int[n1];
        int[] right = new int[n2];

      
        for (int i = 0; i < n1; i++)
            left[i] = arr[l + i];
        for (int j = 0; j < n2; j++)
            right[j] = arr[m + 1 + j];

        
        int i = 0, j = 0;
        int k = l;

        while (i < n1 && j < n2) {
            if (left[i] <= right[j]) {
                arr[k] = left[i];
                i++;
            } else {
                arr[k] = right[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            arr[k] = left[i];
            i++;
            k++;
        }

    
        while (j < n2) {
            arr[k] = right[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using merge()
    public static void mergeSort(int[] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2; // middle index

            // Sort first and second halves
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

    public static void main(String[] args) {
        int[] arr = {1002, 110, 23445, 6789, 9807, 6455, 75432};
        System.out.println("Before Sorting:");
        for (int num : arr) {
            System.out.print(num + " ");
        }

        mergeSort(arr, 0, arr.length - 1);

        System.out.println("\nAfter Sorting:");
        for (int num : arr) {
            System.out.print(num + " ");
        }
    }
}
