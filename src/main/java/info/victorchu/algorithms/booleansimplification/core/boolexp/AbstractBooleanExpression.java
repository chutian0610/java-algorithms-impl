package info.victorchu.algorithms.booleansimplification.core.boolexp;

/**
 * @author victor
 * @mail victorchu0610@outlook.com
 * @date 2019/5/23
 * @version 1.0
 * @description 表示抽象布尔表达式
 */
public abstract  class AbstractBooleanExpression {

    public abstract boolean equal(AbstractBooleanExpression abstractBooleanExpression);

    /**
     * 这个方法是为了将not(AbstractBoolean) 逐级简化到 not(atomBoolean),以用于合成最小项。
     * @return
     */
    public abstract AbstractBooleanExpression simplifiedNot();

    public abstract String print();
}
