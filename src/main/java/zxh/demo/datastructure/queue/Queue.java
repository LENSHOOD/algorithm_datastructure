package zxh.demo.datastructure.queue;

/**
 * Queue:
 * @author zhangxuhai
 * @date 2020/7/16
*/
public interface Queue<E> {
    void enqueue(E element);

    E dequeue();

    int size();

    boolean isEmpty();
}
