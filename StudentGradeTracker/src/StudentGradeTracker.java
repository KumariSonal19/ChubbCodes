import java.util.*;

class Student {
    String name;
    int rollNo;

    public Student(String name, int rollNo) {
        this.name = name;
        this.rollNo = rollNo;
    }
}

class GradeStudent extends Student {
    Map<String, Integer> grades = new HashMap<>();

    public GradeStudent(String name, int rollNo) {
        super(name, rollNo);
    }

    void addGrade(String subject, int marks) {
        grades.put(subject, marks);
    }

    void displayGrades() {
        System.out.println("Grades for " + name + ":");
        for (String subject : grades.keySet()) {
            System.out.println(subject + " -> " + grades.get(subject));
        }
    }
}

public class StudentGradeTracker {
    public static void main(String[] args) {
        GradeStudent s1 = new GradeStudent("Alice", 101);
        GradeStudent s2 = new GradeStudent("Bob", 102);

        s1.addGrade("Math", 90);
        s1.addGrade("Science", 85);

        s2.addGrade("Math", 75);
        s2.addGrade("Science", 80);

        s1.displayGrades();
        s2.displayGrades();
    }
}
