package zxh.demo.datastructure.tree;

import java.util.List;

/**
 * Tree:
 * @author zhangxuhai
 * @date 2021/4/6
*/
public interface Tree<E extends Comparable<E>> {
    void add(TreeNode<E> node);

    List<TreeNode<E>> get(E key);

    boolean remove(E key);

    int size();

    List<TreeNode<E>> asOrderedList();
}
