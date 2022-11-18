package info.victorchu.utils;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * 断言工厂
 */
public interface Predicates {
    /**
     * 字符串不为空
     */
    Predicate<String> STRING_NOT_BLANK = s -> s != null && s.trim().length() > 0;
    /**
     * Int 大于 0
     */
    Predicate<Integer> INTEGER_GT_0 = integer -> integer > 0;
    /**
     * Int 大于等于 0
     */
    Predicate<Integer> INTEGER_GTE_0 = integer -> integer >= 0;
    /**
     * 集合不为空
     */
    Predicate<Collection<?>> COLLECTION_NOT_EMPTY = s -> s != null && !s.isEmpty();
}
