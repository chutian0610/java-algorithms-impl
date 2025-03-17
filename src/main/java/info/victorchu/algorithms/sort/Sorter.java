package info.victorchu.algorithms.sort;

import java.util.Comparator;

/**
 * 抽象排序类
 */
public abstract class Sorter<T> {
    /**
     * 比较器
     */
    protected Comparator<T> comparator;

    public Sorter(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    protected abstract T[] sort(T[] array);
}
