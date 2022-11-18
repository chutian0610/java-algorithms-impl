package info.victorchu.algorithms.sort.insertionsort;

import info.victorchu.algorithms.sort.Sorter;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author victor
 * @mail victorchu0610@outlook.com
 * @date 2019/4/28
 * @version 1.0
 * @description TODO
 */
public class BinaryInsertSort<T> extends Sorter<T> {
    public BinaryInsertSort(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    protected T[] sort(T[] array) {
        for (int i = 1; i < array.length; i++) {
            T target = array[i];
            if (comparator.compare(array[i - 1], target) > 0) {
                // 需要移动位置
                int index = binarySearch(array, 0, i - 1, target);
                int j = i - 1;
                while (j >= index) {
                    array[j + 1] = array[j];
                    j = j - 1;
                }
                array[index] = target;
            }
        }
        return array;
    }

    /**
     * 返回搜索index
     *
     * @param array
     * @param beginIndex
     * @param endIndex
     * @param target
     * @return
     */
    int binarySearch(T[] array, int beginIndex, int endIndex, T target) {
        if (beginIndex == endIndex) {
            return beginIndex;
        }
        int middle = (beginIndex + endIndex) / 2;
        if (comparator.compare(array[middle], target) > 0) {
            return binarySearch(array, beginIndex, middle, target);
        } else {
            return binarySearch(array, middle + 1, endIndex, target);
        }
    }

    public static void main(String[] args) {
        BinaryInsertSort<Integer> binaryInsertSort = new BinaryInsertSort<>(new Comparator<Integer>() {
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
        binaryInsertSort.sort(integers);
        System.out.println(Arrays.toString(integers));
    }
}
