package zxh.demo.datastructure.graph;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import zxh.demo.datastructure.linked_list.LinkedList;
import zxh.demo.datastructure.linked_list.TwoWayLinkedList;
import java.util.*;

/**
 * Graph:
 * @author zhangxuhai
 * @date 2021/6/7
*/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Graph<E> {
    private final Map<E, LinkedList<E>> table = new HashMap<>();

    public static class GraphBuilder<E> {
        private final Graph<E> graph = new Graph<>();

        public GraphBuilder<E> put(E from, E to) {
            LinkedList<E> successors = graph.table.computeIfAbsent(from, k -> new TwoWayLinkedList<>());
            successors.addTail(to);
            return this;
        }

        public Graph<E> build() {
            return graph;
        }
    }

    public static <E> GraphBuilder<E> builder() {
        return new GraphBuilder<E>();
    }

    public Optional<LinkedList<E>> successors(E node) {
        return Optional.ofNullable(table.get(node));
    }

    public List<E> bfs(E from, E to) {
        if (from.equals(to)) {
            return List.of(from);
        }

        LinkedList<E> tmpQueue = new TwoWayLinkedList<>();
        Set<E> accessed = new HashSet<>();
        Map<E, E> roadMap = new HashMap<>();
        E curr = from;
        while (true) {
            LinkedList<E> successors = successors(curr).orElse(new TwoWayLinkedList<>());
            for (var i = 0; i < successors.size(); i++) {
                var e = successors.get(i);
                if (accessed.contains(e)) {
                    continue;
                }

                roadMap.put(e, curr);
                if (e.equals(to)) {
                    break;
                }
                accessed.add(e);
                tmpQueue.addTail(e);
            }

            if (tmpQueue.isEmpty()) {
                break;
            }
            curr = tmpQueue.getHead();
            tmpQueue.removeHead();
        }

        if (!roadMap.containsKey(to)) {
            return new ArrayList<>();
        }

        var result = new ArrayList<E>();
        var pointer = to;
        while (pointer != null) {
            result.add(pointer);
            pointer = roadMap.get(pointer);
        }
        Collections.reverse(result);

        return result;
    }

    private boolean found = false;
    public List<E> dfs(E from, E to) {
        if (from.equals(to)) {
            return List.of(from);
        }

        Set<E> accessed = new HashSet<>();
        Map<E, E> roadMap = new HashMap<>();
        dfs(from, to, roadMap, accessed);

        if (!roadMap.containsKey(to)) {
            return new ArrayList<>();
        }

        var result = new ArrayList<E>();
        var pointer = to;
        while (pointer != null) {
            result.add(pointer);
            pointer = roadMap.get(pointer);
        }
        Collections.reverse(result);

        return result;
    }

    private void dfs(E node, E to, Map<E, E> roadMap, Set<E> accessed) {
        if (accessed.contains(node)) {
            return;
        }

        if (found || node.equals(to)) {
            found = true;
            return;
        }

        accessed.add(node);
        LinkedList<E> successors = successors(node).orElse(new TwoWayLinkedList<>());
        for (var i = 0; i < successors.size(); i++) {
            var e = successors.get(i);
            roadMap.put(e, node);
            dfs(e, to, roadMap, accessed);
        }
    }
}
