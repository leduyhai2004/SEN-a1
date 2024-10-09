package engine;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Engine {
    private List<Doc> docs = new ArrayList<>();

    public int loadDocs(String dirname) {
        File folder = new File(dirname);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles == null) return 0;
        for (File file : listOfFiles) {
            try {
                Scanner sc = new Scanner(file);
                StringBuilder content = new StringBuilder();
                while (sc.hasNextLine()) {
                    content.append(sc.nextLine()).append("\n");
                }
                docs.add(new Doc(content.toString().trim()));
                sc.close();
            } catch (FileNotFoundException e) {
            }
        }
        return docs.size();
    }

    public Doc[] getDocs() {
        return docs.toArray(new Doc[0]);
    }

    public List<Result> search(Query q) {
        List<Result> results = new ArrayList<>();

        for (Doc doc : docs) {
            List<Match> matches = q.matchAgainst(doc);

            if (!matches.isEmpty()) {
                results.add(new Result(doc, matches));
            }
        }

        results.sort(Result::compareTo);

        return results;
    }


    public String htmlResult(List<Result> results) {
        StringBuilder html = new StringBuilder();

        for (Result result : results) {
            html.append(result.htmlHighlight());
        }

        return html.toString();
    }
}