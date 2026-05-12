package mvc.controller;

import mvc.entity.Subject;
import mvc.service.impl.SubjectService;
import mvc.utils.exception.NotFoundException;
import java.util.List;

public class SubjectController {
    private SubjectService subjectService = new SubjectService();

    public List<Subject> getAll() { return subjectService.findAll(); }
    public void add(Subject subject) { subjectService.add(subject); }
    public void deleteSubject(String id) throws NotFoundException { subjectService.delete(id); }
    public Subject getSubjectById(String id) throws NotFoundException { return subjectService.findById(id); }

    public void updateSubject(String id, String name, int credits) throws NotFoundException {
        subjectService.update(id, name, credits);
    }

    public void saveData() { subjectService.saveToFile(); }
    public void loadData() { subjectService.loadFromFile(); }
}