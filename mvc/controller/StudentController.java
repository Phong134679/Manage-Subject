package mvc.controller;

import mvc.entity.Student;
import mvc.service.impl.StudentService;
import mvc.utils.exception.NotFoundException;
import java.util.List;

public class StudentController {
    private StudentService studentService = new StudentService();

    public List<Student> getStudentsByClass(String classId) {
        return studentService.findByClassroomId(classId);
    }

    public void add(Student student) {
        studentService.add(student);
    }

    public void deleteStudent(String id) throws NotFoundException {
        studentService.delete(id);
    }
    public Student getStudentById(String id) throws NotFoundException {
        return studentService.findById(id);
    }

    public void updateStudent(String id, Student info) throws NotFoundException {
        studentService.update(id, info);
    }

    public void saveData() { studentService.saveToFile(); }
    public void loadData() { studentService.loadFromFile(); }
}