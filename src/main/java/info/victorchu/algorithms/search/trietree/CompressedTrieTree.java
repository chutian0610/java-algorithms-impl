package info.victorchu.algorithms.search.trietree;

/**
 * @Author victor
 * @Email victorchu0610@outlook.com
 * @Data 2019/7/30
 * @Version 1.0
 * @Description 压缩字典树
 */
import java.util.Map;
import java.util.Set;

public class CompressedTrieTree{

    private static final String point1 = "└── ";
    private static final String point2 = "|—— ";
    private static final String pre1= "    ";
    private static final String pre2= "|   ";

    private CompressedTrieNode root = new CompressedTrieNode(".");

    public void printTree(){
        printNode(root);
    }
    private void printNode(CompressedTrieNode node){
        printNode(root,0,false,"");
    }

    private void printNode(CompressedTrieNode node,int level,boolean isLastChild,String pre){
        String isWord = level == 0?"":node.isWord()?"(+)":"(-)";
        System.out.println(getPreString(pre,level,isLastChild)+node.getData()+isWord);
        String currentPre = getLevelPre(pre,level,isLastChild);
        if(!node.getChildren().isEmpty()){
            level = level+1;
            int size = node.getChildren().size();
            int count = 0;
            for (String key: node.getChildren().keySet()) {
                CompressedTrieNode subNode = node.getChild(key);
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

    public void insert(String word) {
        insertToNode(root,word);
    }
    private void insertToNode(CompressedTrieNode current,String word){
        Map<String,CompressedTrieNode> children = current.getChildren();
        if(children.isEmpty()){
            children.put(word,new CompressedTrieNode(word,true));
        }else {
            String likeWord = null;
            CompressedTrieNode likeNode = null;
            String pre = null;
            for (CompressedTrieNode child :children.values()) {
                pre = child.getSamePrefix(word);
                if ( pre!= null) {
                    likeWord = child.getData();
                    likeNode = child;
                    break;
                }
            }
            if(likeWord == null){
                // 不存在共同前缀的单词
                children.put(word,new CompressedTrieNode(word,true));
            }else {
                // 存在共同前缀的单词
                if(word.equals(likeWord)){
                    likeNode.setWord(true);
                    return;
                }
                if(pre.length() == likeWord.length()){
                    String leftWord = word.substring(pre.length());
                    insertToNode(likeNode,leftWord);
                    return;
                }
                if(pre.length()< likeWord.length()){
                    CompressedTrieNode parent = new CompressedTrieNode(pre,false);
                    likeNode.setData(likeWord.substring(pre.length()));
                    current.getChildren().remove(likeWord);
                    parent.getChildren().put(likeNode.getData(),likeNode);
                    if(word.length()>pre.length()) {
                        CompressedTrieNode child = new CompressedTrieNode(word.substring(pre.length()), true);
                        parent.getChildren().put(child.getData(), child);
                    }else {
                        parent.setWord(true);
                    }

                    current.getChildren().put(parent.getData(),parent);
                }
            }
        }
    }

    public boolean find(String word) {
        CompressedTrieNode current = root;
        return findInNode(current,word);
    }

    private boolean findInNode(CompressedTrieNode current,String word){
        if(current.getChildren().isEmpty()){
            return false;
        }
        CompressedTrieNode node = null;
        String preWord = null;
        for (CompressedTrieNode child: current.getChildren().values()) {
            String pre = child.getSamePrefix(word);
            if(pre != null){
                node = child;
                preWord = pre;
                break;
            }
        }
        if(node == null){
            return false;
        }
        if(node.getData().equals(word)){
            return node.isWord();
        }

        if(preWord.length() < node.getData().length()){
            return false;
        }

        if(preWord.length() == node.getData().length()){
            return findInNode(node,word.substring(preWord.length()));
        }
        return false;
    }

    public boolean delete(String word) {
        return deleteInNode(root,word);
    }

    private boolean deleteInNode(CompressedTrieNode current,String word){
        if(current.getChildren().isEmpty()){
            return false;
        }
        CompressedTrieNode node = null;
        String preWord = null;
        for (CompressedTrieNode child: current.getChildren().values()) {
            String pre = child.getSamePrefix(word);
            if(pre != null){
                node = child;
                preWord = pre;
                break;
            }
        }
        if(node == null){
            return false;
        }
        if(preWord.length() < node.getData().length()){
            return false;
        }
        if(node.getData().equals(word)){
            if(!node.isWord()){
                return false;
            }
            if(node.getChildren().size() == 0){
                current.getChildren().remove(word);
            }
            if(node.getChildren().size() > 1){
                node.setWord(false);
            }
            if(node.getChildren().size() == 1){
                Set<String> keys = node.getChildren().keySet();
                String key  =(String) keys.toArray()[0];
                CompressedTrieNode child = node.getChild(key);
                child.setData(word+child.getData());
                current.getChildren().remove(word);
                current.getChildren().put(child.getData(),child);
            }
            return true;
        }

        if(preWord.length() == node.getData().length()){
            if(deleteInNode(node,word.substring(preWord.length()))){
                if(node.getChildren().size() ==1 && !node.isWord()){
                    Set<String> keys = node.getChildren().keySet();
                    String key  =(String) keys.toArray()[0];
                    CompressedTrieNode child = node.getChild(key);
                    child.setData(preWord+child.getData());
                    current.getChildren().remove(preWord);
                    current.getChildren().put(child.getData(),child);
                }
                return true;
            }else {
                return false;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        CompressedTrieTree trie = new CompressedTrieTree();

        trie.insert("Programming");
        trie.insert("Progming");
        trie.insert("Progm");
        trie.insert("Problem");
        trie.printTree();
        // + 表示是单词的结尾
        System.out.println(trie.find("Progm"));
    }
}

