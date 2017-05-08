import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yonghong on 5/7/17.
 */
public class Formatter {

    public static void main (String[] args) throws IOException {
        File file = new File("fundsRateCompare.txt");
        List<String> lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        List<String> results = new ArrayList<>();

        for (String line : lines) {
            StringBuffer result = new StringBuffer();
            String[] values = line.split(" ");

            result.append("['").append(values[0])
                    .append("', ").append(values[1])
                    .append(", ").append(values[2])
                    .append(", ").append(values[3])
                    .append("],");

            System.out.println(result);
        }
    }
}
