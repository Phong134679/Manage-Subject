package mvc.service.impl;

import mvc.entity.Classroom;
import mvc.entity.Student;
import mvc.repository.ClassroomRepository;
import mvc.repository.StudentRepository;
import mvc.service.IService;
import mvc.utils.FileHelper.FileHelper;
import mvc.utils.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class ClassroomService implements IService<Classroom> {
    private ClassroomRepository classRepo = new ClassroomRepository();
    private StudentRepository studentRepo = new StudentRepository();

    @Override
    public List<Classroom> findAll() {
        return classRepo.findAll();
    }

    @Override
    public void add(Classroom classroom) {
        classRepo.add(classroom);
    }

    @Override
    public void delete(String id) throws NotFoundException {
        Classroom target = classRepo.findById(id);
        if (target == null) {
            throw new NotFoundException("Không tìm thấy lớp học mã: " + id);
        }

        // Logic dây chuyền: Xóa lớp -> Phải xóa toàn bộ SV thuộc lớp đó
        List<Student> allStudents = studentRepo.findAll();
        allStudents.removeIf(s -> s.getClassroomId().equalsIgnoreCase(id));

        classRepo.delete(target);
    }

    public void update(String id, String newName) throws NotFoundException {
        Classroom target = classRepo.findById(id);
        if (target == null) throw new NotFoundException("Không tìm thấy lớp mã: " + id);
        target.setName(newName);
    }

    public void saveToFile() {
        List<String> lines = new ArrayList<>();
        lines.add("ClassroomID,ClassroomName");

        for (Classroom c : classRepo.findAll()) {
            lines.add(c.getId() + "," + c.getName());
        }
        FileHelper.writeToFile("classrooms.csv", lines);
    }

    public void loadFromFile() {
        List<String> lines = FileHelper.readFromFile("classrooms.csv");

        if (lines == null || lines.size() <= 1) {
            return;
        }

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split(",");

            if (parts.length == 2) {
                classRepo.add(new Classroom(parts[0], parts[1]));
            }
        }
    }
}