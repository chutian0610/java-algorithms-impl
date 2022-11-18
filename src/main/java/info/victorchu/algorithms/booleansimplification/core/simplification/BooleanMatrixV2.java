package info.victorchu.algorithms.booleansimplification.core.simplification;

import java.util.ArrayList;
import java.util.List;

/**
 * @author victor
 * @mail victorchu0610@outlook.com
 * @date 2020/6/01
 * @version 2.0
 * @description 化简矩阵
 */
public class BooleanMatrixV2 {
    /**
     * 矩阵节点值类型
     */
    public static enum MatrixNode {
        /**
         * 反变量
         */
        NEGATIVE(0,"-1"),
        /**
         * 空变量
         */
        EMPTY(1,"0"),
        /**
         * 原变量
         */
        POSITIVE(2,"1"),
        /**
         * 互斥
         */
        MUTEX(3,"Ø");

        private String desc;
        private Integer index;

        MatrixNode(Integer index,String desc) {
            this.desc = desc;
            this.index = index;
        }

        /**
         * 真值表
         */
        private static MatrixNode[][] resultTable = new MatrixNode[][]{
            {NEGATIVE,NEGATIVE,MUTEX,MUTEX},
            {NEGATIVE,EMPTY,POSITIVE,MUTEX},
            {MUTEX,POSITIVE,POSITIVE,MUTEX},
            {MUTEX,MUTEX,MUTEX,MUTEX}
        };

        /**
         * 交换律
         * @param node1
         * @param node2
         * @return
         */
        public static MatrixNode calculateAnd(MatrixNode node1,MatrixNode node2) {
            int i = node1.getIndex();
            int j = node2.getIndex();
            return resultTable[i][j];
        }

        /**
         * 获取矩阵节点值说明
         * @return
         */
        public String getDesc() {
            return desc;
        }

        /**
         * 获取矩阵节点值index
         * @return
         */
        public Integer getIndex() {
            return index;
        }
    }

    // =============================== 矩阵操作 ==========================================

    /**
     *  行
     */
    private final int m;
    /**
     * 列
     */
    private final int n;

    /**
     * 矩阵容器，二维数组
     */
    private final MatrixNode[][] matrix;

    public int getM() {
        return m;
    }

    public int getN() {
        return n;
    }

    public MatrixNode[][] getMatrix() {
        return matrix;
    }

    /**
     * 构造器.
     *
     * 创建一个固定大小的空矩阵.
     * @param m
     * @param n
     */
    public BooleanMatrixV2(int m, int n) {
        this.m = m;
        this.n = n;
        matrix = new MatrixNode[m][n];
        // init metric
        for (int i =0; i<m;i++){
            for (int j=0;j<n;j++){
                matrix[i][j] = MatrixNode.EMPTY;
            }
        }
    }

    /**
     * 构造器.
     *
     * 带初始值的矩阵.
     * @param m
     * @param n
     * @param matrixIn
     */
    public BooleanMatrixV2(int m, int n,MatrixNode[][] matrixIn) {
        this.m = m;
        this.n = n;
        if(matrixIn.length!=m || matrixIn[0].length !=n){
            throw new IllegalArgumentException("matrix input is not int[m][n] !");
        }
        matrix = new MatrixNode[m][n];
        // init metric
        for (int i =0; i<m;i++){
            for (int j=0;j<n;j++){
                matrix[i][j] = matrixIn[i][j];
            }
        }
    }

    /**
     * or 操作
     * @param b
     * @return
     */
    public BooleanMatrixV2 or(BooleanMatrixV2 b){
        if(this.n != b.n){
            throw new IllegalArgumentException("matrix width must be same!");
        }
        BooleanMatrixV2 result = new BooleanMatrixV2(this.m+b.m,this.n);
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
     * 与操作
     *
     * @param b
     * @return
     */
    public BooleanMatrixV2 and(BooleanMatrixV2 b){
        if(this.n != b.n){
            throw new IllegalArgumentException("matrix width must be same!");
        }
        MatrixNode[][] result = new MatrixNode[this.m*b.m][this.n];
        for (int i = 0; i <this.m ; i++) {
            for (int j = 0; j <b.m; j++) {
                result[i*b.m+j] = and(this.matrix[i],b.matrix[j]);
            }
        }
        return new BooleanMatrixV2(this.m*b.m,n,result);
    }

    private static MatrixNode[] and(MatrixNode[] a,MatrixNode[] b){
        if (a.length != b.length){
            throw new IllegalArgumentException("matrix width must be same!");
        }

        MatrixNode[] result = new MatrixNode[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = MatrixNode.calculateAnd(a[i], b[i]);
        }
        return result;
    }

    /**
     * 去除互斥行
     * @return
     */
    public BooleanMatrixV2 simplify0(BooleanMatrixV2 matrixV2){
        int[] record = new int[matrixV2.getM()];
        List<MatrixNode[]> tmpList = new ArrayList<>();
        for (int i = 0; i <matrixV2.getM(); i++) {
            record[i] = 0;
        }
        // =============== 化简逻辑  ========================
        for (int i = 0; i <matrixV2.getM(); i++) {
            MatrixNode[] line = matrixV2.getMatrix()[i];
            if(isLineToDelete(line)){
                record[i] = -1;
            }
        }
        // =========================================
        for (int i = 0; i < record.length; i++) {
            if(record[i] != -1){
                tmpList.add(matrixV2.getMatrix()[i]);
            }
        }
        MatrixNode[][] tmp = new MatrixNode[tmpList.size()][];
        for (int i=0;i<tmpList.size();i++) {
            tmp[i] = tmpList.get(i);
        }
        return new BooleanMatrixV2(tmp.length,matrixV2.getN(),tmp);
    }

    /**
     * 吸收化简
     * @return
     */
    public BooleanMatrixV2 simplify1(BooleanMatrixV2 matrixV2){
        int[] record = new int[matrixV2.getM()];
        List<MatrixNode[]> tmpList = new ArrayList<>();
        for (int i = 0; i <matrixV2.getM(); i++) {
            record[i] = 0;
        }
        // =============== 化简逻辑  ========================
        for (int i =0; i<matrixV2.getM();i++){
            for (int j = 0;j<matrixV2.getM();j++){
                if(record[i] ==-1){
                    // 已被简化剔除掉无需遍历
                    continue;
                }
                if(record[j] != -1 && i!=j && matrixContains(matrixV2.getMatrix()[i],matrixV2.getMatrix()[j]) ) {
                    // 吸收简化 j
                    record[j] = -1;
                }
            }
        }
        // =========================================
        for (int i = 0; i < record.length; i++) {
            if(record[i] != -1){
                tmpList.add(matrixV2.getMatrix()[i]);
            }
        }
        MatrixNode[][] tmp = new MatrixNode[tmpList.size()][];
        for (int i=0;i<tmpList.size();i++) {
            tmp[i] = tmpList.get(i);
        }
        return new BooleanMatrixV2(tmp.length,matrixV2.getN(),tmp);
    }


    /**
     * 合并化简
     * @return
     */
    public BooleanMatrixV2 simplify2(BooleanMatrixV2 matrixV2){
        List<MatrixNode[]> tmpList = new ArrayList<>();
        for (int i = 0; i <matrixV2.getM(); i++) {
            tmpList.add(matrixV2.getMatrix()[i]);
        }
        boolean next = true;
        while (next) {
            int length = tmpList.size();
            int i =0; int j=0;
            int pos = -1;

            loop1:
            for (i = 0; i < length; i++) {
                for (j = i + 1; j < length; j++) {
                    int result = merge(tmpList.get(i), tmpList.get(j));
                    if (result > -1) {
                        pos = result;
                        break loop1;
                    }
                }
            }
            if(pos>-1){
                MatrixNode[] tmp = tmpList.get(i);
                MatrixNode[] tmpi = tmpList.get(i);
                MatrixNode[] tmpj = tmpList.get(j);
                tmpList.remove(tmpi);
                tmpList.remove(tmpj);
                tmp[pos] = MatrixNode.EMPTY;
                tmpList.add(tmp);
            }else {
                next = false;
            }
        }
        MatrixNode[][] tmp = new MatrixNode[tmpList.size()][];
        for (int i=0;i<tmpList.size();i++) {
            tmp[i] = tmpList.get(i);
        }
        return new BooleanMatrixV2(tmp.length,matrixV2.getN(),tmp);
    }

    public int merge(MatrixNode[] a,MatrixNode[] b){
        if (a.length != b.length){
            throw new IllegalArgumentException("matrix width must be same!");
        }
        int flag =0;
        int pos = -1;
        for (int i=0;i<a.length;i++){
            if(!b[i].equals(a[i])){
                if(flag>=1){
                    return -1;

                }
                if ( ( b[i].equals(MatrixNode.NEGATIVE) && a[i].equals(MatrixNode.POSITIVE) )
                    || ( b[i].equals(MatrixNode.POSITIVE) && a[i].equals(MatrixNode.NEGATIVE) )){
                    pos = i;
                    flag++;
                }
            }

        }
        return pos;
    }

    /**
     * 化简操作
     * @return
     */
    public BooleanMatrixV2 simplify(){
        BooleanMatrixV2 booleanMatrixV2 = this;
        int length0 =0; int length1= 0;
        do {
            length0= booleanMatrixV2.getM();
            booleanMatrixV2 = simplify0(booleanMatrixV2);
            booleanMatrixV2 = simplify1(booleanMatrixV2);
            booleanMatrixV2 = simplify2(booleanMatrixV2);
            length1= booleanMatrixV2.getM();
        }while (length1!=length0);

        return booleanMatrixV2;
    }

    private static boolean matrixContains(MatrixNode[] a, MatrixNode[] b){
        if (a.length != b.length){
            throw new IllegalArgumentException("matrix width must be same!");
        }
        for (int i=0;i<a.length;i++){
            if(!MatrixNode.EMPTY.equals(a[i])){
                if(!b[i].equals(a[i])){
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isLineToDelete(MatrixNode[] line){
        if(line != null & line.length>0){
            for (MatrixNode ele: line) {
                if(MatrixNode.MUTEX.equals(ele)){
                    return true;
                }
            }
        }
        return false;
    }

    public String print(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<m;i++){
            stringBuilder.append("[");// line begin
            for (int j =0;j<n;j++){
                stringBuilder.append(matrix[i][j].getDesc()+",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            stringBuilder.append("]\n");// line end
        }
        return stringBuilder.toString();
    }
}
