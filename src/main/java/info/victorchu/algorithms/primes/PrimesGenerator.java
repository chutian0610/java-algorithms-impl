package info.victorchu.algorithms.primes;

import java.util.Arrays;

/**
 * @Author victor
 * @Email victorchu0610@outlook.com
 * @Data 2019/8/15
 * @Version 1.0
 * @Description 素数生成器
 */
public class PrimesGenerator {

    /**
     * 递归生成所有素数
     * @param num 素数个数
     * @return
     */
    public static int[] generatePrimes(int num){
        if(num<=0){
            throw new IllegalArgumentException("数组长度必须大于0!");
        }
        // 构造数组
        int[] primes = new int[num];
        primes[0] =2;
        if(num == 1){
            return primes;
        }
        primes[1] =3;
        if(num == 2){
            return primes;
        }

        int i,index,j;
        for(i = 5, index = 2; index < num; i += 2) { // 素数不可能是偶数，所以每次加二
            boolean flag = true;
            // 如果n不是素数, 则n有满足1<d<=sqrt(n)的一个"素数"因子d.
            // 此处利用已有的素数列表
            for(j = 1; j<index; ++j) {

                if(primes[j]*primes[j] <= i && i%primes[j] == 0)
                {
                    flag = false;
                    break;
                }
            }
            if(flag)
                primes[index++] = i;
        }
        return primes;

    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(generatePrimes(1000)));
    }
}
