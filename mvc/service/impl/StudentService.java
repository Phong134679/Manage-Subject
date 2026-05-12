package mvc.service.impl;

import mvc.entity.Student;
import mvc.repository.StudentRepository;
import mvc.service.IService;
import mvc.utils.FileHelper.FileHelper;
import mvc.utils.exception.NotFoundException;
import java.util.ArrayList;
import java.util.List;

public class StudentService implements IService<Student> {
    private StudentRepository studentRepo = new StudentRepository();
    public Student findById(String id) throws NotFoundException {
        Student target = studentRepo.findById(id);
        if (target == null) {
            throw new NotFoundException("Không tìm thấy sinh viên mã: " + id);
        }
        return target;
    }
    @Override
    public List<Student> findAll() {
        return studentRepo.findAll();
    }

    public List<Student> findByClassroomId(String classId) {
        List<Student> result = new ArrayList<>();
        for (Student s : studentRepo.findAll()) {
            if (s.getClassroomId().equalsIgnoreCase(classId)) {
                result.add(s);
            }
        }
        return result;
    }

    @Override
    public void add(Student student) {
        studentRepo.add(student);
    }

    @Override
    public void delete(String id) throws NotFoundException {
        Student target = studentRepo.findById(id);
        if (target == null) {
            throw new NotFoundException("Không tìm thấy sinh viên mã: " + id);
        }
        studentRepo.delete(target);
        // Sau này nối ScoreboardService vào đây để xóa điểm
    }

    public void update(String id, Student newInfo) throws NotFoundException {
        Student target = studentRepo.findById(id);
        if (target == null) throw new NotFoundException("Không tìm thấy sinh viên mã: " + id);

        target.setName(newInfo.getName());
        target.setDob(newInfo.getDob());
        target.setSex(newInfo.getSex());
    }

    public void saveToFile() {
        List<String> lines = new ArrayList<>();

        lines.add("ID,Name,DateOfBirth,Gender,ClassroomID");

        for (Student s : studentRepo.findAll()) {
            lines.add(s.getId() + "," +
                    s.getName() + "," +
                    s.getDob() + "," +
                    s.getSex() + "," +
                    s.getClassroomId());
        }

        FileHelper.writeToFile("students.csv", lines);
    }

    public void loadFromFile() {
        List<String> lines = FileHelper.readFromFile("students.csv");
        if (lines == null || lines.size() <= 1) return;
        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split(",");
            if (parts.length == 5) {
                Student student = new Student(
                        parts[0], // ID
                        parts[1], // Name
                        parts[2], // DoB
                        Integer.parseInt(parts[3]), // Sex
                        parts[4]  // ClassroomId
                );
                studentRepo.add(student);
            }
        }
    }
}