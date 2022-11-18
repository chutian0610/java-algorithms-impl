package info.victorchu.algorithms.booleansimplification.core.simplification;

import java.util.ArrayList;
import java.util.List;

/**
 * @author victor
 * @mail victorchu0610@outlook.com
 * @date 2019/5/28
 * @version 1.0
 * @description 化简矩阵
 *  a11 , .... ,a1n
 *  .
 *  .
 *  .
 *  am1 , ... ,amn
 */
public final class BooleanMatrixV1 {
    /**
     * 矩阵列
     */
    private final int n;
    /**
     * 矩阵行
     */
    private final int m;

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }


    public int[][] getMatrix() {
        return matrix;
    }

    private final int[][] matrix;

    public BooleanMatrixV1(int m, int n) {
        this.m = m;
        this.n = n;
        matrix = new int[m][n];
        // init metric
        for (int i =0; i<m;i++){
            for (int j=0;j<n;j++){
                matrix[i][j] = 0;
            }
        }
    }

    public BooleanMatrixV1(int m, int n, int[][] matrixIn) {
        this.m = m;
        this.n = n;
        if(matrixIn.length!=m || matrixIn[0].length !=n){
            throw new IllegalArgumentException("matrix input is not int[m][n] !");
        }
        matrix = new int[m][n];
        // init metric
        for (int i =0; i<m;i++){
            for (int j=0;j<n;j++){
                matrix[i][j] = matrixIn[i][j];
            }
        }
    }

    public BooleanMatrixV1 and(BooleanMatrixV1 b){
        if(this.n != b.n){
            throw new IllegalArgumentException("matrix width must be same!");
        }
        int[][] result = new int[this.m*b.m][this.n];
        for (int i = 0; i <this.m ; i++) {
            for (int j = 0; j <b.m; j++) {
                result[i*b.m+j] = and(this.matrix[i],b.matrix[j]);
            }
        }
        return new BooleanMatrixV1(this.m*b.m,n,result);
    }

    private static int[] and(int[] a,int[] b){
        if (a.length != b.length){
            throw new IllegalArgumentException("matrix width must be same!");
        }

        int[] result = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = a[i] | b[i];
        }
        return result;
    }

    /**
     * or 操作
     * @param b
     * @return
     */
    public BooleanMatrixV1 or(BooleanMatrixV1 b){
        if(this.n != b.n){
            throw new IllegalArgumentException("matrix width must be same!");
        }
        BooleanMatrixV1 result = new BooleanMatrixV1(this.m+b.m,this.n);
        for (int i =0;i<this.m;i++){
            for (int j=0;j<this.n;j++){
                result.matrix[i][j] = this.matrix[i][j];
            }
        }
        for (int i= 0;i<b.m;i++){
            for (int j=0;j<b.n;j++){
                result.matrix[i+this.m][j] = b.matrix[i][j];
            }
        }
        return result;
    }

    /**
     * 吸收操作
     * @return
     */
    public BooleanMatrixV1 simplify(){
        // a copy of current matrix
        int[] record = new int[m];
        for (int i = 0; i <m; i++) {
            record[i] = 0;
        }
        List<int[]> tmpList = new ArrayList<>();
        for (int i =0; i<m;i++){
            for (int j = 0;j<m;j++){
                if(record[i] ==-1){
                    // 已被吸收无需遍历
                    continue;
                }
                if(i!=j && matrixContains(matrix[i],matrix[j]) ) {
                    // 吸收 j
                    record[j] = -1;
                }
            }
        }
        for (int i = 0; i < record.length; i++) {
            if(record[i] != -1){
                tmpList.add(matrix[i]);
            }
        }
        int[][] tmp = new int[tmpList.size()][];
        for (int i=0;i<tmpList.size();i++) {
            tmp[i] = tmpList.get(i);
        }
        return new BooleanMatrixV1(tmp.length,n,tmp);

    }

    private static boolean matrixContains(int[] a, int[] b){
        if (a.length != b.length){
            throw new IllegalArgumentException("matrix width must be same!");
        }
        for (int i=0;i<a.length;i++){
            if(a[i] ==1){
                if(b[i] != 1){
                    return false;
                }
            }
        }
        return true;
    }
    public String print(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<m;i++){
            stringBuilder.append("[");// line begin
            for (int j =0;j<n;j++){
                stringBuilder.append(matrix[i][j]+",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            stringBuilder.append("]\n");// line end
        }
        return stringBuilder.toString();
    }
}
