package info.victorchu.algorithms.sort.insertionsort;

import info.victorchu.algorithms.sort.Sorter;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 希尔排序的实现
 * 1. 将数组按照gap = length /2 分组，对每组进行插入排序
 * 2. 缩小gap = gap / 2, 重复执行 1。直至 gap = 1
 */
public class ShellSort<T> extends Sorter<T> {
    public ShellSort(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    protected T[] sort(T[] array) {
        int len = array.length;
        T target = null;
        int j = 0;
        for (int gap = len / 2; gap >= 1; gap = gap / 2) {
            for (int i = gap; i < len; i++) {
                target = array[i];
                // 起始点 j
                j = i - gap;
                // 插入排序
                while (j >= 0 && comparator.compare(array[j],target) > 0) {
                    array[j + gap] = array[j];
                    j -= gap;
                }
                array[j + gap] = target;
            }
        }
        return array;
    }
    public static void main(String[] args) {
        ShellSort<Integer> InsertionSort = new ShellSort<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1 > o2) {
                    return 1;
                }
                if (o1.equals(o2)) {
                    return 0;
                }
                return -1;
            }
        });

        Integer[] integers = new Integer[]{32, 4254, 124, 54, 24, 6763, 3435, 12344, 63, 254};
        InsertionSort.sort(integers);
        System.out.println(Arrays.toString(integers));
    }
}
