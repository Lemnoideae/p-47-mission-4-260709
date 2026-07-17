import com.ll.wiseSaying.App;

import java.io.ByteArrayOutputStream;
import java.util.Scanner;

public class AppTestRunner {
    public static String run(String input) {
        String finalInput;
        if (input == null || input.isEmpty() || input.equals("종료"))
            finalInput = "종료\n";
        else
            finalInput = input.stripIndent().trim() + "\n종료\n";

        Scanner scanner = TestUtil.genScanner(finalInput);
        ByteArrayOutputStream output = TestUtil.setOutToByteArray();

        App app = new App(scanner);
        app.run();

        String result = output.toString();
        TestUtil.clearSetOutToByteArray(output);

        return result;
    }
}