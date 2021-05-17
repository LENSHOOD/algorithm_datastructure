package zxh.demo.datastructure.tree;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * BinarySearchTree:
 * @author zhangxuhai
 * @date 2021/4/6
*/
public class BinarySearchTree<K extends Comparable<K>> implements Tree<K> {
    private TreeNode<K> root;
    private int size;

    @Override
    public void add(TreeNode<K> node) {
        if (isNull(root)) {
            root = node;
            size++;
            return;
        }

        TreeNode<K> curr = root;
        while (true) {
            if (node.getKey().compareTo(curr.getKey()) < 0) {
                if (curr.getLeft().isEmpty()) {
                    curr.setLeft(node);
                    node.setParent(curr);
                    size++;
                    return;
                }

                curr = curr.getLeft().get();
            } else {
                if (curr.getRight().isEmpty()) {
                    curr.setRight(node);
                    node.setParent(curr);
                    size++;
                    return;
                }

                curr = curr.getRight().get();
            }
        }
    }

    @Override
    public List<TreeNode<K>> get(K key) {
        if (isNull(root)) {
            return List.of();
        }

        ArrayList<TreeNode<K>> resultList = new ArrayList<>();
        TreeNode<K> curr = root;
        while (true) {
            if (curr.getKey().compareTo(key) == 0) {
                resultList.add(curr);
                if (curr.getRight().isEmpty()) {
                    break;
                }
                curr = curr.getRight().get();
            } else if (curr.getKey().compareTo(key) > 0) {
                if (curr.getLeft().isEmpty()) {
                    break;
                }

                curr = curr.getLeft().get();
            } else {
                if (curr.getRight().isEmpty()) {
                    break;
                }

                curr = curr.getRight().get();
            }
        }

        return resultList;
    }

    @Override
    public boolean remove(K key) {
        if (isNull(root)) {
            return false;
        }

        TreeNode<K> curr = root;
        while (true) {
            if (curr.getKey().compareTo(key) == 0) {
                TreeNode<K> newNodeAtPosition;
                // no children
                if (curr.getRight().isEmpty() && curr.getLeft().isEmpty()) {
                    newNodeAtPosition = null;
                } else if (curr.getRight().isEmpty()) {
                    newNodeAtPosition = curr.getLeft().get();
                } else if (curr.getLeft().isEmpty()) {
                    newNodeAtPosition = curr.getRight().get();
                } else {
                    newNodeAtPosition = getSmallest(curr.getRight().get());
                }

                if (curr.getParent().isEmpty()) {
                    root = newNodeAtPosition;
                    size--;
                    return true;
                } else {
                    TreeNode<K> parent = curr.getParent().get();
                    if (parent.getLeft().isPresent() && parent.getLeft().get() == curr) {
                        parent.setLeft(newNodeAtPosition);
                    } else {
                        parent.setRight(newNodeAtPosition);
                    }

                    if (nonNull(newNodeAtPosition)) {
                        newNodeAtPosition.setParent(parent);
                        if (curr.getLeft().isPresent() && curr.getLeft().get() != newNodeAtPosition) {
                            newNodeAtPosition.setLeft(curr.getLeft().get());
                        }

                        if (curr.getRight().isPresent() && curr.getRight().get() != newNodeAtPosition) {
                            newNodeAtPosition.setRight(curr.getRight().get());
                        }
                    }

                    size--;
                    return true;
                }
            } else if (curr.getKey().compareTo(key) > 0) {
                if (curr.getLeft().isEmpty()) {
                    return false;
                }

                curr = curr.getLeft().get();
            } else {
                if (curr.getRight().isEmpty()) {
                    return false;
                }

                curr = curr.getRight().get();
            }
        }
    }

    TreeNode<K> getSmallest(TreeNode<K> rootNode) {
        TreeNode<K> curr = rootNode;
        while (true) {
            if (curr.getLeft().isEmpty()) {
                return curr;
            }

            curr = curr.getLeft().get();
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<TreeNode<K>> asOrderedList() {
        List<TreeNode<K>> orderedList = new ArrayList<>();
        if (nonNull(root)) {
            traverse(root, orderedList);
        }
        return orderedList;
    }

    private void traverse(TreeNode<K> node, List<TreeNode<K>> resultList) {
        if (node.getLeft().isPresent()) {
            traverse(node.getLeft().get(), resultList);
        }

        resultList.add(node);

        if (node.getRight().isPresent()) {
            traverse(node.getRight().get(), resultList);
        }
    }
}
