package mvc.service.impl;

import mvc.entity.Score;
import mvc.repository.ScoreRepository;
import mvc.utils.FileHelper.FileHelper;

import java.util.ArrayList;
import java.util.List;

public class ScoreService {
    private ScoreRepository scoreRepo = new ScoreRepository();
    private final String CSV_SEPARATOR = ",";

    public void exportCSV(String subjectId, String path) {
        List<Score> scores = new ArrayList<>();
        for (Score s : scoreRepo.findAll()) {
            if (s.getSubjectId().equalsIgnoreCase(subjectId)) {
                scores.add(s);
            }
        }

        List<String> lines = new ArrayList<>();
        // Thêm tiêu đề cho file CSV
        lines.add("MSSV,Mã Môn,Giữa Kỳ,Cuối Kỳ,Hủy Môn (true/false)");

        for (Score s : scores) {
            String line = String.format("%s%s%s%s%.1f%s%.1f%s%b",
                    s.getStudentId(), CSV_SEPARATOR,
                    s.getSubjectId(), CSV_SEPARATOR,
                    s.getMidTerm(), CSV_SEPARATOR,
                    s.getFinalTerm(), CSV_SEPARATOR,
                    s.isCancelled());
            lines.add(line);
        }
        FileHelper.writeToFile(path, lines);
    }

    public void importCSV(String path) {
        List<String> lines = FileHelper.readFromFile(path);
        if (lines.isEmpty()) return;

        // Bỏ qua dòng tiêu đề đầu tiên
        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(CSV_SEPARATOR);
            if (parts.length >= 5) {
                try {
                    String studentId = parts[0].trim();
                    String subjectId = parts[1].trim();
                    double mid = Double.parseDouble(parts[2].trim());
                    double fin = Double.parseDouble(parts[3].trim());
                    boolean cancelled = Boolean.parseBoolean(parts[4].trim());

                    Score score = new Score(studentId, subjectId, mid, fin, cancelled);
                    scoreRepo.addOrUpdate(score);
                } catch (Exception e) {
                    System.out.println("Lỗi định dạng tại dòng " + (i + 1) + ": " + lines.get(i));
                }
            }
        }
    }

    public List<Score> getScoresBySubject(String subjectId) {
        List<Score> result = new ArrayList<>();
        for (Score s : scoreRepo.findAll()) {
            if (s.getSubjectId().equalsIgnoreCase(subjectId)) {
                result.add(s);
            }
        }
        return result;
    }

    public void saveScore(Score score) {
        scoreRepo.addOrUpdate(score);
    }

    public void deleteScore(String studentId, String subjectId) {
        scoreRepo.removeScore(studentId, subjectId);
    }

    public void saveAllToFile() {
        List<String> lines = new ArrayList<>();
        lines.add("MSSV,Mã Môn,Giữa Kỳ,Cuối Kỳ,Hủy Môn (true/false)");
        for (Score s : scoreRepo.findAll()) {
            String line = String.format("%s%s%s%s%.1f%s%.1f%s%b",
                    s.getStudentId(), CSV_SEPARATOR,
                    s.getSubjectId(), CSV_SEPARATOR,
                    s.getMidTerm(), CSV_SEPARATOR,
                    s.getFinalTerm(), CSV_SEPARATOR,
                    s.isCancelled());
            lines.add(line);
        }
        FileHelper.writeToFile("scores.csv", lines);
    }

    public void loadAllFromFile() {
        importCSV("scores.csv");
    }
}