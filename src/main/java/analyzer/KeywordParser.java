package analyzer;

import analyzer.treeUtil.WordTree;
import org.jsoup.nodes.Document;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is responsible for parsing the HTML file
 */
public class KeywordParser {

    private final Document document;
    private Logger logger = Logger.getLogger(DocumentReader.class.getName());
    private Set<String> stopWords;

    private WordTree wordTree;

    public KeywordParser(Document document) {
        stopWords = new HashSet<>();
        wordTree = new WordTree();
        this.document = document;
        init();
    }

    /**
     * This method will initialize meta data required for parsing
     */
    private void init() {
        loadStopWords();
    }

    /**
     * This method will load all the stop words from a file
     */
    private void loadStopWords() {
        try {
            File file = new File(getClass().getClassLoader().getResource("stopWords.txt").getFile());
            Scanner sc = new Scanner(file);
            while (sc.hasNext()) {
                stopWords.add(sc.next());
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unable to read stop words from stopWords file");
            System.exit(1);
        }
    }

    /**
     * This is the main method that will parse the whole document for keywords
     * @return set of keywords
     */
    public Set<String> parseKeyWords() {
        Scanner scanner = new Scanner(document.text());

        StringBuilder topicStringBuilder = new StringBuilder();

        while (scanner.hasNext()) {
            String word = scanner.next();
            boolean endPunc = (word.endsWith(".") || word.endsWith(",") || word.endsWith("!") || word.endsWith("?"));


            boolean stopWord = isStopWord(trimSymbols(word.toLowerCase()));
            if(endPunc || stopWord) {
                if(endPunc) {
                    topicStringBuilder.append(word);
                    topicStringBuilder.append(" ");
                }
                String topicString = topicStringBuilder.toString();
                if(!topicString.equals(" ") && !topicString.equals("")) {
                    String[] keyWords = topicString.trim().split(" ");
                    wordTree.add(keyWords);
                    topicStringBuilder.setLength(0);
                    topicStringBuilder.trimToSize();
                }

            } else {
                word = trimSymbols(word);
                if(!word.equals("")) {
                    topicStringBuilder.append(trimSymbols(word));
                    topicStringBuilder.append(" ");
                }
            }
        }
        return wordTree.getAllKeys();
    }

    /**
     * method to determine if word is stopWord
     * @param word input word
     * @return true if stop word else false
     */
    private boolean isStopWord(String word) {
        return word.endsWith(",") || stopWords.contains(word);
    }

    /**
     * trimmer function to trim whiteSpace, special symbols
     * @param inputText text to trim
     * @return trimmed text
     */
    private String trimSymbols(String inputText) {
        // Trim extra whitespace
        String text = inputText.trim();

        // Remove plurals
        text = text.replace("'s", "");

        // Remove other symbols
        text = text.replace("!", "");
        text = text.replace("?", "");
        text = text.replace("\"", "");
        text = text.replace(",", "");
        text = text.replace(":", "");
        text = text.replace("", "");
        text = text.replace("✓", "");
        text = text.replace("›", "");

        // Remove apostrophes
        if (text.startsWith("'") || text.endsWith("'"))
            text = text.replace("'", "");

        // Remove periods
        if (text.endsWith(".") && !isUpper(text.substring(text.length()-2, text.length()-1)))
            text = text.replace(".", "");

        return text;
    }

    /**
     * check is string beginning with upper case letter
     * @param stringToCheck input string to check
     * @return true if string begins with uppercase letter else false
     */
    private boolean isUpper(String stringToCheck) {
        return stringToCheck != null && !stringToCheck.isEmpty() && Character.isUpperCase(stringToCheck.charAt(0));
    }

    /**
     * method to get all keyword phrases starting with string 'str' and their respective count
     * @param str starting string
     * @return map of keywords and their count
     */
    public Map<String, Integer> get(String str) {
        return wordTree.getKeywords(str);
    }
}
