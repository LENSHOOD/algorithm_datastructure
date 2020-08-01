package zxh.demo.datastructure.linked_list;

/**
 * LinkedList:
 * @author zhangxuhai
 * @date 2020/7/12
*/
public interface LinkedList<E> {
    void addHead(E element);

    void addTail(E element);

    void add(int index, E element);

    int indexOf(E element);

    E getHead();

    E getTail();

    E get(int index);

    void set(int index, E element);
    
    void removeHead();

    void removeTail();

    void remove(int index);

    boolean isEmpty();

    int size();

    void reverse();

    E[] toArray(E[] array);
}
