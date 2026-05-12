package mvc.service.impl;

import mvc.entity.Subject;
import mvc.repository.ScoreRepository;
import mvc.repository.SubjectRepository;
import mvc.service.IService;
import mvc.utils.FileHelper.FileHelper;
import mvc.utils.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class SubjectService implements IService<Subject> {
    private SubjectRepository subjectRepo = new SubjectRepository();
    private ScoreRepository scoreRepo = new ScoreRepository();

    @Override
    public List<Subject> findAll() {
        return subjectRepo.findAll();
    }

    @Override
    public void add(Subject subject) {
        subjectRepo.add(subject);
    }

    @Override
    public void delete(String id) throws NotFoundException {
        Subject target = subjectRepo.findById(id);
        if (target == null) {
            throw new NotFoundException("Không tìm thấy môn học mã: " + id);
        }
        // Xóa dây chuyền: Xóa môn học -> Xóa toàn bộ điểm của môn đó
        scoreRepo.removeScoresOfSubject(id);
        subjectRepo.delete(target);
    }

    public Subject findById(String id) throws NotFoundException {
        Subject target = subjectRepo.findById(id);
        if (target == null) throw new NotFoundException("Không tìm thấy môn học.");
        return target;
    }

    public void update(String id, String newName, int newCredits) throws NotFoundException {
        Subject target = subjectRepo.findById(id);
        if (target == null) throw new NotFoundException("Không tìm thấy môn học mã: " + id);
        target.setName(newName);
        target.setCredits(newCredits);
    }

    public void saveToFile() {
        List<String> lines = new ArrayList<>();

        lines.add("SubjectID,SubjectName,Credits");

        for (Subject s : subjectRepo.findAll()) {
            lines.add(s.getId() + "," + s.getName() + "," + s.getCredits());
        }
        FileHelper.writeToFile("subjects.csv", lines);
    }

    public void loadFromFile() {
        List<String> lines = FileHelper.readFromFile("subjects.csv");

        if (lines == null || lines.size() <= 1) return;

        for (int i = 1; i < lines.size(); i++) {
            String line = lines.get(i);
            String[] parts = line.split(",");

            if (parts.length == 3) {
                try {
                    String id = parts[0];
                    String name = parts[1];
                    int credits = Integer.parseInt(parts[2]);

                    subjectRepo.add(new Subject(id, name, credits));
                } catch (NumberFormatException e) {
                    System.err.println("Lỗi dữ liệu tại dòng " + (i + 1) + ": " + e.getMessage());
                }
            }
        }
    }
}