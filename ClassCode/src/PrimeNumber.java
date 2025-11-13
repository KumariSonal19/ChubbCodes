public class PrimeNumber {
    
    public static boolean isPrime(int n, int i) {
        // Base cases
        if (n <= 2)
            return (n == 2);
        if (n % i == 0)
            return false;
        if (i * i > n)
            return true;
        return isPrime(n, i + 1);
    }

    public static void main(String[] args) {
        System.out.println("Prime numbers up to 500:");

        
        for (int num = 2; num <= 500; num++) {
            if (isPrime(num, 2))
                System.out.print(num + " ");
        }
    }
}