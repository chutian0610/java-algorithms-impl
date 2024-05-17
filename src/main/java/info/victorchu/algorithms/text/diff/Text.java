package info.victorchu.algorithms.text.diff;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Text {
    private List<String> lines;
    public Text(File file) throws IOException {
        this(readFileToLines(file));
    }

    public Text(String text) throws IOException {
        this(readTextToLines(text));
    }
    public Text(List<String> stringList) {
        this.lines = stringList;
    }
    public int size() {
        return lines.size();
    }
    public String getLine(int index) {
        return lines.get(index);
    }


    public static List<String> readFileToLines(File file) throws IOException {
        if (!file.exists() || !file.isFile() || !file.canRead()) {
            throw new FileNotFoundException("File not found or not readable: " + file.getAbsolutePath());
        }

        FileReader fr = new FileReader(file);
        try (BufferedReader br = new BufferedReader(fr)) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
    }


    public static List<String> readTextToLines(String text) throws IOException {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("text is Empty");
        }

        StringReader fr = new StringReader(text);
        try (BufferedReader br = new BufferedReader(fr)) {
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        }
    }
}
