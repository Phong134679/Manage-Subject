package mvc.repository;

import mvc.entity.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    private static List<Student> students = new ArrayList<>();

    public List<Student> findAll() {
        return students;
    }

    public void add(Student student) {
        students.add(student);
    }

    public Student findById(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) return s;
        }
        return null;
    }

    public void delete(Student student) {
        students.remove(student);
    }
}