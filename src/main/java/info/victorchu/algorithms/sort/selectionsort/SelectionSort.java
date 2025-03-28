package info.victorchu.algorithms.sort.selectionsort;

import info.victorchu.algorithms.sort.Sorter;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 选择排序的实现。
 * 1. 在未排序序列中找到最小（大）元素，存放到排序序列的起始位置
 * 2. 从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。
 * 3. 重复第二步，直到所有元素均排序完毕。
 */
public class SelectionSort<T> extends Sorter<T> {

    public SelectionSort(Comparator<T> comparator) {
        super(comparator);
    }

    protected T[] sort(T[] array) {
        int length = array.length;
        for (int i = 0; i < length - 1; i++) {
            // 1. 找到未排序中的最小值
            int index = getMinFromSubArray(array, i);
            // 2. 放置最小值
            swap(array, i, index);
        }
        return array;
    }

    void swap(T[] array, int target, int source) {
        if (target == source) {
            // do nothing
        } else {
            T tmp = array[target];
            array[target] = array[source];
            array[source] = tmp;
        }
    }

    int getMinFromSubArray(T[] array, int begin) {
        T min = array[begin];
        int minIndex = begin;
        for (int i = begin + 1; i < array.length; i++) {
            if (comparator.compare(min, array[i]) > 0) { // stable selection sort
                min = array[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public static void main(String[] args) {
        SelectionSort<Integer> selectionSort = new SelectionSort<>(new Comparator<Integer>() {
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
        selectionSort.sort(integers);
        System.out.println(Arrays.toString(integers));
    }
}
