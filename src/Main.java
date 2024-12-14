import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Main {
    public static void generateRandomTextFile(int length, String fileName) {
        Random random = new Random();
        String characters = "#_!@아이우에오,./|\\PQRSTUVWXYZ1234567890안녕하세요구르트 마시멜로우쿠수츄하\n"; // 사용할 문자들을 지정합니다.

        StringBuilder text = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            text.append(characters.charAt(random.nextInt(characters.length())));
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(text.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        int length = 5000; // 원하는 텍스트의 길이를 지정합니다.
        String fileName = "randomText.txt"; // 파일 이름을 지정합니다.
        generateRandomTextFile(length, fileName);
        new MenuGUI();
    }
}

