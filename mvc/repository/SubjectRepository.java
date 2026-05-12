package mvc.repository;

import mvc.entity.Subject;
import java.util.ArrayList;
import java.util.List;

public class SubjectRepository {
    private static List<Subject> subjects = new ArrayList<>();

    public List<Subject> findAll() { return subjects; }
    public void add(Subject subject) { subjects.add(subject); }
    public void delete(Subject subject) { subjects.remove(subject); }

    public Subject findById(String id) {
        for (Subject s : subjects) {
            if (s.getId().equalsIgnoreCase(id)) return s;
        }
        return null;
    }
}