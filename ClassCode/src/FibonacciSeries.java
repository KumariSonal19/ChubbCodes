public class FibonacciSeries {
    public static void main(String[] args) {
        int n = 10; // how many Fibonacci numbers you want
        int a = 0, b = 1;

        System.out.print("Fibonacci Series: " + a + " " + b);

        for(int i = 2; i < n; i++) {
            int next = a + b;
            System.out.print(" " + next);
            a = b;
            b = next;
        }
    }
}
