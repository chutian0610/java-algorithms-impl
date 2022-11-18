package info.victorchu.algorithms.booleansimplification.core.boolexp;

/**
 * @author victor
 * @mail victorchu0610@outlook.com
 * @date 2019/5/23
 * @version 1.0
 * @description 表示原子布尔表达式
 */
public abstract class AtomBooleanExpression extends AbstractBooleanExpression {
    @Override
    public AbstractBooleanExpression simplifiedNot() {
        return new NotOpBooleanExpression(this);
    }
}
