package mvc.utils.FileHelper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {
    public static void writeToFile(String path, List<String> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path))) {
            for (String line : data) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file: " + e.getMessage());
        }
    }

    public static List<String> readFromFile(String path) {
        List<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    data.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Lỗi: Không tìm thấy file tại đường dẫn: " + path);
        } catch (IOException e) {
            System.out.println("Lỗi đọc file: " + e.getMessage());
        }
        return data;
    }
}