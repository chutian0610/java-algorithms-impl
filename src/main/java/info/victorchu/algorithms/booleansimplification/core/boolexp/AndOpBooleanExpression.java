package info.victorchu.algorithms.booleansimplification.core.boolexp;

import static info.victorchu.utils.PredicateExec.check;
import static info.victorchu.utils.Predicates.OBJECT_NOT_NULL;

/**
 * @author victor
 * @mail victorchu0610@outlook.com
 * @date 2019/5/23
 * @version 1.0
 * @description 表示“与”表达式
 */
public class AndOpBooleanExpression extends OperatorBooleanExpression {
    public AbstractBooleanExpression getLeft() {
        return left;
    }

    public AbstractBooleanExpression getRight() {
        return right;
    }

    private  AbstractBooleanExpression left;

    public void setLeft(AbstractBooleanExpression left) {
        this.left = left;
    }

    public void setRight(AbstractBooleanExpression right) {
        this.right = right;
    }

    private  AbstractBooleanExpression right;
    public AndOpBooleanExpression(BooleanOp booleanOp, AbstractBooleanExpression left, AbstractBooleanExpression right) {
        super(booleanOp);
        check(OBJECT_NOT_NULL,left,"and operator left parameter must not null!");
        this.left = left;
        check(OBJECT_NOT_NULL,right,"and operator right parameter must not null!");
        this.right = right;
    }

    public AndOpBooleanExpression(AbstractBooleanExpression left, AbstractBooleanExpression right) {
        this(BooleanOp.AND,left,right);
    }

    @Override
    public boolean equal(AbstractBooleanExpression abstractBooleanExpression) {
        if(abstractBooleanExpression instanceof AndOpBooleanExpression){
            if(left.equal(((AndOpBooleanExpression) abstractBooleanExpression).left)
                    && right.equal(((AndOpBooleanExpression) abstractBooleanExpression).right)){
                return true;
            };
            // 交换律
            if(left.equal(((AndOpBooleanExpression) abstractBooleanExpression).right)
                && right.equal(((AndOpBooleanExpression) abstractBooleanExpression).left)){
                return true;
            }
        }
        return false;
    }

    @Override
    public AbstractBooleanExpression simplifiedNot() {
        return new OrOpBooleanExpression(
                this.getLeft().simplifiedNot(),
                this.getRight().simplifiedNot()
        );
    }

    @Override
    public String print() {
        return "(" + left.print() + " & " + right.print() +")";
    }
}
