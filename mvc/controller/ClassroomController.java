package mvc.controller;

import mvc.entity.Classroom;
import mvc.service.impl.ClassroomService;
import mvc.utils.exception.NotFoundException;
import java.util.List;

public class ClassroomController {
    private ClassroomService classService = new ClassroomService();

    public List<Classroom> getAll() {
        return classService.findAll();
    }

    public void add(Classroom classroom) {
        classService.add(classroom);
    }

    public void deleteClass(String id) throws NotFoundException {
        classService.delete(id);
    }

    public void updateClass(String id, String newName) throws NotFoundException {
        classService.update(id, newName);
    }

    public void saveData() { classService.saveToFile(); }
    public void loadData() { classService.loadFromFile(); }
}