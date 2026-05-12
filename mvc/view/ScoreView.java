package mvc.view;

import mvc.controller.ScoreController;
import mvc.controller.StudentController;
import mvc.controller.SubjectController;
import mvc.entity.Score;
import mvc.entity.Student;
import mvc.entity.Subject;
import mvc.utils.exception.NotFoundException;
import mvc.utils.validate.CheckInput;
import java.util.List;

public class ScoreView {
    private static ScoreController scoreController = new ScoreController();
    private static SubjectController subjectController = new SubjectController();
    private static StudentController studentController = new StudentController();

    public static void mainMenu() {
        String subjectId = CheckInput.checkString("\nNhập Mã Môn Học cần quản lý điểm: ");
        Subject subject;
        try {
            subject = subjectController.getSubjectById(subjectId);
        } catch (NotFoundException e) {
            System.out.println("Lỗi: " + e.getMessage());
            return;
        }

        while (true) {
            System.out.println("\n--- QUẢN LÝ BẢNG ĐIỂM MÔN: " + subject.getName().toUpperCase() + " ---");
            System.out.println("1. Xem bảng điểm hiện tại");
            System.out.println("2. Nhập/Sửa điểm trực tiếp (Thủ công)");
            System.out.println("3. Xóa điểm sinh viên");
            System.out.println("4. Nhập dữ liệu từ file CSV"); //import trực tiếp bảng điểm có sẵn
            System.out.println("5. Xuất bảng điểm ra file CSV");
            System.out.println("6. Quay lại menu chính");
            int choice = CheckInput.checkInt("Nhập lựa chọn: ");

            switch (choice) {
                case 1:
                    List<Score> scores = scoreController.getScoresBySubject(subjectId);
                    System.out.println("+------------+----------------------+--------+--------+--------+------------+");
                    System.out.printf("| %-10s | %-20s | %-6s | %-6s | %-6s | %-10s |\n",
                            "MSSV", "Tên SV", "Giữa Kỳ", "Cuối Kỳ", "TB", "Trạng thái");
                    System.out.println("+------------+----------------------+--------+--------+--------+------------+");
                    for (Score s : scores) {
                        String studentName = "Không xác định";
                        try {
                            Student st = studentController.getStudentById(s.getStudentId());
                            studentName = st.getName();
                        } catch (NotFoundException ignored) {} // Đã xóa SV nhưng còn kẹt điểm (nếu quên cascade)

                        String status = s.isCancelled() ? "[Đã hủy]" : "Bình thường";
                        System.out.printf("| %-10s | %-20s | %-6.1f | %-6.1f | %-6.1f | %-10s |\n",
                                s.getStudentId(), studentName, s.getMidTerm(), s.getFinalTerm(), s.getAvgScore(), status);
                    }
                    System.out.println("+------------+----------------------+--------+--------+--------+------------+");
                    break;

                case 2:
                    String studentId = CheckInput.checkString("Nhập MSSV: ");
                    try {
                        studentController.getStudentById(studentId); // Verify SV có tồn tại
                        double mid = (double) CheckInput.checkInt("Nhập điểm giữa kỳ (0-10): ");
                        double fin = (double) CheckInput.checkInt("Nhập điểm cuối kỳ (0-10): ");

                        scoreController.saveScore(new Score(studentId, subjectId, mid, fin, false));
                        System.out.println("Hệ thống: Đã cập nhật điểm thành công.");
                    } catch (NotFoundException e) {
                        System.out.println("Lỗi: " + e.getMessage() + " Vui lòng thêm sinh viên vào hệ thống trước.");
                    }
                    break;
                case 3:
                    String delStudentId = CheckInput.checkString("Nhập MSSV cần xóa điểm: ");

                    if (CheckInput.checkYesNo("Bạn có chắc chắn muốn xóa/hủy điểm môn này của sinh viên " + delStudentId + "? (Y/N): ")) {
                        scoreController.deleteScore(delStudentId, subjectId);
                        System.out.println("Hệ thống: Đã xóa/hủy điểm môn này của sinh viên.");
                    } else {
                        System.out.println("Hệ thống: Đã hủy thao tác xóa điểm.");
                    }
                    break;
                case 4:
                    System.out.println("\n--- NHẬP ĐIỂM TỪ FILE ---");
                    String importPath = CheckInput.checkString("Nhập đường dẫn file CSV (VD: data/scores.csv): ");
                    scoreController.importFromCSV(importPath);
                    System.out.println("Hệ thống: Quá trình nhập dữ liệu từ file hoàn tất.");
                    break;
                case 5:
                    System.out.println("\n--- XUẤT BẢNG ĐIỂM ---");
                    String exportFileName = "scores_" + subjectId + ".csv";
                    scoreController.exportToCSV(subjectId, exportFileName);
                    System.out.println("Hệ thống: Đã xuất file thành công với tên: " + exportFileName);
                    break;
                case 6:
                    return;
            }
        }
    }
}