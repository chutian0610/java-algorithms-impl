package info.victorchu.algorithms.str.match;

import info.victorchu.utils.PredicateExec;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    public static int firstIndexOf(String target,String pattern){
         int i=0;int j=0;
         int tLength = target.length();
         int pLength = pattern.length();
         if(tLength < pLength){
             return -1;
         }
         int[] next = getNextArrayV2(pattern);
         while (i<tLength && j<pLength){
             if ( target.charAt(i) == pattern.charAt(j)) {
                //如果当前字符匹配成功（即target[i] == pattern[j]），令i++，j++
                i++;
                j++;
            } else {
                 //如果当前字符匹配失败（即target[i] != pattern[j]），则令 i 不变，j = next[j-1]
                 //next[j-1]即为j所对应的next值
                 if(j>0) {
                     j = next[j - 1];
                 }else {
                     // 如果当前字符匹配失败，且j=0，则i++，j不变
                     i++;
                 }
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
    public static List<Integer> indexOf(String target, String pattern){
        List<Integer> result = new ArrayList<>();
        int i=0;int j=0;
        int tLength = target.length();
        int pLength = pattern.length();
        if(tLength < pLength){
            return result;
        }
        int[] next = getNextArrayV2(pattern);
        while (i<=tLength && j<= pLength){
            if(j==pLength){
                // 匹配成功，记录结果
                result.add(i-j);
                // 重置 j
                j=next[j-1];
            }
            if(i==tLength){
                // 到达target末尾 break
                break;
            }
            if ( target.charAt(i) == pattern.charAt(j)) {
                //如果当前字符匹配成功（即target[i] == pattern[j]），令i++，j++
                i++;
                j++;
            } else {
                //如果当前字符匹配失败（即target[i] != pattern[j]），则令 i 不变，j = next[j-1]
                //next[j-1]即为j所对应的next值
                if(j>0) {
                    j = next[j - 1];
                }else {
                    // 如果当前字符匹配失败，且j=0，则i++，j不变
                    i++;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        PredicateExec.check(KMPMatcher.firstIndexOf("acacababababca","abababca") == 6,"firstIndexOf");
        PredicateExec.check(KMPMatcher.firstIndexOf("c","a") == -1,"firstIndexOf");
        List<Integer> result1 = Arrays.stream(new int[]{0,1}).boxed().collect(Collectors.toList());
        PredicateExec.check(CollectionUtils.isEqualCollection(KMPMatcher.indexOf("aa","a"),result1),"indexOf");
        List<Integer> result2 = Arrays.stream(new int[]{0,1}).boxed().collect(Collectors.toList());
        PredicateExec.check(CollectionUtils.isEqualCollection(KMPMatcher.indexOf("aaa","aa"),result2),"indexOf");
    }
}
