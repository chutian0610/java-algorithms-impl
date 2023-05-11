package info.victorchu.algorithms.sort;

import java.util.Comparator;

/**
 * @author victor
 * @mail victorchu0610@outlook.com
 * @date 2019/4/13
 * @version 1.0
 * @description 抽象排序器
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
