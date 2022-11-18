package info.victorchu.algorithms.booleansimplification.core.simplification;

import info.victorchu.algorithms.booleansimplification.core.boolexp.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author victor
 * @mail victorchu0610@outlook.com
 * @date 2019/5/23
 * @version 1.0
 * @description 化简器
 */
public final class BooleanMatrixSimplifierV1 implements BooleanMatrixSimplifier{

    private BooleanMatrixV1 booleanMatrixV1;
    private List<AbstractBooleanExpression> booleanExpressionList = new ArrayList<>();

    /**
     * 新增表达式
     * @param expression
     * @return
     */
    private int addExpression(AbstractBooleanExpression expression){
        int index = -1;
        for (int i = 0; i <booleanExpressionList.size() ; i++) {
            if(booleanExpressionList.get(i).equal(expression)){
                index = i;
                break;
            }
        }
        if(index == -1){
            booleanExpressionList.add(expression);
            return booleanExpressionList.size()-1;
        }else {
            return index;
        }

    }

    /**
     * 获得表达式索引
     * @param expression
     * @return
     */
    private int getExpressionIndex(AbstractBooleanExpression expression){
        int index = -1;
        for (int i = 0; i <booleanExpressionList.size() ; i++) {
            if(booleanExpressionList.get(i).equal(expression)){
                index = i;
                break;
            }
        }
        if(index == -1){
            throw new RuntimeException("not constains this expression:"+expression.print()+",please check!");
        }else {
            return index;
        }

    }

    /**
     * travelExpression 方法中识别 原变量和反变量,被识别成无关系的变量。
     * @param expression
     */
    private void travelExpression(AbstractBooleanExpression expression){
        if(expression instanceof AtomBooleanExpression){
            addExpression(expression);
        }else {
            if(expression instanceof AndOpBooleanExpression){
                travelExpression(((AndOpBooleanExpression) expression).getLeft());
                travelExpression(((AndOpBooleanExpression) expression).getRight());
                return;
            }

            if(expression instanceof OrOpBooleanExpression){
                travelExpression(((OrOpBooleanExpression) expression).getLeft());
                travelExpression(((OrOpBooleanExpression) expression).getRight());
                return;
            }
            if(expression instanceof NotOpBooleanExpression){
                if(((NotOpBooleanExpression) expression).getInner() instanceof AtomBooleanExpression){
                    addExpression(expression);
                }else {
                    travelExpression(((NotOpBooleanExpression) expression).getInner());
                }
            }
        }
    }

    /**
     * 表达式生成矩阵
     * @param expression
     * @return
     */
    private BooleanMatrixV1 getBooleanMatrix(AbstractBooleanExpression expression){
        if(expression instanceof AtomBooleanExpression || expression instanceof NotOpBooleanExpression){
            return buildBoolenMatrixForAtom(expression);
        }else if(expression instanceof AndOpBooleanExpression){
            // 与运算
            return getBooleanMatrix(((AndOpBooleanExpression) expression).getLeft())
                    .and(getBooleanMatrix(((AndOpBooleanExpression) expression).getRight()))
                    .simplify();
        }else {
            // 或运算
            return getBooleanMatrix(((OrOpBooleanExpression) expression).getLeft())
                    .or(getBooleanMatrix(((OrOpBooleanExpression) expression).getRight()))
                    .simplify();
        }
    }

    private BooleanMatrixV1 buildBoolenMatrixForAtom(AbstractBooleanExpression expression){
        if( (expression instanceof AtomBooleanExpression) ||
                (expression instanceof NotOpBooleanExpression
                        && ((NotOpBooleanExpression) expression).getInner() instanceof AtomBooleanExpression)
        ){
            int index = getExpressionIndex(expression);
            int[][] tmp = new int[1][booleanExpressionList.size()];
            for (int j = 0; j <booleanExpressionList.size() ; j++) {
                if(index == j){
                    tmp[0][j] = 1;
                }else {
                    tmp[0][j] =0;
                }
            }
            return new BooleanMatrixV1(1,booleanExpressionList.size(),tmp);
        }
        throw new IllegalArgumentException("error expression:"+expression.print() +",expression must be atom or not(atom)!");
    }

    /**
     * 从矩阵生成化简表达式
     * or { a1 and a2 and a3 ...}
     * or { b1 and b2 and b3 ...}
     * or
     * ...
     *
     * @return
     */
    private List<List<AbstractBooleanExpression>> getExpressionsFromBooleanMatrix(){
        List<List<AbstractBooleanExpression>> list =new ArrayList<>();
        for (int i = 0; i < booleanMatrixV1.getM(); i++) {
            List<AbstractBooleanExpression> expressions = new ArrayList<>();
            for (int j = 0; j < booleanMatrixV1.getN() ; j++) {
                if(booleanMatrixV1.getMatrix()[i][j] ==1){
                    expressions.add(booleanExpressionList.get(j));
                }
            }
            list.add(expressions);
        }
        return list;
    }

    /**
     * 析范式提取矩阵
     * @param expression 简化not expression 后的语法树
     * @return 最小项list
     */
    public List<List<AbstractBooleanExpression>> handleBooleanExpression(AbstractBooleanExpression expression){
        // 1. 获得expression 使用的所有 atom expression ,not(atom) expression
        travelExpression(expression);
        // 2. 生成 expression 对应的 matrix
        booleanMatrixV1 = getBooleanMatrix(expression);
        // 3. 根据 boolean matrix 和 atom list 生成最小项list
        return getExpressionsFromBooleanMatrix();
    }
}
