package info.victorchu.algorithms.booleansimplification.core.boolexp;

import static info.victorchu.utils.PredicateExec.check;
import static info.victorchu.utils.Predicates.OBJECT_NOT_NULL;

/**
 * @author victor
 * @mail victorchu0610@outlook.com
 * @date 2019/5/23
 * @version 1.0
 * @description 表示取反表达式
 */
public class NotOpBooleanExpression extends OperatorBooleanExpression {
    public AbstractBooleanExpression getInner() {
        return inner;
    }

    public void setInner(AbstractBooleanExpression inner) {
        this.inner = inner;
    }

    private AbstractBooleanExpression inner;
    public NotOpBooleanExpression(BooleanOp booleanOp, AbstractBooleanExpression inner) {
        super(booleanOp);
        check(OBJECT_NOT_NULL,inner,"not operator inner parameter must not null!");
        this.inner = inner;
    }

    public NotOpBooleanExpression(AbstractBooleanExpression inner){
        this(BooleanOp.NOT,inner);
    }

    @Override
    public boolean equal(AbstractBooleanExpression abstractBooleanExpression) {
        if(abstractBooleanExpression instanceof NotOpBooleanExpression){
            return inner == ((NotOpBooleanExpression) abstractBooleanExpression).inner;
        }
        return false;
    }

    @Override
    public AbstractBooleanExpression simplifiedNot() {
        return this.getInner();
    }

    @Override
    public String print() {
        return "!(" + inner.print() +")";
    }
}
