import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
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
            String fileName = statement.getName().substring(0, statement.getName().indexOf("."));
            System.out.println("Processing file " + fileName);
            String sentence;

            for (int j = 0; j < lines.size(); j++) {
                String word = lines.get(j).trim();
                if (word.equals("")) {
                    // process words
                    sentence = formSentence(words);
                    if (!sentence.isEmpty())
                        results.add(fileName + "\t" + sentence);
                    words.clear();
                } else {
                    words.add(word);
                }
            }

            // process the last sentence of words
            if (!words.isEmpty()) {
                sentence = formSentence(words);
                if (!sentence.isEmpty())
                    results.add(fileName + "\t" + sentence);
            }

            results.add("");
        }

        // print results to file
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter("federalFundsRate.txt", false));

        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        for (String result : results)
            sb.append(result + "\n");

        pw.append(sb.toString());
        pw.close();
    }

    private static String formSentence(List<String> words) {
        boolean isTargetSentence = false;
        boolean hasNumbers = false;
        StringBuffer result = new StringBuffer();
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
//                    values = words.get(i) + " " + words.get(i + 1) + " " + words.get(i + 2);
                    values = words.get(i) + "\t" + words.get(i) + "\t" + words.get(i+2) + "\t" + words.get(i+2);

                    hasNumbers = true;
                }
            }
        }

        Set<String> uniqueWords = new HashSet<>();
        uniqueWords.addAll(words);
        // ensure Committee perform this action
        if (isTargetSentence && hasNumbers && words.contains("Committee")) {
            result.append(values);
//            result.append(keyword + ": " + values);
//            result.append("\n" + sentence.toString());
        }

        return result.toString();
    }

    private static boolean isNumber(String value) {
        return value.matches("(\\d+[/]\\d+)|(\\d+)");
    }

}
