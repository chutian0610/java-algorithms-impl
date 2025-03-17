package info.victorchu.algorithms.sort.insertionsort;

import info.victorchu.algorithms.sort.Sorter;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 插入排序的实现
 * 1. 把待排序的数组分成已排序和未排序两部分，初始的时候把第一个元素认为是已排好序的。
 * 2. 从第二个元素开始，在已排好序的子数组中寻找到该元素合适的位置并插入该位置。
 * 3. 重复上述过程直到最后一个元素被插入有序子数组中。
 */
public class InsertionSort<T> extends Sorter<T> {
    public InsertionSort(Comparator<T> comparator) {
        super(comparator);
    }

    @Override
    protected T[] sort(T[] array) {
        for (int i = 1; i < array.length; i++) {
            T target = array[i];
            int j = i - 1;
            // 将比目标大的元素都向后移动一位
            while (j >= 0 && comparator.compare(array[j],target)>0) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = target;
        }
        return array;
    }

    public static void main(String[] args) {
        InsertionSort<Integer> InsertionSort = new InsertionSort<>(new Comparator<Integer>() {
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
