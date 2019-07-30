package info.victorchu.algorithms.search.trietree;

/**
 * @Author victor
 * @Email victorchu0610@outlook.com
 * @Data 2019/7/30
 * @Version 1.0
 * @Description 字典树
 */
import java.util.Map;

public class TrieTree{
    private static final String point1 = "└── ";
    private static final String point2 = "|—— ";
    private static final String pre1= "    ";
    private static final String pre2= "|   ";

    private TrieNode root = new TrieNode('.');

    public void printTree(){
        printNode(root);
    }
    private void printNode(TrieNode node){
        printNode(root,0,false,"");
    }

    private void printNode(TrieNode node,int level,boolean isLastChild,String pre){
        String isWord = level == 0?"":node.isEndOfData()?"(+)":"(-)";
        System.out.println(getPreString(pre,level,isLastChild)+node.getData()+isWord);
        String currentPre = getLevelPre(pre,level,isLastChild);
        if(!node.getChildren().isEmpty()){
            level = level+1;
            int size = node.getChildren().size();
            int count = 0;
            for (Character key: node.getChildren().keySet()) {
                TrieNode subNode = node.getChild(key);
                count++;
                if(count == size){
                    printNode(subNode,level,true,currentPre);
                }else {
                    printNode(subNode,level,false,currentPre);
                }
            }
        }
    }

    private String getPreString(String pre,int level,boolean isLastChild){
        if(level == 0){
            return pre;
        }
        if(isLastChild){
            return pre+point1;
        }else {
            return pre+point2;
        }
    }

    private String getLevelPre(String pre,int level,boolean isLastChild){
        if(level ==0){
            return pre;
        }else {
            StringBuilder sb = new StringBuilder();
            sb.append(pre);
            if(isLastChild) {
                sb.append(pre1);
            }else {
                sb.append(pre2);
            }
            return sb.toString();
        }
    }
    // 插入元素
    public void insert(String word) {
        TrieNode current = root;

        for (int i = 0; i < word.length(); i++) {
            Map<Character,TrieNode> children = current.getChildren();
            if(children.containsKey(word.charAt(i))){
                // pass
                current = children.get(word.charAt(i));
            }else {
                current = new TrieNode(word.charAt(i),false);
                children.put(word.charAt(i),current);
            }
        }
        current.setEndOfData(true);
    }
    // 寻找元素
    public boolean find(String word) {
        TrieNode current = root;
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            TrieNode node = current.getChild(ch);
            if (node == null) {
                return false;
            }
            current = node;
        }
        return current.isEndOfData();
    }
    // 删除元素
    public boolean delete(String word) {
        return delete(root, word, 0);
    }

    private boolean delete(TrieNode current, String word, int index) {
        if (index == word.length()) {
            if (!current.isEndOfData()) {
                return false;
            }
            current.setEndOfData(false);
            // 如果没有其他公共前缀单词，则将当前前缀从trie中删除·
            return current.getChildren().isEmpty();
        }
        char ch = word.charAt(index);
        TrieNode node = current.getChildren().get(ch);
        if (node == null) {
            return false;
        }
        boolean shouldDeleteCurrentNode = delete(node, word, index + 1) && !node.isEndOfData();

        if (shouldDeleteCurrentNode) {
            current.deleteChild(ch);
            return current.getChildren().isEmpty();
        }
        return false;
    }

    public static void main(String[] args) {
        TrieTree trie = new TrieTree();

        trie.insert("Programming");
        trie.insert("Progming");
        trie.insert("Progm");
        trie.insert("Problem");
        trie.printTree();
        // + 表示是单词的结尾
        System.out.println(trie.find("Progm"));
    }

}