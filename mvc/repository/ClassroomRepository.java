package mvc.repository;

import mvc.entity.Classroom;
import java.util.ArrayList;
import java.util.List;

public class ClassroomRepository {
    private static List<Classroom> classrooms = new ArrayList<>();

    public List<Classroom> findAll() {
        return classrooms;
    }

    public void add(Classroom classroom) {
        classrooms.add(classroom);
    }

    public Classroom findById(String id) {
        for (Classroom c : classrooms) {
            if (c.getId().equalsIgnoreCase(id)) return c;
        }
        return null;
    }

    public void delete(Classroom classroom) {
        classrooms.remove(classroom);
    }
}