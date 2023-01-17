package info.victorchu.algorithms.booleansimplification.core.simplification;

import org.junit.jupiter.api.Test;

/**
 * @Description:
 * @Date:2022/11/18 13:39
 * @Author:victorchutian
 */
class BooleanMatrixV1Test {
    @Test
    public void or(){
        int [][] tmp = new int[][]{
                {1,0,0,0},{0,0,0,1},{1,0,1,1}
        };
        int [][] tmp2 = new int[][]{
                {1,0,0,1},{0,1,0,0},{1,0,1,0}
        };
        BooleanMatrixV1 booleanMatrix1 = new BooleanMatrixV1(3,4,tmp);
        BooleanMatrixV1 booleanMatrix2= new BooleanMatrixV1(3,4,tmp2);
        BooleanMatrixV1 booleanMatrix = booleanMatrix1.or(booleanMatrix2);
        System.out.println(booleanMatrix.print());
        System.out.println(booleanMatrix.simplify().print());

    }

    @Test
    public void simplify() {
        int [][] tmp = new int[][]{
                {1,0,0},{0,1,0},{1,0,1}
        };
        BooleanMatrixV1 booleanMatrix = new BooleanMatrixV1(3,3,tmp);
        System.out.println(booleanMatrix.print());
        booleanMatrix = booleanMatrix.simplify();
        System.out.println(booleanMatrix.print());
    }

    @Test
    public void and() {
        int [][] tmp = new int[][]{
                {1,0,0,0},{0,0,0,1},{1,0,1,1}
        };
        int [][] tmp2 = new int[][]{
                {1,0,0,1},{0,1,0,0},{1,0,1,0}
        };
        BooleanMatrixV1 booleanMatrix1 = new BooleanMatrixV1(3,4,tmp);
        BooleanMatrixV1 booleanMatrix2= new BooleanMatrixV1(3,4,tmp2);
        BooleanMatrixV1 booleanMatrix = booleanMatrix1.and(booleanMatrix2);
        System.out.println(booleanMatrix.print());
        System.out.println(booleanMatrix.simplify().print());
    }
}