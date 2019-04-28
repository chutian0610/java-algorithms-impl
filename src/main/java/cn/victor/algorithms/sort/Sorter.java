package cn.victor.algorithms.sort;

import java.util.Comparator;

/**
 * @Author victor
 * @Email victorchu0610@outlook.com
 * @Data 2019/4/13
 * @Version 1.0
 * @Description 抽象排序器
 */
public abstract class Sorter<T> {
    protected Comparator<T> comparator;

    public Sorter(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    protected abstract void sort(T[] array);
}
