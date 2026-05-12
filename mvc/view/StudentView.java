package mvc.view;

import mvc.controller.StudentController;
import mvc.entity.Student;
import mvc.utils.exception.NotFoundException;
import mvc.utils.validate.CheckInput;
import java.util.List;

public class StudentView {
    private static StudentController studentController = new StudentController();

    public static void mainMenu() {
        String classId = CheckInput.checkString("\nNhập Mã Lớp cần quản lý sinh viên: ");

        while (true) {
            System.out.println("\n--- QUẢN LÝ SINH VIÊN (LỚP: " + classId + ") ---");
            System.out.println("1. Hiển thị sinh viên trong lớp");
            System.out.println("2. Thêm sinh viên vào lớp");
            System.out.println("3. Xóa sinh viên");
            System.out.println("4. Sửa thông tin sinh viên");
            System.out.println("5. Tìm kiếm sinh viên theo MSSV (Toàn hệ thống)");
            System.out.println("6. Quay lại menu chính");
            int choice = CheckInput.checkInt("Nhập lựa chọn: ");

            switch (choice) {
                case 1:
                    List<Student> list = studentController.getStudentsByClass(classId);
                    for (Student s : list) System.out.println(s);
                    if (list.isEmpty()) System.out.println("Lớp này chưa có sinh viên.");
                    break;
                case 2:
                    String id = CheckInput.checkString("Nhập MSSV: ");
                    String name = CheckInput.checkString("Nhập tên: ");
                    String dob = CheckInput.checkString("Nhập ngày sinh: ");
                    int sex = CheckInput.checkInt("Giới tính (1:Nam, 0:Nữ): ");
                    studentController.add(new Student(id, name, dob, sex, classId));
                    System.out.println("Hệ thống: Thêm sinh viên thành công!");
                    break;
                case 3:
                    String deleteId = CheckInput.checkString("Nhập MSSV cần xóa: ");

                    if (CheckInput.checkYesNo("Bạn có chắc chắn muốn xóa sinh viên này khỏi hệ thống? (Y/N): ")) {
                        try {
                            studentController.deleteStudent(deleteId);
                            System.out.println("Hệ thống: Đã xóa sinh viên.");
                        } catch (NotFoundException e) {
                            System.out.println("Lỗi: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Hệ thống: Đã hủy thao tác xóa sinh viên.");
                    }
                    break;
                case 4:
                    String sId = CheckInput.checkString("Nhập MSSV cần sửa: ");
                    try {
                        Student old = studentController.getStudentById(sId);
                        System.out.println("Đang sửa: " + old.getName());

                        String newName = CheckInput.checkString("Tên mới (Enter để giữ nguyên): ");
                        if (newName.isEmpty()) newName = old.getName();

                        String newDob = CheckInput.checkString("Ngày sinh mới (Enter để giữ nguyên): ");
                        if (newDob.isEmpty()) newDob = old.getDob();

                        int newSex = CheckInput.checkInt("Giới tính mới (1:Nam, 0:Nữ, -1 giữ nguyên): ");
                        if (newSex == -1) newSex = old.getSex();

                        studentController.updateStudent(sId, new Student(sId, newName, newDob, newSex, classId));
                        System.out.println("Hệ thống: Cập nhật thành công!");
                    } catch (Exception e) {
                        System.out.println("Lỗi: " + e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("\n--- TÌM KIẾM TỔNG HỢP ---");
                    String searchId = CheckInput.checkString("Nhập MSSV cần tìm kiếm: ");
                    try {
                        Student foundStudent = studentController.getStudentById(searchId);
                        System.out.println("Hệ thống: Đã tìm thấy dữ liệu sinh viên!");
                        System.out.println("+------------+----------------------+--------------+------------+------------------+");
                        System.out.println(foundStudent);
                        System.out.println("+------------+----------------------+--------------+------------+------------------+");
                    } catch (NotFoundException e) {
                        System.out.println("Lỗi: Không tìm thấy sinh viên nào mang mã '" + searchId + "' trong hệ thống.");
                    }
                    break;
                case 6:
                    return;
            }
        }
    }
}