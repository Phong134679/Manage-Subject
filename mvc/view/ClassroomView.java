package mvc.view;

import mvc.controller.ClassroomController;
import mvc.entity.Classroom;
import mvc.utils.exception.NotFoundException;
import mvc.utils.validate.CheckInput;
import java.util.List;

public class ClassroomView {
    private static ClassroomController classController = new ClassroomController();

    public static void mainMenu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ LỚP HỌC ---");
            System.out.println("1. Hiển thị danh sách lớp");
            System.out.println("2. Thêm lớp mới");
            System.out.println("3. Xóa lớp");
            System.out.println("4. Sửa tên lớp");
            System.out.println("5. Quay lại menu chính");
            int choice = CheckInput.checkInt("Nhập lựa chọn: ");

            switch (choice) {
                case 1:
                    List<Classroom> list = classController.getAll();
                    System.out.println("+------------+----------------------+");
                    System.out.printf("| %-10s | %-20s |\n", "Mã Lớp", "Tên Lớp");
                    System.out.println("+------------+----------------------+");
                    for (Classroom c : list) System.out.println(c);
                    System.out.println("+------------+----------------------+");
                    break;
                case 2:
                    String id = CheckInput.checkString("Nhập mã lớp: ");
                    String name = CheckInput.checkString("Nhập tên lớp: ");
                    classController.add(new Classroom(id, name));
                    System.out.println("Hệ thống: Thêm lớp thành công!");
                    break;
                case 3:
                    String deleteId = CheckInput.checkString("Nhập mã lớp cần xóa: ");
                    boolean confirm = CheckInput.checkYesNo("CẢNH BÁO: Bạn có chắc chắn muốn xóa lớp này và TOÀN BỘ sinh viên bên trong? (Y/N): ");

                    if (confirm) {
                        try {
                            classController.deleteClass(deleteId);
                            System.out.println("Hệ thống: Xóa lớp và sinh viên liên quan thành công!");
                        } catch (NotFoundException e) {
                            System.out.println("Lỗi: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Hệ thống: Đã hủy thao tác xóa lớp.");
                    }
                    break;
                case 4:
                    String updateId = CheckInput.checkString("Nhập mã lớp cần sửa: ");
                    try {
                        String currentName = classController.getAll().stream()
                                .filter(c -> c.getId().equalsIgnoreCase(updateId))
                                .findFirst().orElseThrow(() -> new NotFoundException("")).getName();

                        System.out.println("Tên hiện tại: " + currentName);
                        String newName = CheckInput.checkString("Nhập tên mới (Enter để giữ nguyên): ");
                        if (newName.isEmpty()) newName = currentName;

                        classController.updateClass(updateId, newName);
                        System.out.println("Hệ thống: Cập nhật thành công!");
                    } catch (NotFoundException e) {
                        System.out.println("Lỗi: Không tìm thấy mã lớp.");
                    }
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }
}