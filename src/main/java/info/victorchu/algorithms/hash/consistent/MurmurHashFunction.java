package info.victorchu.algorithms.hash.consistent;

import info.victorchu.algorithms.hash.HashFunction;
import info.victorchu.algorithms.hash.hashfunction.MurmurHash;

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
