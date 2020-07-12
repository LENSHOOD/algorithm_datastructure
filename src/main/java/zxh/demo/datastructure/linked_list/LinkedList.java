package zxh.demo.datastructure.linked_list;

/**
 * LinkedList:
 * @author zhangxuhai
 * @date 2020/7/12
*/
public interface LinkedList<E> {
    public void addHead(E element);

    public void addTail(E element);

    public void add(int index, E element);

    public int indexOf(E element);

    public E getHead();

    public E getTail();

    public E get(int index);

    public void removeHead();

    public void removeTail();

    public void remove(int index);

    public boolean isEmpty();

    public int size();
}
