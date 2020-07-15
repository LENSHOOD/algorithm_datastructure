package zxh.demo.datastructure.stack;

/**
 * Stack:
 * @author zhangxuhai
 * @date 2020/7/15
*/
public interface Stack<E> {
    void push(E element);

    E pop();

    int size();

    boolean isEmpty();
}
