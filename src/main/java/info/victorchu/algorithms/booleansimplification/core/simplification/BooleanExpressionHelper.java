package info.victorchu.algorithms.booleansimplification.core.simplification;

import info.victorchu.algorithms.booleansimplification.core.boolexp.*;

/**
 * @author victor
 * @mail victorchu0610@outlook.com
 * @date 2019/5/27
 * @version 1.0
 * @description 化简工具类
 */
public class BooleanExpressionHelper {
    /**
     * 将所有的not(combinate expression) 简化为 not(atom expression),用于最小项的布尔变量
     *
     * @param expression
     * @return
     */
    public static AbstractBooleanExpression simplifyNot(AbstractBooleanExpression expression) {
        if (expression instanceof AndOpBooleanExpression) {
            ((AndOpBooleanExpression) expression).setLeft(
                    simplifyNot(((AndOpBooleanExpression) expression).getLeft()));

            ((AndOpBooleanExpression) expression).setRight(
                    simplifyNot(((AndOpBooleanExpression) expression).getRight()));
            return expression;
        }

        if (expression instanceof OrOpBooleanExpression) {
            ((OrOpBooleanExpression) expression).setLeft(
                    simplifyNot(((OrOpBooleanExpression) expression).getLeft()));

            ((OrOpBooleanExpression) expression).setRight(
                    simplifyNot(((OrOpBooleanExpression) expression).getRight()));
            return expression;
        }

        if (expression instanceof NotOpBooleanExpression) {
            if (((NotOpBooleanExpression) expression).getInner() instanceof AtomBooleanExpression) {
                return expression;
            }
            expression = ((NotOpBooleanExpression) expression).getInner().simplifiedNot();
            return simplifyNot(expression);
        }
        // atom
        return expression;
    }
}
