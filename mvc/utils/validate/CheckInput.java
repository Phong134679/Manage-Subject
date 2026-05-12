package mvc.utils.validate;

import java.util.Scanner;

public class CheckInput {
    private static Scanner sc = new Scanner(System.in);

    public static int checkInt(String mess) {
        while (true) {
            try {
                System.out.print(mess);
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên hợp lệ.");
            }
        }
    }

    public static String checkString(String mess) {
        System.out.print(mess);
        return sc.nextLine();
    }

    public static boolean checkYesNo(String mess) {
        while (true) {
            System.out.print(mess);
            String input = sc.nextLine().trim().toLowerCase();
            if (input.equals("y") || input.equals("yes")) {
                return true;
            }
            if (input.equals("n") || input.equals("no")) {
                return false;
            }
            System.out.println("Lỗi: Vui lòng chỉ nhập Y (Đồng ý) hoặc N (Hủy bỏ).");
        }
    }
}