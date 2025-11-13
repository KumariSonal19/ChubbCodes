import java.util.*;


public class FixedProgram {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>(List.of(10, 20, 30));
        // Safe fetch
        System.out.println("First: " + safeGet(nums, 0).orElse(-1));
        System.out.println("Fourth (safe): " + safeGet(nums, 3).orElse(-1));

        // Robust input read
        try (Scanner sc = new Scanner(System.in)) {
            System.out.print("Enter integer to add: ");
            String line = sc.nextLine().trim();
            int val = Integer.parseInt(line);
            nums.add(val);
        } catch (NumberFormatException e) {
            System.out.println("Invalid number, skipping add.");
        }

        // No NPE: stream handles empty list; defensive copy for iteration
        int sum = nums.stream().mapToInt(Integer::intValue).sum();
        System.out.println("Sum: " + sum);
    }

    static Optional<Integer> safeGet(List<Integer> list, int idx) {
        if (list == null || idx < 0 || idx >= list.size()) return Optional.empty();
        return Optional.of(list.get(idx));
    }
}
