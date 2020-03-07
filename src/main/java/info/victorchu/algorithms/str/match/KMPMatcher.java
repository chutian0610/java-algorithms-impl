package info.victorchu.algorithms.str.match;

public class KMPMatcher {

     // 从模式中提取 next 数组
     public void getNextArray(char[] pattern,int[] next) {
         next[0]= -1; // index为0的字符前无字符串
         for (int j = 0; j < pattern.length-1; ++j) {
             // pattern[0~k-1] 与pattern[j-k ~ j-1] 相同
             // 如果pattern[j] 匹配失败,下一步匹配 pattern[k].
             int k = next[j];
             while (k > 0 && pattern[k] != pattern[j]) {
                 k = next[k];
             }
             if (k == -1) {
                 next[j + 1] = 0;
                 continue;
             }
             if (pattern[k] == pattern[j]) {
                 // 如果pattern[j+1] 匹配失败,下一步匹配 pattern[k+1].
                 // 但是如果 pattern[j+1] == pattern[k+1],这一步是可以省略的.直接判断 next[k+1]
                 int x = j + 1;
                 int y = k + 1;
                 while (y > 0 && pattern[x] == pattern[y]) {
                     y = next[y];
                 }
                 next[x] = y;
             }
         }
     }

     public int indexOf(String target,String pattern){
         int i=0;int j=0;
         int tLength = target.length();
         int pLength = pattern.length();
         if(tLength < pLength){
             return -1;
         }
         int[] next = new int[pattern.length()];
         getNextArray(pattern.toCharArray(),next);

         while (i<tLength && j<pLength){
             //如果j = -1，或者当前字符匹配成功（即target[i] == pattern[j]），令i++，j++
            if (j == -1 || target.charAt(i) == pattern.charAt(j)){
                i++;
                j++;
            } else {
                //如果j != -1，且当前字符匹配失败（即target[i] != pattern[j]），则令 i 不变，j = next[j]
                //next[j]即为j所对应的next值
                j = next[j];
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
        System.out.println(new KMPMatcher().indexOf("abacababc","abab"));
    }
}
