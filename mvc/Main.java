package mvc;

import mvc.controller.ClassroomController;
import mvc.controller.ScoreController;
import mvc.controller.StudentController;
import mvc.controller.SubjectController;
import mvc.utils.validate.CheckInput;
import mvc.view.ClassroomView;
import mvc.view.ScoreView;
import mvc.view.StudentView;
import mvc.view.SubjectView;

public class Main {
    private static ClassroomController classController = new ClassroomController();
    private static StudentController studentController = new StudentController();
    private static SubjectController subjectController = new SubjectController();
    private static ScoreController scoreController = new ScoreController();

    public static void main(String[] args) {
        classController.loadData();
        studentController.loadData();
        subjectController.loadData();
        scoreController.loadData();
        System.out.println("Hệ thống: Khởi tạo dữ liệu thành công!\n");

        while (true) {
            System.out.println("\n===== MENU CHÍNH =====");
            System.out.println("1. Quản lý Lớp học");
            System.out.println("2. Quản lý Sinh viên");
            System.out.println("3. Quản lý Môn học ");
            System.out.println("4. Quản lý Điểm");
            System.out.println("5. Lưu dữ liệu và Thoát");

            int choice = CheckInput.checkInt("Nhập lựa chọn: ");
            switch (choice) {
                case 1: ClassroomView.mainMenu(); break;
                case 2: StudentView.mainMenu(); break;
                case 3: SubjectView.mainMenu(); break;
                case 4: ScoreView.mainMenu(); break;
                case 5:
                    // LƯU DỮ LIỆU TRƯỚC KHI TẮT
                    System.out.println("Đang lưu toàn bộ dữ liệu...");
                    classController.saveData();
                    studentController.saveData();
                    subjectController.saveData();
                    scoreController.saveData();
                    System.out.println("Lưu dữ liệu thành công. Tạm biệt!");
                    System.exit(0);
                default:
                    System.out.println("Vui lòng chọn lại.");
            }
        }
    }
}