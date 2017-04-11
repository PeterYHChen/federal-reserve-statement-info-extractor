import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by yonghong on 4/11/17.
 */
public class NaiveExtractor {

    public static void main (String[] args) throws IOException {
        if (args.length != 1) {
            System.err.println("PosTagger takes 1 arguments:  java FeatureBuilder training.directory");
            System.exit(1);
        }

        extract(args[0]);
    }

    private static void extract(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        List<String> results = new ArrayList<>();

        File[] files = directory.listFiles();
        Arrays.sort(files);
        for (int i = 0; i < files.length; i++) {
            File statement = files[i];
            List<String> lines = Files.readAllLines(statement.toPath(), StandardCharsets.UTF_8);
            List<String> words = new ArrayList<>();

            System.out.println("Processing file " + statement);
            results.add(statement.getName());
            for (int j = 0; j < lines.size(); j++) {
    //            System.out.println("Processing line " + i);
                String word = lines.get(j).trim();
                if (word.equals("")) {
                    // process words and get features
                    results.addAll(formSentence(words));
                    words.clear();
                } else {
                    words.add(word);
                }
            }

            // process the last sentence of words
            if (!words.isEmpty()) {
                results.addAll(formSentence(words));
            }

            results.add("");
        }

        // print results to file
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter("extracted-sentences", false));

        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (String result : results)
            sb.append(result + "\n");

        pw.append(sb.toString());
        pw.close();
    }

    private static List<String> formSentence(List<String> words) {
        List<String> results = new ArrayList<>();
        boolean isTargetSentence = false;
        boolean hasNumbers = false;

        StringBuffer sentence = new StringBuffer();
        String keyword = "";
        String values = "";

        for (int i = 0; i < words.size(); i++) {
            sentence.append(" " + words.get(i));

            if (!isTargetSentence && i + 2 < words.size()) {
                if (words.get(i).equals("federal") && words.get(i+1).equals("funds") && words.get(i+2).equals("rate")) {
                    keyword = words.get(i) + " " + words.get(i + 1) + " " + words.get(i + 2);
                    isTargetSentence = true;
                }
            }

            if (!hasNumbers && i + 2 < words.size()) {
                if (isNumber(words.get(i)) && words.get(i+1).equals("to") && isNumber(words.get(i+2))) {
                    values = words.get(i) + " " + words.get(i + 1) + " " + words.get(i + 2);
                    hasNumbers = true;
                }
            }
        }

        if (isTargetSentence && hasNumbers) {
            results.add(keyword + ": " + values);
            results.add(sentence.toString());
        }

        return results;
    }

    private static boolean isNumber(String value) {
        return value.matches("(\\d+[/]\\d+)|(\\d+)");
    }

}
