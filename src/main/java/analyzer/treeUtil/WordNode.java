package analyzer.treeUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Tree node will hold keyword and it's count of keyword and children of word
 */
public class WordNode implements Comparable {
    private String value;
    private int size;
    private Map<String, WordNode> children;

    public WordNode(String value, int size) {
        this.value = value;
        this.size = size;
        children = new HashMap<>();
    }

    public WordNode(String value) {
        this.value = value;
        size = 1;
        children = new HashMap<>();
    }

    /**
     * Adds a new child WordNode. If child already exists then increments the child's size.
     * @param child keyword to add
     */
    public void add(String child) {
        if (!children.containsKey(child))
            children.put(child, new WordNode(child));
        else
            children.get(child).increment();
    }

    public void increment() {
        size++;
    }

    /**
     * wordNode getter for given keyword
     * @param child child keyword
     * @return wordNode of child keyword
     */
    public WordNode get(String child) {
        return children.get(child);
    }

    public String getValue() {
        return value;
    }

    public int getSize() {
        return size;
    }

    /**
     * method to get all children
     * @return a HashMap of containing all children strings and their respective WordNodes
     */
    public Map<String, WordNode> getChildren() {
        return children;
    }

    /**
     * method to check if node has children
     * @return true if the WordNode has children else false
     */
    public boolean hasChildren() {
        return children.size() != 0;
    }

    @Override
    public int compareTo(Object o) {
        WordNode a = this;
        WordNode b = (WordNode) o;

        if (a.getSize() > b.getSize())
            return -1;
        else if (a.getSize() < b.getSize())
            return 1;
        else
            return 0;
    }
}
