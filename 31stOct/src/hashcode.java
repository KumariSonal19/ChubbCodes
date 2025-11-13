class Student {
    String name;
    int rollNo;

    Student(String name, int rollNo) {
        this.name = name;
        this.rollNo = rollNo;
    }
    @Override
    public int hashCode() {
        return rollNo;  
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return rollNo == s.rollNo && name.equals(s.name);
    }
}

public class hashcode {
    public static void main(String[] args) {
        Student s1 = new Student("Sonal", 101);
        Student s2 = new Student("Sonal", 101);

        System.out.println("s1 hashCode: " + s1.hashCode());
        System.out.println("s2 hashCode: " + s2.hashCode());
        System.out.println("Are they equal? " + s1.equals(s2));
        
    }
}
