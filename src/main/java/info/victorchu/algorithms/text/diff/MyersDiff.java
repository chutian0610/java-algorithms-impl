package info.victorchu.algorithms.text.diff;

import java.util.Arrays;

/**
 * Myers Diff algorithm.
 *
 */
public class MyersDiff {
    private final Text textA;
    private final Text textB;

    public MyersDiff(Text textA, Text textB) {
        this.textA = textA;
        this.textB = textB;
    }

    public EditScript getEditScript() {
        // 获取两个文本的行数
        int N = textA.size();
        int M = textB.size();
        // 最大编辑距离就是先删除A，再插入B
        int MAX = N + M;
        // K的最大取值范围范围
        int size = 1 + 2 * MAX;
        // 中间点
        int middle = size / 2;
        int[] V = new int[size];
        // 记录V的快照，用于恢复路径
        int[][] trace = new int[MAX + 1][size];
        // 初始值为0
        V[middle] = 0;
        int x, y;
        for (int D = 0; D <= MAX; D++) {
            for (int k = D; k >= -D; k -= 2) {
                // k 每轮都是修改奇数或偶数

                // 如果K是左边界，x值为上一轮编辑距离的值，因为选择向下移动
                // 如果K是右边界，x值为上一轮编辑距离的值+1，因为选择向右移动
                // 否则，如果左边的值小于右边的值，选择向下移动，否则选择向右移动。
                if (k == -D || (k != D && V[middle + k - 1] < V[middle + k + 1])) {
                    x = V[middle + k + 1];
                } else {
                    x = V[middle + k - 1] + 1;
                }
                y = x - k;
                while (x < N && y < M && textA.getLine(x).equals(textB.getLine(y))) {
                    // 对角线移动
                    x++;
                    y++;
                }
                V[middle + k] = x;
                if (x >= N && y >= M) {
                    trace[D] = V.clone();
                    return backtrack(D, middle,trace);
                }
            }
            trace[D] = V.clone();
        }
        return null;
    }

    /**
     * 构建编辑脚本
     *
     * @param D      最大编辑距离
     * @param trace  中间数组
     * @param middle 初始点
     * @return
     */
    private EditScript backtrack(int D, int middle,int[][] trace) {
        int N = textA.size();
        int M = textB.size();
        // 终点
        int x = N;
        int y = M;
        int prev_x = x;
        int prev_y = y;
        EditScript script = new EditScript(textA,textB);
        for (int d = D; d > 0; d--) {
            int[] v = trace[d];
            int k = x - y;
            Operation operation = null;
            if (k == -d || (k != d && v[middle + k - 1] < v[middle + k + 1])) {
                // 向下移动
                prev_x = v[middle + k + 1];
                prev_y = prev_x - (k + 1);
                operation = Operation.INSERT;
            } else {
                // 向右移动
                prev_x =  v[middle + k - 1];
                prev_y = prev_x - (k - 1);
                operation = Operation.DELETE;
            }
            while (x > prev_x && y > prev_y){
                x = x-1;
                y = y-1;
                script.appendEqual(x,y);
            }
            if(operation == Operation.INSERT){
                script.appendInsert(prev_x,prev_y);
            }else {
                script.appendDelete(prev_x,prev_y);
            }
            x= prev_x;
            y= prev_y;
        }
        if(x !=0){
            while (x > 0 && y> 0){
                x = x-1;
                y = y-1;
                script.appendEqual(x,y);
            }
        }
        return script;
    }
}
