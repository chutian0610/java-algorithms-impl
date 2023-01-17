package info.victorchu.algorithms.booleansimplification.core.simplification;

import org.junit.jupiter.api.Test;

import static info.victorchu.algorithms.booleansimplification.core.simplification.BooleanMatrixV2.MatrixNode.EMPTY;
import static info.victorchu.algorithms.booleansimplification.core.simplification.BooleanMatrixV2.MatrixNode.MUTEX;
import static info.victorchu.algorithms.booleansimplification.core.simplification.BooleanMatrixV2.MatrixNode.NEGATIVE;
import static info.victorchu.algorithms.booleansimplification.core.simplification.BooleanMatrixV2.MatrixNode.POSITIVE;

/**
 * @Description:
 * @Date:2022/11/18 13:40
 * @Author:victorchutian
 */
class BooleanMatrixV2Test {
    @Test
    public void or() {
        BooleanMatrixV2.MatrixNode[][] tmp = new BooleanMatrixV2.MatrixNode[][]{
                {POSITIVE,EMPTY,EMPTY,EMPTY},
                {EMPTY,NEGATIVE,EMPTY,POSITIVE}
        };
        BooleanMatrixV2.MatrixNode[][] tmp2 = new BooleanMatrixV2.MatrixNode[][]{
                {POSITIVE,EMPTY,EMPTY,POSITIVE},
                {EMPTY,POSITIVE,EMPTY,POSITIVE}
        };
        BooleanMatrixV2 booleanMatrix1 = new BooleanMatrixV2(2,4,tmp);
        BooleanMatrixV2 booleanMatrix2= new BooleanMatrixV2(2,4,tmp2);
        BooleanMatrixV2 booleanMatrix = booleanMatrix1.or(booleanMatrix2);
        System.out.println(booleanMatrix.print());
        System.out.println(booleanMatrix.simplify().print());
    }

    @Test
    public void or2() {
        BooleanMatrixV2.MatrixNode[][] tmp = new BooleanMatrixV2.MatrixNode[][]{
                {POSITIVE,EMPTY,EMPTY,EMPTY},
                {EMPTY,NEGATIVE,EMPTY,POSITIVE}
        };
        BooleanMatrixV2.MatrixNode[][] tmp2 = new BooleanMatrixV2.MatrixNode[][]{
                {POSITIVE,EMPTY,EMPTY,POSITIVE},
                {NEGATIVE,EMPTY,EMPTY,POSITIVE}
        };
        BooleanMatrixV2 booleanMatrix1 = new BooleanMatrixV2(2,4,tmp);
        BooleanMatrixV2 booleanMatrix2= new BooleanMatrixV2(2,4,tmp2);
        BooleanMatrixV2 booleanMatrix = booleanMatrix1.or(booleanMatrix2);
        System.out.println(booleanMatrix.print());
        System.out.println(booleanMatrix.simplify().print());
    }

    @Test
    public void and() {
        BooleanMatrixV2.MatrixNode[][] tmp = new BooleanMatrixV2.MatrixNode[][]{
                {NEGATIVE,EMPTY,EMPTY,EMPTY},
                {EMPTY,EMPTY,EMPTY,NEGATIVE}
        };
        BooleanMatrixV2.MatrixNode[][] tmp2 = new BooleanMatrixV2.MatrixNode[][]{
                {POSITIVE,EMPTY,EMPTY,POSITIVE},
                {EMPTY,POSITIVE,EMPTY,NEGATIVE}
        };
        BooleanMatrixV2 booleanMatrix1 = new BooleanMatrixV2(2,4,tmp);
        BooleanMatrixV2 booleanMatrix2= new BooleanMatrixV2(2,4,tmp2);
        BooleanMatrixV2 booleanMatrix = booleanMatrix1.and(booleanMatrix2);
        System.out.println(booleanMatrix.print());
        System.out.println(booleanMatrix.simplify().print());
    }

    @Test
    public void simplify() {
        BooleanMatrixV2.MatrixNode[][] tmp = new BooleanMatrixV2.MatrixNode[][]{
                {NEGATIVE,EMPTY,EMPTY},
                {EMPTY,MUTEX,EMPTY},
                {NEGATIVE,EMPTY,NEGATIVE},
                {POSITIVE,EMPTY,EMPTY},
                {EMPTY,EMPTY,POSITIVE},
                {EMPTY,EMPTY,NEGATIVE}
        };
        BooleanMatrixV2 booleanMatrix = new BooleanMatrixV2(6,3,tmp);
        System.out.println(booleanMatrix.print());
        booleanMatrix = booleanMatrix.simplify();
        System.out.println(booleanMatrix.print());
    }
}