package info.victorchu.algorithms.search.trietree;

import java.util.HashMap;
import java.util.Map;

/**
 * @author victor
 * @mail victorchu0610@outlook.com
 * @date 2019/7/30
 * @version 1.0
 * @description TODO
 */
public class TrieNode{
    private Map<Character,TrieNode> children = new HashMap<>();
    private Character data;
    private boolean endOfData = false;

    public TrieNode(Character data) {
        this.data = data;
    }

    public TrieNode(Character data, boolean endOfData) {
        this.data = data;
        this.endOfData = endOfData;
    }

    public Character getData() {
        return data;
    }

    public void setData(Character data) {
        this.data = data;
    }

    public boolean isEndOfData() {
        return endOfData;
    }

    public void setEndOfData(boolean endOfData) {
        this.endOfData = endOfData;
    }

    public Map<Character, TrieNode> getChildren() {
        return children;
    }

    public TrieNode getChild(Character name) {
        return children.get(name);
    }

    public void addChild(Character name, TrieNode child) {
        children.put(name,child);
    }
    public boolean deleteChild(Character name){
        if(children.containsKey(name)){
            children.remove(name);
            return true;
        }
        return false;
    }
}