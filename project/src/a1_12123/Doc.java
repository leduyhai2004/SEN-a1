package engine;

import java.util.List;
import java.util.ArrayList;

public class Doc {
    private List<Word> title;
    private List<Word> body;

    public Doc(String content) {
        String[] lines = content.split("\n");
        title = processLine(lines[0]);
        body = lines.length > 1 ? processLine(lines[1]) : new ArrayList<>();
    }

    private List<Word> processLine(String line) {
        List<Word> words = new ArrayList<>();
        if (line.isEmpty()) {  // Kiểm tra xem line có rỗng không
            return words;  // Trả về danh sách rỗng nếu line rỗng
        }
        String[] rawWords = line.split("\\s+"); // Sửa lỗi tại đây
        for (String rawWord : rawWords) {
            words.add(Word.createWord(rawWord));
        }
        return words;
    }

    public List<Word> getTitle() {
        return title;
    }

    public List<Word> getBody() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doc)) return false;
        Doc other = (Doc) o;
        return this.title.equals(other.title) && this.body.equals(other.body);
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + body.hashCode();
        return result;
    }
}