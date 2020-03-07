package info.victorchu.algorithms.match.str;

import java.util.Arrays;

/**
 * @Author victor
 * @Email victorchu0610@outlook.com
 * @Version 1.0
 * @Description TODO
 */
public class KMPStringmatcher {
     static void getNext(char[] pattern,int[] next){
        next[0]= -1; // index为0的字符前无字符串
        for (int j = 0; j < pattern.length-1; ++j){
            int k = next[j];
            while (k>0 && pattern[k] != pattern[j]){
                k= next[k];
            }
            if(k == -1 ){
                next[j+1] = 0;
                continue;
            }
            if(pattern[k] == pattern[j]){
                next[j+1] = k+1;
            }
        }
    }

    public static void main(String[] args) {
        char[] pattern = new char[]{'a','b','c','a','b','d'};
        int[] next = new int[pattern.length];
        getNext(pattern,next);
        System.out.println(Arrays.toString(next));
    }
}
