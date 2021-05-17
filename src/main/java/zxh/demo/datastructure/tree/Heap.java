package zxh.demo.datastructure.tree;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Heap:
 * @author zhangxuhai
 * @date 2021/5/17
*/
public class Heap<E extends Comparable<E>> {
    private final E[] innerArr;

    public Heap(E[] arr) {
        this.innerArr = Arrays.copyOf(arr, arr.length);
        heapify();
    }

    void heapify() {
        // exclude all leaf
        for (int lastParent = (innerArr.length - 1) / 2; lastParent >= 0; lastParent--) {
            heapifyChildHeap(lastParent);
        }
    }

    private void heapifyChildHeap(int startPos) {
        int currPos = startPos;
        while (true) {
            int maxPos = currPos;
            int leftChildIndex = currPos * 2 + 1;
            int rightChildIndex = currPos * 2 + 2;

            E currNode = innerArr[currPos];
            if (leftChildIndex < innerArr.length
                    && currNode.compareTo(innerArr[leftChildIndex]) < 0) {
                maxPos = leftChildIndex;
            }

            if (rightChildIndex < innerArr.length
                    && innerArr[maxPos].compareTo(innerArr[rightChildIndex]) < 0) {
                maxPos = rightChildIndex;
            }

            if (maxPos == currPos) {
                break;
            }

            swap(currPos, maxPos);
            currPos = maxPos;
        }
    }

    private void swap(int fromIndex, int toIndex) {
        E tmp = innerArr[fromIndex];
        innerArr[fromIndex] = innerArr[toIndex];
        innerArr[toIndex] = tmp;
    }

    public E[] toArray() {
        return Arrays.copyOf(innerArr, innerArr.length);
    }
}