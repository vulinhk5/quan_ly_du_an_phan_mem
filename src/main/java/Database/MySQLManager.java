package Database;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MySQLManager {
    public static void startMySQL() {
        String mysqlPath = "C:/xampp/mysql/bin/mysqld.exe";

        try {
            System.out.println("MySQL server is starting...");

            ProcessBuilder processBuilder = new ProcessBuilder(mysqlPath);
            Process process = processBuilder.start();
            Thread.sleep(500);

            System.out.println("MySQL server started successfully.");
        } catch (Exception e) {
            System.err.println("Error starting MySQL: " + e.getMessage());
        }
    }

    public static void stopMySQL() {
        String mysqlStopPath = "C:/xampp/mysql/bin/mysqladmin.exe";
        String[] shutdownCommand = {mysqlStopPath, "-u", "root", "-p", "shutdown"};

        try {
            // Khởi tạo ProcessBuilder để chạy lệnh mysqladmin
            ProcessBuilder processBuilder = new ProcessBuilder(shutdownCommand);
            Process process = processBuilder.start();

            // Đợi một chút để MySQL yêu cầu mật khẩu
            Thread.sleep(300); // Đợi một giây trước khi gửi mật khẩu

            // Gửi phím Enter (mật khẩu rỗng)
            OutputStream outputStream = process.getOutputStream();
            outputStream.write("\n".getBytes()); // Gửi phím Enter
            outputStream.flush(); // Đảm bảo rằng phím Enter được gửi

            // Đợi thêm một chút để đảm bảo tiến trình dừng lại
            Thread.sleep(300); // Đợi một giây trước khi tiến hành xong

            // Đợi cho tiến trình kết thúc;

            System.out.println("MySQL server stopped successfully.");
        } catch (Exception e) {
            System.err.println("Error stopping MySQL: " + e.getMessage());
        }
    }
}
