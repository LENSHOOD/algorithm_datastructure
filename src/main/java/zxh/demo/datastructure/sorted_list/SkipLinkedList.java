package zxh.demo.datastructure.sorted_list;

import zxh.demo.datastructure.linked_list.LinkedList;
import java.util.Arrays;
import java.util.Random;

/**
 * SkipLinkedList:
 *
 * @author zhangxuhai
 * @date 2021/1/21
 */
public class SkipLinkedList<E extends Comparable<E>> implements SortedList<E> {
    private Node<E> head;
    private Node<E> tail;
    private int level = 1;
    private int size;

    @Override
    public void add(E element) {
        if (isEmpty()) {
            addHead(element);
            return;
        }

        int nodeLevel = randomLevel();
        SearchResult<E> result = search(element);

        // rearrange level
        if (level < nodeLevel) {
            result.updates = Arrays.copyOf(result.updates, nodeLevel);
            result.ranks = Arrays.copyOf(result.ranks, nodeLevel);
            for (int i = nodeLevel - 1; i >= level; i--) {
                result.updates[i] = head;
                result.ranks[i] = 0;
                head.forwards[i].forward = null;
                head.forwards[i].span = size;
            }
            level = nodeLevel;
        }

        Node<E> newNode = new Node<>(element, nodeLevel);
        for (int i = 0; i < result.updates.length; i++) {
            newNode.forwards[i].forward = result.updates[i].forwards[i].forward;
            result.updates[i].forwards[i].forward = newNode;

            newNode.forwards[i].span = result.updates[i].forwards[i].span - (result.ranks[0] - result.ranks[i]);
            result.updates[i].forwards[i].span = result.ranks[0] - result.ranks[i] + 1;
        }

        // update tail if is tail
        if (result.updates[0].equals(tail)) {
            tail = newNode;
        }

        // update backward
        newNode.backward = result.updates[0];

        size++;
    }

    private void addHead(E element) {
        Node<E> headNode = new Node<>(element, 1);
        head = headNode;
        tail = headNode;
        size++;
    }

    @Override
    public boolean contains(E element) {
        SearchResult<E> result = search(element);
        Node<E> found = result.updates[0].forwards[0].forward;
        if (found == null) {
            found = result.updates[0];
        }
        return found.dataEqual(element);
    }

    @Override
    public void remove(E element) {
        if (isEmpty()) {
            throw new IllegalStateException("Empty List.");
        }

        SearchResult<E> result = search(element);
        Node<E> found = result.updates[0].forwards[0].forward;
        if (found == null) {
            found = result.updates[0];
        }

        if (!found.dataEqual(element)) {
            throw new IllegalStateException("No such element.");
        }

        for (int i = 0; i < result.updates.length; i++) {
            if (result.updates[i].forwards[i].forward.equals(found)) {
                result.updates[i].forwards[i].forward = found.forwards[i].forward;
                result.updates[i].forwards[i].span += found.forwards[i].span - 1;
            } else {
                result.updates[i].forwards[i].span -= 1;
            }
        }

        // update tail if is tail
        if (found.equals(tail)) {
            tail = found.backward;
        }

        // update level
        while (level > 1 && head.forwards[level - 1].forward == null) {
            level--;
        }

        size--;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E[] toArray(E[] array) {
        Object[] elements = new Object[size];
        Node<E> currNode = head;
        for (int i = 0; i < size; i++) {
            elements[i] = currNode.data;
            currNode = currNode.forwards[0].forward;
        }
        return (E[]) Arrays.copyOf(elements, elements.length, array.getClass());
    }

    private static class Level<E extends Comparable<E>> {
        private Node<E> forward;
        private int span;
    }

    private static final int MAX_LEVEL = 64;
    private static class Node<E extends Comparable<E>> implements Comparable<E> {
        private Level<E>[] forwards = new Level[MAX_LEVEL];
        private Node<E> backward;
        private E data;
        private int level;

        public Node(E data, int level) {
            this.data = data;
            this.level = level;
            for (int i = 0; i < MAX_LEVEL; i++) {
                forwards[i] = new Level<>();
            }
        }

        @Override
        public int compareTo(E o) {
            return data.compareTo(o);
        }

        public boolean dataEqual(E element) {
            return compareTo(element) == 0;
        }
    }

    private static class SearchResult<E extends Comparable<E>> {
        private Node<E>[] updates;
        private int[] ranks;

        private SearchResult(Node<E>[] updates, int[] ranks) {
            this.updates = updates;
            this.ranks = ranks;
        }
    }

    private SearchResult<E> search(E toFind) {
        SearchResult<E> result = new SearchResult<>(new Node[level], new int[level]);
        Node<E> closest = head;
        for (int i = level - 1; i >= 0; i--) {
            while (closest.forwards[i].forward != null
                    && closest.forwards[i].forward.compareTo(toFind) < 0) {
                closest = closest.forwards[i].forward;
                result.ranks[i] += closest.forwards[i].span;
            }
            result.updates[i] = closest;
        }
        return result;
    }

    /**
     * Using probability to control the skip depth globally.
     *
     * Statistically, the number of nodes in each level is LEVEL_P times of that in the lower level.
     *
     * If LEVEL_P = 0.5, then the list can be built like a standard binary search list, every level
     * extract 1/2 nodes to upper level.
     *
     * The probability of randomLevel() == 1 is (1 - LEVEL_P),
     * the probability of randomLevel() == 2 is LEVEL_P * (1 - LEVEL_P),
     * ...
     * the probability of randomLevel() == n is LEVEL_P ^ (n-1) * (1 - LEVEL_P).
     *
     * Hence, when LEVEL_P = 0.5, the mathematical expectation of level is:
     *     1 / (1 - LEVEL_P) = 2
     */
    private static final double LEVEL_P = 0.5;
    private static final Random RANDOM = new Random();
    private int randomLevel() {
        int initLevel = 1;
        while (RANDOM.nextDouble() > LEVEL_P) {
            initLevel++;
        }
        return initLevel;
    }
}
