package mvc.controller;

import mvc.entity.Score;
import mvc.service.impl.ScoreService;
import java.util.List;

public class ScoreController {
    private ScoreService scoreService = new ScoreService();

    public List<Score> getScoresBySubject(String subjectId) {
        return scoreService.getScoresBySubject(subjectId);
    }

    public void saveScore(Score score) {
        scoreService.saveScore(score);
    }

    public void deleteScore(String studentId, String subjectId) {
        scoreService.deleteScore(studentId, subjectId);
    }

    public void exportToCSV(String subjectId, String path) {
        scoreService.exportCSV(subjectId, path);
    }

    public void importFromCSV(String path) {
        scoreService.importCSV(path);
    }

    public void saveData() { scoreService.saveAllToFile(); }
    public void loadData() { scoreService.loadAllFromFile(); }
}