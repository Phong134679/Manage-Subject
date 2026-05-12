package mvc.repository;

import mvc.entity.Score;
import java.util.ArrayList;
import java.util.List;

public class ScoreRepository {
    private static List<Score> scores = new ArrayList<>();

    public List<Score> findAll() { return scores; }

    public void addOrUpdate(Score newScore) {
        // Tìm xem đã có điểm môn này của SV này chưa, có thì đè, chưa thì thêm
        for (int i = 0; i < scores.size(); i++) {
            Score s = scores.get(i);
            if (s.getStudentId().equalsIgnoreCase(newScore.getStudentId())
                    && s.getSubjectId().equalsIgnoreCase(newScore.getSubjectId())) {
                scores.set(i, newScore);
                return;
            }
        }
        scores.add(newScore);
    }

    public void removeScoresOfSubject(String subjectId) {
        scores.removeIf(s -> s.getSubjectId().equalsIgnoreCase(subjectId));
    }

    public void removeScore(String studentId, String subjectId) {
        scores.removeIf(s -> s.getStudentId().equalsIgnoreCase(studentId)
                && s.getSubjectId().equalsIgnoreCase(subjectId));
    }
}