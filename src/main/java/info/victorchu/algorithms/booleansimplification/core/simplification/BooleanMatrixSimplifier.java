package info.victorchu.algorithms.booleansimplification.core.simplification;

import info.victorchu.algorithms.booleansimplification.core.boolexp.AbstractBooleanExpression;

import java.util.List;

/**
 * @Description:
 * @Date:2022/11/18 13:53
 * @Author:victorchutian
 */
public interface BooleanMatrixSimplifier {

    List<List<AbstractBooleanExpression>> handleBooleanExpression(AbstractBooleanExpression expression);

}
