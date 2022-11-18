package info.victorchu.algorithms.hash.consistent;

import info.victorchu.utils.PredicateExec;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static info.victorchu.utils.Predicates.STRING_NOT_BLANK;

/**
 * 一致性hash中使用的hash方法
 */
public interface HashFunction {
    public static final int DEFAULT_SEED = 104729;

    long hash(byte[] bytes, int seed);

    long hash(byte[] bytes);

    default long hash(String key) {
        return hash(
                PredicateExec.check(STRING_NOT_BLANK
                        , key
                        , "The key to hash cannot be null or empty"
                ).getBytes(StandardCharsets.UTF_8)
        );
    }

    default long hash(String key, int seed) {
        return hash(
                PredicateExec.check(STRING_NOT_BLANK
                        , key
                        , "The key to hash cannot be null or empty"
                ).getBytes(StandardCharsets.UTF_8)
                , seed
        );
    }

    default long hash(long key, int seed) {
        return hash(ByteBuffer.allocate(12).putLong(key).putInt(seed).array());
    }
}
