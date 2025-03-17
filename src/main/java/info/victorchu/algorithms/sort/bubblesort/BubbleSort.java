package info.victorchu.algorithms.sort.bubblesort;

import info.victorchu.algorithms.sort.Sorter;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 冒泡排序的实现
 * 1. 比较相邻的元素。如果第一个比第二个大，就交换它们两个；
 * 2. 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
 * 3. 针对所有的元素重复以上的步骤，除了最后一个；
 * 4. 重复步骤1~3，直到排序完成。
 */
public class BubbleSort<T> extends Sorter<T> {

    public BubbleSort(Comparator<T> comparator) {
        super(comparator);
    }

    protected T[] sort(T[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            if (!bubble(array, i)) {
                // 未发生交换
                break;
            }
        }
        return array;
    }

    boolean bubble(T[] array, int endIndex) {
        boolean swap = false;
        for (int i = 0; i < endIndex; i++) {
            if (comparator.compare(array[i], array[i + 1]) > 0) { // stable sort,当相等时，不冒泡
                T tmp = array[i];
                array[i] = array[i + 1];
                array[i + 1] = tmp;
                swap = true;
            }
        }
        return swap;
    }

    public static void main(String[] args) {
        BubbleSort<Integer> bubbleSort = new BubbleSort<>(new Comparator<Integer>() {
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
        bubbleSort.sort(integers);
        System.out.println(Arrays.toString(integers));
    }
}
