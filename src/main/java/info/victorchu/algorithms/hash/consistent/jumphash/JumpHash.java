package info.victorchu.algorithms.hash.consistent.jumphash;

import info.victorchu.algorithms.hash.HashFunction;

/**
 * 跳转Hash.
 * <a href="https://arxiv.org/pdf/1406.2294.pdf">paper</a>
 */
public class JumpHash {

    /* **************** jump hash begin******************* */

    // 7 = 0x0111
    private static final long UNSIGNED_MASK = 0x7fffffffffffffffL;
    private static final long JUMP = 1L << 31;
    private static final long CONSTANT = Long.parseUnsignedLong("2862933555777941757");

    /**
     * key是无符号数
     */
    static int jumpConsistentHash(final long key, final int buckets) {
        long k = key;
        long b = -1;
        long j = 0;

        while (j < buckets) {
            b = j;
            k = k * CONSTANT + 1L;//伪随机数生成器
            // j=(b+1)*x/y则是上面的求上界的公式，其中y/x通过浮点数运算来产生(0,1)内的一个随机数。
            j = (long) ((b + 1L) * (JUMP / toDouble((k >>> 33) + 1L)));
        }
        return (int) b;
    }

    // 无符号long转double
    private static double toDouble(final long n) {
        double d = n & UNSIGNED_MASK;
        if (n < 0) {
            d += 0x1.0p63; // 1*2的63次方
        }
        return d;
    }
    /* **************** jump hash end ******************* */


    /**
     * 散列hash函数
     */
    private final HashFunction hashFunction;

    public int getSize() {
        return size;
    }

    /**
     * 集群机器节点数
     */
    private int size;

    public JumpHash(int size, HashFunction hashFunction) {
        this.size = size;
        this.hashFunction = hashFunction;
    }

    /**
     * 返回key所对应的桶
     *
     * @param key key
     * @return 桶ID
     */
    public int getBucket(String key) {
        return jumpConsistentHash(hashFunction.hash(key), size);
    }

    /**
     * 增加节点
     *
     * @return 总节点数
     */
    public int increase() {
        size = size + 1;
        return size;
    }

    /**
     * 减少节点
     *
     * @return 总节点数
     */
    public int decrease() {

        size = size - 1;
        return size;

    }


}
