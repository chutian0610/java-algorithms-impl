package info.victorchu.algorithms.sort.mergesort;

import info.victorchu.algorithms.sort.Sorter;
import info.victorchu.algorithms.sort.insertionsort.ShellSort;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 递归归并排序
 * 1. 申请空间，使其大小为两个已经排序序列之和，该空间用来存放合并后的序列
 * 2. 设定两个指针，最初位置分别为两个已经排序序列的起始位置
 * 3. 比较两个指针所指向的元素，选择相对小的元素放入到合并空间，并移动指针到下一位置
 * 4. 重复步骤3直到某一指针到达序列尾
 * 5. 将另一序列剩下的所有元素直接复制到合并序列尾
 */
public class TopDownMergeSort<T>  extends Sorter<T>
{
    public TopDownMergeSort(Comparator<T> comparator) {
        super(comparator);
    }
    @Override
    protected T[] sort(T[] array)
    {
        Object[] temp =new Object[array.length];
        internalMergeSort(array, temp, 0, array.length-1);
        return array;
    }
    private void internalMergeSort(T[] arr, Object[] temp, int left, int right){
        //当left==right的时，已经不需要再划分了
        if (left<right){
            int middle = (left+right)/2;
            internalMergeSort(arr, temp, left, middle);          //左子数组
            internalMergeSort(arr, temp, middle+1, right);       //右子数组
            mergeSortedArray(arr, temp, left, middle, right);    //合并两个子数组
        }
    }
    // 合并两个有序子序列
    private void mergeSortedArray(T[] arr, Object[] temp, int left, int middle, int right){
        int i=left;
        int j=middle+1;
        int k=0;
        while (i<=middle && j<=right){
            temp[k++] = comparator.compare(arr[j],arr[i])>=0 ?
                    arr[i++] : arr[j++];
        }
        while (i <=middle){
            temp[k++] = arr[i++];
        }
        while ( j<=right){
            temp[k++] = arr[j++];
        }
        //把数据复制回原数组
        for (i=0; i<k; ++i){
            arr[left+i] = (T)temp[i];
        }
    }
    public static void main(String[] args) {
        TopDownMergeSort<Integer> InsertionSort = new TopDownMergeSort<>(new Comparator<Integer>() {
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
