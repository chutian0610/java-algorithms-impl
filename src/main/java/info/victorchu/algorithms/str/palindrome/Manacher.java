package info.victorchu.algorithms.str.palindrome;

/**
 * 最长回文子串 - Manacher算法
 */
public class Manacher {
    public String longestPalindrome(String s) {
        String newStr = preProcess(s);
        // rad[i]表示以i为中心的回文的最大半径，i至少为1，即该字符本身
        int [] rad = new int[newStr.length()];
        // right表示已知的回文中，最右的边界的坐标
        int right = -1;
        // id表示已知的回文中，拥有最右边界的回文的中点坐标
        int id = -1;
        // 2.计算所有的rad
        // 这个算法是O(n)的，因为right只会随着里层while的迭代而增长，不会减少。
        for (int i = 0; i < newStr.length(); i ++) {
            int r = 1;
            if (i <= right) {
                // 2.1.确定一个最小的半径, 新的点落在已有回文中，
                // 重点理解
                r = Math.min(rad[id] - i + id, rad[2 * id - i]);
            }
            // 2.2.尝试更大的半径
            while (i - r >= 0 && i + r < newStr.length() && newStr.charAt(i - r) == newStr.charAt(i + r)) {
                r++;
            }
            // 2.3.更新边界和回文中心坐标
            if (i + r - 1> right) { // i的边界 在边界外
                right = i + r - 1;
                id = i;
            }
            rad[i] = r;
        }
        // 3.扫描一遍rad数组，找出最大的半径
        int maxLength = 0;
        int maxValuePos = 0;
        int Pos = 0;
        for (int r : rad) {
            if (r > maxLength) {
                maxLength = r;
                maxValuePos = Pos;
            }
            Pos++;
        }
        int realLen =  maxLength - 1;
        String huiwen;
        StringBuffer realHuiwen = new StringBuffer();
        if (realLen == 1) {
            return newStr.substring(maxValuePos,maxValuePos+1);
        } else {
            huiwen = newStr.substring((maxValuePos + 1 - rad[maxValuePos]), maxValuePos + rad[maxValuePos]);
            //去掉辅助字符
            for (int j = 0; j < huiwen.length(); j++) {
                if (j % 2 != 0)
                    realHuiwen = realHuiwen.append(huiwen.charAt(j));
            }
            return realHuiwen.toString();
        }
    }

    // 为了避免奇数回文和偶数回文的不同处理问题，在原字符串中插入'#'，将所有回文变成奇数回文
    public String preProcess(String str) {
        StringBuilder newStr = new StringBuilder();
        newStr.append('#');
        for (int i = 0; i < str.length(); i ++) {
            newStr.append(str.charAt(i));
            newStr.append('#');
        }
        return newStr.toString();
    }

    public static void main(String[] args) {
        System.out.println(new Manacher().longestPalindrome("abcabba"));
    }
}
