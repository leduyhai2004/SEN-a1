package engine;

import java.util.List;

public class Result implements Comparable<Result> {
    private Doc document;
    private List<Match> matches;
    private int matchCount;
    private int totalFrequency;
    private double averageFirstIndex;

    public Result(Doc d, List<Match> matches) {
        this.document = d;
        this.matches = matches;
        this.matchCount = matches.size();
        this.totalFrequency = calculateTotalFrequency();
        this.averageFirstIndex = calculateAverageFirstIndex();
    }

    private int calculateTotalFrequency() {
        int total = 0;
        for (Match match : matches) {
            total += match.getFreq();
        }
        return total;
    }

    private double calculateAverageFirstIndex() {
        if (matches.isEmpty()) return 0;
        int totalFirstIndex = 0;
        for (Match match : matches) {
            totalFirstIndex += match.getFirstIndex();
        }
        return (double) totalFirstIndex / matches.size();
    }

    public List<Match> getMatches() {
        return matches;
    }

    public int getTotalFrequency() {
        return totalFrequency;
    }

    public double getAverageFirstIndex() {
        return averageFirstIndex;
    }

    public Doc getDoc() {
        return document;
    }

    public String htmlHighlight() {
        StringBuilder highlightedText = new StringBuilder();

        List<Word> titleWords = document.getTitle();
        highlightedText.append("<h3>");
        for (int i = 0; i < titleWords.size(); i++) {
            Word word = titleWords.get(i);
            if (matchesWord(word)) {
                highlightedText.append(word.getPrefix())
                        .append("<u>").append(word.getText()).append("</u>")
                        .append(word.getSuffix());
            } else {
                highlightedText.append(word.toString());
            }
            if (i < titleWords.size() - 1) {
                highlightedText.append(" ");
            }
        }
        highlightedText.append("</h3>");

        List<Word> bodyWords = document.getBody();
        highlightedText.append("<p>");
        for (int i = 0; i < bodyWords.size(); i++) {
            Word word = bodyWords.get(i);
            if (matchesWord(word)) {
                highlightedText.append(word.getPrefix())
                        .append("<b>").append(word.getText()).append("</b>")
                        .append(word.getSuffix());
            } else {
                highlightedText.append(word.toString());
            }
            if (i < bodyWords.size() - 1) {
                highlightedText.append(" ");
            }
        }
        highlightedText.append("</p>");

        return highlightedText.toString().trim();
    }




    private boolean matchesWord(Word word) {
        for (Match match : matches) {
            if (match.getWord().equals(word)) {
                return true;
            }
        }
        return false;
    }


    @Override
    public int compareTo(Result o) {
        if (this.matchCount != o.matchCount) {
            return o.matchCount - this.matchCount;
        }

        if (this.totalFrequency != o.totalFrequency) {
            return o.totalFrequency - this.totalFrequency;
        }

        return Double.compare(this.averageFirstIndex, o.averageFirstIndex);
    }



}