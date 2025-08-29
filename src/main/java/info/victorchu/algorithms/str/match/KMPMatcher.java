package info.victorchu.algorithms.str.match;

import java.util.Arrays;

public class KMPMatcher {

    public static int[] getNextArrayV0(String pattern) {
        int[] next = new int[pattern.length()];
        next[0] = 0;
        for (int i = 1; i < pattern.length(); i++) {
            for (int j = i; j >= 0; j--) {
                if (pattern.substring(0, j).equals(pattern.substring(i - j + 1, i + 1))) {
                    next[i] = j;
                    break;
                }
            }
        }
        return next;
    }

    public static int[] getNextArrayV1(String pattern) {
        int[] next = new int[pattern.length()];
        next[0] = 0;
        for (int i = 1; i < pattern.length(); i++) {
            for (int j = next[i - 1] + 1; j >= 0; j--) {
                // 注意，j 是从前一个字符的 next 前缀函数判断的
                if (pattern.substring(0, j).equals(pattern.substring(i - j + 1, i + 1))) {
                    next[i] = j;
                    break;
                }
            }
        }
        return next;
    }

    public static int[] getNextArrayV2(String pattern) {
        int[] next = new int[pattern.length()];
        next[0] = 0;
        for (int i = 1; i < pattern.length(); i++) {
            int j = next[i - 1];
            while (j > 0 && pattern.charAt(i) != pattern.charAt(j)) {
                j = next[j - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(j)) {
                j++;
            }
            next[i] = j;
        }
        return next;
    }

     public int firstIndexOf(String target,String pattern){
         int i=0;int j=0;
         int tLength = target.length();
         int pLength = pattern.length();
         if(tLength < pLength){
             return -1;
         }
         int[] next = getNextArrayV2(pattern);

         while (i<tLength && j<pLength){
             //如果j = 0，表示从头开始匹配
             //或者当前字符匹配成功（即target[i] == pattern[j]），令i++，j++
            if (j == 0 || target.charAt(i) == pattern.charAt(j)){
                i++;
                j++;
            } else {
                //如果j != 0，且当前字符匹配失败（即target[i] != pattern[j]），则令 i 不变，j = next[j-1]
                //next[j-1]即为j所对应的next值
                j = next[j-1];
            }
         }
         if (j == pLength) {
             // 匹配成功
             return i - j;
         }
         else {
             return -1;
         }
     }

    public static void main(String[] args) {
        String pattern = "abababca";
        System.out.println(new KMPMatcher().firstIndexOf("acacababababca",pattern));
    }
}
