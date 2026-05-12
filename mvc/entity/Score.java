package mvc.entity;

public class Score {
    private String studentId;
    private String subjectId;
    private double midTerm;
    private double finalTerm;
    private boolean isCancelled;

    public Score(String studentId, String subjectId, double midTerm, double finalTerm, boolean isCancelled) {
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.midTerm = midTerm;
        this.finalTerm = finalTerm;
        this.isCancelled = isCancelled;
    }

    // Tính điểm trung bình tự động
    public double getAvgScore() {
        return (midTerm + finalTerm) / 2.0;
    }

    public String getStudentId() { return studentId; }
    public String getSubjectId() { return subjectId; }
    public double getMidTerm() { return midTerm; }
    public double getFinalTerm() { return finalTerm; }
    public boolean isCancelled() { return isCancelled; }
}