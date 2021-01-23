package zxh.demo.datastructure.sorted_list;

import java.util.Arrays;
import java.util.Random;

/**
 * SkipLinkedList:
 *
 * @author zhangxuhai
 * @date 2021/1/21
 */
public class SkipLinkedList<E extends Comparable<E>> implements SortedList<E> {
    /**
     * sentinel dummy head
     */
    private final Node<E> head = new Node<>(null);
    private Node<E> tail = head;
    private int level = 1;
    private int size;

    @Override
    public void add(E element) {
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

        Node<E> newNode = new Node<>(element);
        for (int i = 0; i < level; i++) {
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

    @Override
    public boolean contains(E element) {
        SearchResult<E> result = search(element);
        Node<E> found = result.updates[0].forwards[0].forward;
        return found != null && found.dataEqual(element);
    }

    @Override
    public void remove(E element) {
        if (isEmpty()) {
            throw new IllegalStateException("Empty List.");
        }

        SearchResult<E> result = search(element);
        Node<E> found = result.updates[0].forwards[0].forward;
        if (found == null || !found.dataEqual(element)) {
            throw new IllegalStateException("No such element.");
        }

        for (int i = 0; i < level; i++) {
            Level<E> currLevel = result.updates[i].forwards[i];
            if (currLevel.forward.equals(found)) {
                currLevel.forward = found.forwards[i].forward;
                currLevel.span += found.forwards[i].span - 1;
            } else {
                currLevel.span -= 1;
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
        // exclude dummy head
        Node<E> currNode = head.forwards[0].forward;
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
        private final Level<E>[] forwards = new Level[MAX_LEVEL];
        private Node<E> backward;
        private final E data;

        public Node(E data) {
            this.data = data;
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
            while (true) {
                Node<E> closestNext = closest.forwards[i].forward;
                // when closestNext is null(end of the level)
                // or bigger than toFind (the find range of the level can be sure)
                // break the while loop and go to next level
                if (closestNext == null || closestNext.compareTo(toFind) >= 0) {
                    break;
                }

                closest = closestNext;

                // rank contains whole numbers of node back to head
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
    private static final double LEVEL_P = 0.25;
    private static final Random RANDOM = new Random();
    private int randomLevel() {
        int initLevel = 1;
        while (RANDOM.nextDouble() < LEVEL_P) {
            initLevel++;
        }
        return initLevel;
    }
}
