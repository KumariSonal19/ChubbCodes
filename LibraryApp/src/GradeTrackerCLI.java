import java.util.*;


class Person {
    private final String name;
    Person(String name) { this.name = name; }
    public String getName() { return name; }
}

class Student extends Person {
    private final String rollNo;
    // courseCode -> CourseGrade
    private final Map<String, CourseGrade> grades = new HashMap<>();
    Student(String rollNo, String name) { super(name); this.rollNo = rollNo; }
    public String getRollNo() { return rollNo; }
    public void addGrade(CourseGrade g) { grades.put(g.courseCode(), g); }
    public double gpa() {
        if (grades.isEmpty()) return 0.0;
        double total = 0, credits = 0;
        for (CourseGrade g : grades.values()) {
            total += g.points() * g.credits();
            credits += g.credits();
        }
        return credits == 0 ? 0 : total / credits;
    }
    public Collection<CourseGrade> getGrades() { return grades.values(); }
    @Override public String toString() { return rollNo + " | " + getName() + " | GPA: " + String.format("%.2f", gpa()); }
}

// Immutable record-like class
final class CourseGrade {
    private final String courseCode;
    private final int credits;
    private final String letter; // A+, A, B+, B, C, D, F

    CourseGrade(String courseCode, int credits, String letter) {
        this.courseCode = courseCode.toUpperCase(Locale.ROOT);
        this.credits = credits;
        this.letter = letter.toUpperCase(Locale.ROOT);
        if (credits <= 0) throw new InvalidGradeException("Credits must be > 0");
        if (!POINTS.containsKey(this.letter)) throw new InvalidGradeException("Invalid letter grade: " + letter);
    }
    public String courseCode() { return courseCode; }
    public int credits() { return credits; }
    public String letter() { return letter; }
    public double points() { return POINTS.get(letter); }

    private static final Map<String, Double> POINTS = Map.ofEntries(
            Map.entry("A+", 10.0), Map.entry("A", 9.0),
            Map.entry("B+", 8.0), Map.entry("B", 7.0),
            Map.entry("C", 6.0), Map.entry("D", 5.0),
            Map.entry("F", 0.0)
    );

    @Override public String toString() {
        return courseCode + " (" + credits + " cr) : " + letter;
    }
}

class GradeTracker {
    private final Map<String, Student> students = new HashMap<>();

    public void addStudent(String roll, String name) {
        if (students.containsKey(roll)) throw new BusinessException("Student already exists: " + roll);
        students.put(roll, new Student(roll, name));
    }
    public void addGrade(String roll, String course, int credits, String letter) {
        Student s = students.get(roll);
        if (s == null) throw new StudentNotFoundException("Student not found: " + roll);
        s.addGrade(new CourseGrade(course, credits, letter));
    }
    public Student get(String roll) {
        Student s = students.get(roll);
        if (s == null) throw new StudentNotFoundException("Student not found: " + roll);
        return s;
    }
    public List<Student> topN(int n) {
        return students.values().stream()
                .sorted(Comparator.comparingDouble(Student::gpa).reversed())
                .limit(Math.max(0, n)).toList();
    }
    public Collection<Student> all() { return students.values(); }
}

public class GradeTrackerCLI {
    public static void main(String[] args) {
        GradeTracker gt = new GradeTracker();
        Scanner sc = new Scanner(System.in);
        System.out.println("=== Student Grade Tracker ===");
        loop:
        while (true) {
            System.out.println("\n1) Add Student  2) Add Grade  3) GPA  4) Top N  5) List Students  6) View Grades  0) Exit");
            System.out.print("Choose: ");
            String ch = sc.nextLine().trim();
            try {
                switch (ch) {
                    case "1" -> {
                        System.out.print("Roll No: "); String roll = sc.nextLine().trim();
                        System.out.print("Name: "); String name = sc.nextLine().trim();
                        gt.addStudent(roll, name);
                        System.out.println("Added student " + roll);
                    }
                    case "2" -> {
                        System.out.print("Roll No: "); String roll = sc.nextLine().trim();
                        System.out.print("Course Code: "); String cc = sc.nextLine().trim();
                        System.out.print("Credits: "); int cr = Integer.parseInt(sc.nextLine().trim());
                        System.out.print("Score (A+,A,B+,B,C,D,F): "); String lt = sc.nextLine().trim();
                        gt.addGrade(roll, cc, cr, lt);
                        System.out.println("Grade added.");
                    }
                    case "3" -> {
                        System.out.print("Roll No: "); String roll = sc.nextLine().trim();
                        System.out.printf("CGPA: %.2f%n", gt.get(roll).gpa());
                    }
                    case "4" -> {
                        System.out.print("N: "); int n = Integer.parseInt(sc.nextLine().trim());
                        List<Student> top = gt.topN(n);
                        top.forEach(System.out::println);
                    }
                    case "5" -> gt.all().forEach(System.out::println);
                    case "6" -> {
                        System.out.print("Roll No: "); String roll = sc.nextLine().trim();
                        gt.get(roll).getGrades().forEach(System.out::println);
                    }
                    case "0" -> { System.out.println("Goodbye!"); break loop; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (BusinessException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e);
            }
        }
        sc.close();
    }
}
