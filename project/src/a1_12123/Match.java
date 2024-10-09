package engine;

public class Match implements Comparable<Match> {
    private Doc doc;
    private Word word;
    private int freq;
    private int firstIndex;

    public Match(Doc d, Word w, int freq, int firstIndex) {
        this.doc = d;
        this.word = w;
        this.freq = freq;
        this.firstIndex = firstIndex;
    }

    public Word getWord() {
        return word;
    }

    public int getFreq() {
        return freq;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    @Override
    public int compareTo(Match o) {
        return Integer.compare(this.firstIndex, o.firstIndex);
    }
}