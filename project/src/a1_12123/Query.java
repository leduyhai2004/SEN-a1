package engine;

import java.util.List;
import java.util.ArrayList;

public class Query {
    private List<Word> keywords = new ArrayList<>();

    public Query(String search) {
        String[] raws = search.split(" ");
        for (String raw : raws) {
            Word word = Word.createWord(raw);
            // Kiểm tra word có phải keyword và không phải stop word
            if (word.isKeyword() && !Word.stopWords.contains(word.getText().toLowerCase())) {
                keywords.add(word);
            }
        }
    }

    public List<Word> getKeywords() {
        return keywords;
    }

    public List<Match> matchAgainst(Doc d) {
        List<Match> matches = new ArrayList<>();
        List<Word> all = new ArrayList<>(d.getTitle());
        all.addAll(d.getBody());

        for (Word keyword : keywords) {
            int freq = 0;
            int firstIndex = -1;

            for (int i = 0; i < all.size(); i++) {
                if (all.get(i).equals(keyword)) {
                    if (firstIndex == -1) {
                        firstIndex = i;
                    }
                    freq++;
                }
            }

            if (freq > 0) {
                matches.add(new Match(d, keyword, freq, firstIndex));
            }
        }

        matches.sort((m1, m2) -> {
            return Integer.compare(m1.getFirstIndex(), m2.getFirstIndex());
        });

        return matches;
    }





}