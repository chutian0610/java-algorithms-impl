package info.victorchu.algorithms.hash.consistent.hashfunction;

import info.victorchu.algorithms.hash.MurmurHash;
import info.victorchu.algorithms.hash.consistent.HashFunction;

/**
 * MurmurHash 算法的包装类
 */
public class MurmurHashFunction implements HashFunction {
    @Override
    public long hash(byte[] bytes, int seed) {
        if( seed == 0 ) {
            return hash(bytes, DEFAULT_SEED);
        }
        return MurmurHash.murmurhash3_x86_32(bytes,0,bytes.length,seed);
    }

    @Override
    public long hash(byte[] bytes) {
        return hash(bytes, DEFAULT_SEED);
    }
}
