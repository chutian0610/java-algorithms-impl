package info.victorchu.algorithms.booleansimplification.core.simplification;

import info.victorchu.algorithms.booleansimplification.core.boolexp.AbstractBooleanExpression;
import info.victorchu.algorithms.booleansimplification.core.boolexp.AndOpBooleanExpression;
import info.victorchu.algorithms.booleansimplification.core.boolexp.NotOpBooleanExpression;
import info.victorchu.algorithms.booleansimplification.core.boolexp.OrOpBooleanExpression;
import info.victorchu.algorithms.booleansimplification.core.boolexp.TestAtomBooleanExpression;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description:
 * @Date:2022/11/18 13:37
 * @Author:victorchutian
 */
class BooleanExpressionHelperTest {
    @Test
    public void simplifyNot() {
        TestAtomBooleanExpression testAtomBooleanExpression1 = new TestAtomBooleanExpression(false);
        TestAtomBooleanExpression testAtomBooleanExpression2 = new TestAtomBooleanExpression(true);
        TestAtomBooleanExpression testAtomBooleanExpression3 = new TestAtomBooleanExpression(false);
        TestAtomBooleanExpression testAtomBooleanExpression4 = new TestAtomBooleanExpression(true);
        AndOpBooleanExpression and1 = new AndOpBooleanExpression(testAtomBooleanExpression1,testAtomBooleanExpression2);
        OrOpBooleanExpression or1 = new OrOpBooleanExpression(
                new NotOpBooleanExpression(and1),testAtomBooleanExpression3);
        OrOpBooleanExpression or2 = new OrOpBooleanExpression(
                new NotOpBooleanExpression(or1),testAtomBooleanExpression4
        );
        System.out.println(or2.print());
        AbstractBooleanExpression abstractBooleanExpression =  BooleanExpressionHelper.simplifyNot(or2);
        System.out.println(abstractBooleanExpression.print());
    }
}