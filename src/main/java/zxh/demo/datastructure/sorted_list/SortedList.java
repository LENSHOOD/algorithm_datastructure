package zxh.demo.datastructure.sorted_list;

/**
 * LinkedList:
 * @author zhangxuhai
 * @date 2020/7/12
*/
public interface SortedList<E> {
    void add(E element);

    boolean contains(E element);

    void remove(E element);

    boolean isEmpty();

    int size();

    E[] toArray(E[] array);
}
