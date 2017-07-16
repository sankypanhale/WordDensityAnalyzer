package analyzer.treeUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class will hold the whole tree of keywords along with their frequencies
 */
public class WordTree {

    private Map<String, WordNode> tree;

    public WordTree() {
        tree = new HashMap<>();
    }

    /**
     * method to add keyword to tree
     * if keyword exist then increment the count, else make a new insert
     * @param keyword keyword to add in tree
     */
    private void add(String keyword) {
        if (!tree.containsKey(keyword))
            tree.put(keyword, new WordNode(keyword));
        else {
            tree.get(keyword).increment();
        }
    }

    /**
     * method to add collections of keywords to tree
     * @param keyWords all keywords to tree
     */
    public void add(String[] keyWords) {
        int index = 1;
        for (String keyword : keyWords) {
            add(keyword);
            addHelper(keyWords, index, tree.get(keyword));
            index++;
        }
    }

    /**
     * helper method to add all keywords to tree
     * @param keyWords all keywords to add
     * @param index level in the tree to insert
     * @param parent parent of current node
     */
    private void addHelper(String[] keyWords, int index, WordNode parent) {
        if (index != keyWords.length) {
            parent.add(keyWords[index]);
            addHelper(keyWords, index + 1, parent.get(keyWords[index]));
        }
    }

    /**
     * @return all the keywords in tree
     */
    public Set<String> getAllKeys() {
        return tree.keySet();
    }

    /**
     * @param str keyword starting phrase
     * @return Returns a HashMap containing each keyword phrase starting with string 'str' and their respective size
     */
    public Map<String, Integer> getKeywords(String str) {
        return getKeywords(tree.get(str));
    }

    /**
     * Helper function for getKeywords(String str)*
     * @param startNode starting node of keyowrd in tree
     * @return map of keywords and their counts
     */
    private Map<String, Integer> getKeywords(WordNode startNode) {
        Map<String, Integer> map = new HashMap<>();
        getKeywordsHelper(startNode, startNode.getValue(), map);
        return map;
    }

    /**
     * Recursive helper function for getKeywords(WordNde startNode)*
     * @param node current node
     * @param keywords keyword
     * @param map map to populate values
     */
    private void getKeywordsHelper(WordNode node, String keywords, Map<String, Integer> map) {

        if (node.hasChildren()) {
            Map<String, WordNode> ch = node.getChildren();
            for (String child : ch.keySet()) {
                getKeywordsHelper(ch.get(child), keywords + " " + child, map);
            }
        }
        map.put(keywords, node.getSize());
    }
}
