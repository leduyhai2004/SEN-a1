package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Word {
    public static Set<String> stopWords = new HashSet<>();
    private String prefix;
    private String text;
    private String suffix;

    public Word(String prefix, String text, String suffix) {
        this.prefix = prefix;
        this.text = text;
        this.suffix = suffix;
    }

    public static boolean loadStopWords(String fileName) {
        try {
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNext()) {
                stopWords.add(sc.next().trim().toLowerCase());
            }
            sc.close();
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }



    public String getPrefix() {
        return prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Same object
        if (o == null || getClass() != o.getClass()) return false;

        Word other = (Word) o;
        return this.text.equalsIgnoreCase(other.getText());
    }

    @Override
    public String toString() {
        return prefix + text + suffix;
    }

    public static Word createWord(String rawText) {
        if (rawText.isEmpty()) {
            return new Word("", "", "");
        }

        String prefix = "", text = "", suffix = "";
        int firstAlpha = -1, lastAlpha = -1;

        // Kiểm tra xem chuỗi có chứa ký tự số hay không
        if (rawText.matches(".*\\d.*")) {
            return new Word("", rawText, "");
        }

        for (int i = 0; i < rawText.length(); i++) {
            char c = rawText.charAt(i);
            if (Character.isLetter(c) || c == '-' || c == '\'') {
                if (firstAlpha == -1) {
                    firstAlpha = i;
                }
                lastAlpha = i;
            }
        }

        // Nếu không tìm thấy ký tự chữ cái nào, trả về Word với text là rawText
        if (firstAlpha == -1) {
            return new Word("", rawText, "");
        }

        prefix = rawText.substring(0, firstAlpha);
        suffix = rawText.substring(lastAlpha + 1);

        // Xử lý trường hợp đặc biệt 's và 'd ở cuối text
        if (rawText.endsWith("'s")|| rawText.endsWith("'d")) {
            text = rawText.substring(firstAlpha, lastAlpha - 1);
            suffix = rawText.substring(lastAlpha - 1) + suffix;
        } else {
            text = rawText.substring(firstAlpha, lastAlpha + 1);
        }

        return new Word(prefix, text, suffix);
    }

    public boolean isKeyword() {
        if (text.isEmpty() || stopWords.contains(text.toLowerCase()) || !text.matches(".*[a-zA-Z].*")) {
            return false;
        }
        return text.matches("[a-zA-Z'-]+");
    }
}