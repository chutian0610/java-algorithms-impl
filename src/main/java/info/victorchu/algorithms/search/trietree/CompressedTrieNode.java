package info.victorchu.algorithms.search.trietree;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author victor
 * @Email victorchu0610@outlook.com
 * @Data 2019/7/30
 * @Version 1.0
 * @Description TODO
 */
public class CompressedTrieNode {
        private Map<String,CompressedTrieNode> children = new HashMap<>();
        private String data;
        private boolean isWord = false;

        public boolean isWord() {
            return isWord;
        }

        public void setWord(boolean word) {
            isWord = word;
        }

        public CompressedTrieNode(String data) {
            this.data = data;
        }

        public CompressedTrieNode(String data, boolean isWord) {
            this.data = data;
            this.isWord = isWord;
        }

        public Map<String, CompressedTrieNode> getChildren() {
            return children;
        }

        public CompressedTrieNode getChild(String name) {
            return children.get(name);
        }

        public void setChildren(Map<String, CompressedTrieNode> children) {
            this.children = children;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getSamePrefix(String word){
            if (word == null || data == null){
                return null;
            }
            int length = word.length()>data.length()?data.length():word.length();
            StringBuilder sb =new StringBuilder();
            for (int i =0;i<length;i++){
                if(data.charAt(i) == word.charAt(i)){
                    sb.append(data.charAt(i));
                }else {
                    break;
                }
            }
            if(sb.length()>0){
                return sb.toString();
            }else {
                return null;
            }
        }
    }