package mvc.view;

import mvc.controller.SubjectController;
import mvc.entity.Subject;
import mvc.utils.exception.NotFoundException;
import mvc.utils.validate.CheckInput;
import java.util.List;

public class SubjectView {
    private static SubjectController subjectController = new SubjectController();

    public static void mainMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ MÔN HỌC ---");
            System.out.println("1. Hiển thị danh sách môn học");
            System.out.println("2. Thêm môn học");
            System.out.println("3. Xóa môn học");
            System.out.println("4. Sửa thông tin môn học");
            System.out.println("5. Tìm kiếm môn học theo mã");
            System.out.println("6. Quay lại menu chính");
            int choice = CheckInput.checkInt("Nhập lựa chọn: ");

            switch (choice) {
                case 1:
                    List<Subject> list = subjectController.getAll();
                    System.out.println("+------------+----------------------+----------+");
                    System.out.printf("| %-10s | %-20s | %-8s |\n", "Mã Môn", "Tên Môn", "Tín Chỉ");
                    System.out.println("+------------+----------------------+----------+");
                    for (Subject s : list) System.out.println(s);
                    System.out.println("+------------+----------------------+----------+");
                    break;
                case 2:
                    String id = CheckInput.checkString("Nhập mã môn: ");
                    String name = CheckInput.checkString("Nhập tên môn: ");
                    int credits = CheckInput.checkInt("Nhập số tín chỉ: ");
                    subjectController.add(new Subject(id, name, credits));
                    System.out.println("Hệ thống: Thêm môn học thành công!");
                    break;
                case 3:
                    String deleteId = CheckInput.checkString("Nhập mã môn cần xóa: ");

                    if (CheckInput.checkYesNo("CẢNH BÁO: Xóa môn học sẽ xóa TOÀN BỘ điểm của môn này. Bạn có chắc chắn? (Y/N): ")) {
                        try {
                            subjectController.deleteSubject(deleteId);
                            System.out.println("Hệ thống: Đã xóa môn học và toàn bộ điểm liên quan.");
                        } catch (NotFoundException e) {
                            System.out.println("Lỗi: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Hệ thống: Đã hủy thao tác xóa môn học.");
                    }
                    break;
                case 4:
                    String subId = CheckInput.checkString("Nhập mã môn cần sửa: ");
                    try {
                        Subject oldSub = subjectController.getSubjectById(subId);
                        String newName = CheckInput.checkString("Tên môn mới (Enter để giữ nguyên): ");
                        if (newName.isEmpty()) newName = oldSub.getName();

                        int cre = CheckInput.checkInt("Số tín chỉ mới (Nhập " + oldSub.getCredits() + " để giữ nguyên): ");

                        subjectController.updateSubject(subId, newName, cre);
                        System.out.println("Hệ thống: Cập nhật thành công!");
                    } catch (NotFoundException e) {
                        System.out.println("Lỗi: " + e.getMessage());
                    }
                    break;
                case 5:
                    // CHỨC NĂNG MỚI: TÌM KIẾM MÔN HỌC
                    System.out.println("\n--- TÌM KIẾM MÔN HỌC ---");
                    String searchId = CheckInput.checkString("Nhập mã môn học cần tìm: ");
                    try {
                        Subject foundSubject = subjectController.getSubjectById(searchId);
                        System.out.println("Hệ thống: Đã tìm thấy môn học!");
                        System.out.println("+------------+----------------------+----------+");
                        System.out.printf("| %-10s | %-20s | %-8s |\n", "Mã Môn", "Tên Môn", "Tín Chỉ");
                        System.out.println("+------------+----------------------+----------+");
                        System.out.println(foundSubject);
                        System.out.println("+------------+----------------------+----------+");
                    } catch (NotFoundException e) {
                        System.out.println("Lỗi: Không tìm thấy môn học nào mang mã '" + searchId + "'.");
                    }
                    break;
                case 6:
                    return;
            }
        }
    }
}