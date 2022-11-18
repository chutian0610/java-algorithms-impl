package info.victorchu.algorithms.booleansimplification.core.boolexp;

import static info.victorchu.utils.PredicateExec.check;
import static info.victorchu.utils.Predicates.OBJECT_NOT_NULL;

/**
 * @author victor
 * @mail victorchu0610@outlook.com
 * @date 2019/5/23
 * @version 1.0
 * @description 表示组合布尔表达式
 */
public abstract class OperatorBooleanExpression extends AbstractBooleanExpression {
    public BooleanOp getBooleanOp() {
        return booleanOp;
    }

    protected final BooleanOp booleanOp;

    public OperatorBooleanExpression(BooleanOp booleanOp) {
        check(OBJECT_NOT_NULL,booleanOp,"boolean operator must not null!");
        this.booleanOp = booleanOp;
    }
}
