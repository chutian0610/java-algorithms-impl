package info.victorchu.algorithms.booleansimplification.core.boolexp;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Description:
 * @Date:2022/11/18 13:37
 * @Author:victorchutian
 */
public class TestAtomBooleanExpression extends AtomBooleanExpression {
    private static final AtomicInteger currentIndex = new AtomicInteger(1);

    private boolean result;

    public Integer getIndex() {
        return index;
    }

    private Integer index ;
    public TestAtomBooleanExpression(boolean result) {
        this.result = result;
        index = currentIndex.getAndIncrement();
    }

    @Override
    public boolean equal(AbstractBooleanExpression abstractBooleanExpression) {
        if(abstractBooleanExpression instanceof  TestAtomBooleanExpression){
            if(((TestAtomBooleanExpression) abstractBooleanExpression).getIndex().equals(this.index)){
                return true;
            }
        }
        return false;
    }

    @Override
    public String print() {
        return "{" +index +
                '}';
    }
}