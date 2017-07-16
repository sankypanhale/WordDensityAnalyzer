package analyzer;

import analyzer.treeUtil.WordNode;
import org.jsoup.nodes.Document;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * This class will accept input URL
 * This is main class of keyword analyzer
 */
public class AnalyzerMain {

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Usage java -jar AnalyzerMain.jar <URL>");
            System.exit(0);
        }
        String url = args[0];
        System.out.println("URL: " + url);

        DocumentReader documentReader = new DocumentReader(url);
        Document document = documentReader.readDocument();

        KeywordParser keywordParser = new KeywordParser(document);
        Set<String> keyWords = keywordParser.parseKeyWords();

        PriorityQueue<WordNode> priorityQueue = new PriorityQueue<>();

        //threshold for frequencies of words
        int threshold = 2;
        for(String str : keyWords) {
            Map<String, Integer> allKeywords = keywordParser.get(str);
            for (String keyword : allKeywords.keySet()) {
                if (allKeywords.get(keyword) >= threshold) {
                    priorityQueue.add(new WordNode(keyword, allKeywords.get(keyword)));
                }
            }
        }
        while (!priorityQueue.isEmpty()) {
            WordNode node = priorityQueue.remove();
            System.out.println(node.getSize() + " " + node.getValue());
        }
    }

}
