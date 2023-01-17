package info.victorchu.algorithms.booleansimplification.core.simplification;

import info.victorchu.algorithms.booleansimplification.core.boolexp.AbstractBooleanExpression;
import info.victorchu.algorithms.booleansimplification.core.boolexp.AndOpBooleanExpression;
import info.victorchu.algorithms.booleansimplification.core.boolexp.NotOpBooleanExpression;
import info.victorchu.algorithms.booleansimplification.core.boolexp.OrOpBooleanExpression;
import info.victorchu.algorithms.booleansimplification.core.boolexp.TestAtomBooleanExpression;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @Description:
 * @Date:2022/11/18 13:38
 * @Author:victorchutian
 */
class BooleanMatrixSimplifierV1Test {
    @Test
    public void handleBooleanExpression() {
        TestAtomBooleanExpression testAtomBooleanExpression1 = new TestAtomBooleanExpression(false);
        TestAtomBooleanExpression testAtomBooleanExpression2 = new TestAtomBooleanExpression(true);
        TestAtomBooleanExpression testAtomBooleanExpression3 = new TestAtomBooleanExpression(false);
        TestAtomBooleanExpression testAtomBooleanExpression4 = new TestAtomBooleanExpression(true);
        AndOpBooleanExpression and1 = new AndOpBooleanExpression(testAtomBooleanExpression1,testAtomBooleanExpression2);
        AndOpBooleanExpression and2 = new AndOpBooleanExpression(testAtomBooleanExpression4,testAtomBooleanExpression1);
        OrOpBooleanExpression or1 = new OrOpBooleanExpression(
                new NotOpBooleanExpression(and1),testAtomBooleanExpression3);
        OrOpBooleanExpression or2 = new OrOpBooleanExpression(
                new NotOpBooleanExpression(or1),and2
        );
        OrOpBooleanExpression or3 = new OrOpBooleanExpression(
                or2,testAtomBooleanExpression4
        );
        AbstractBooleanExpression abstractBooleanExpression =  BooleanExpressionHelper.simplifyNot(or3);
        System.out.println(abstractBooleanExpression.print());
        BooleanMatrixSimplifierV1 booleanMatrixSimplifierV1 =new BooleanMatrixSimplifierV1();
        System.out.println("=====================================================================");
        List<List<AbstractBooleanExpression>> booleanMatrix = booleanMatrixSimplifierV1.handleBooleanExpression(abstractBooleanExpression);
        StringBuilder sb = new StringBuilder();
        for (int i = 0;i<booleanMatrix.size();i++) {
            List<AbstractBooleanExpression> list= booleanMatrix.get(i);
            sb.append("(");
            for (int j=0;j<list.size();j++) {
                sb.append(list.get(j).print());
                if(j== list.size()-1){

                }else {
                    sb.append(" and ");
                }
            }
            sb.append(")");
            if(i == booleanMatrix.size()-1){

            }else {
                sb.append("\n or \n");

            }
        }
        System.out.println(sb.toString());
    }
}