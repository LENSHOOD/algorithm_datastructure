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
            accessed.add(curr);
            LinkedList<E> successors = successors(curr).orElse(new TwoWayLinkedList<>());
            for (var i = 0; i < successors.size(); i++) {
                var e = successors.get(i);
                if (accessed.contains(e)) {
                    continue;
                }

                roadMap.put(e, curr);
                if (e.equals(to)) {
                    return getRoad(to, roadMap);
                }
                accessed.add(e);
                tmpQueue.addTail(e);
            }

            if (tmpQueue.isEmpty()) {
                return getRoad(to, roadMap);
            }
            curr = tmpQueue.getHead();
            tmpQueue.removeHead();
        }
    }

    private List<E> getRoad(E end, Map<E, E> roadMap) {
        if (!roadMap.containsKey(end)) {
            return new ArrayList<>();
        }

        var result = new ArrayList<E>();
        var pointer = end;
        while (pointer != null) {
            result.add(pointer);
            pointer = roadMap.get(pointer);
        }
        Collections.reverse(result);

        return result;
    }

    public List<E> dfs(E from, E to) {
        if (from.equals(to)) {
            return List.of(from);
        }

        Set<E> accessed = new HashSet<>();
        Map<E, E> roadMap = new HashMap<>();
        LinkedList<E> tmpStack = new TwoWayLinkedList<>();
        tmpStack.addHead(from);

        E curr;
        while (true) {
            if (tmpStack.isEmpty()) {
                return getRoad(to, roadMap);
            }

            curr = tmpStack.getHead();
            tmpStack.removeHead();
            if (accessed.contains(curr)) {
                continue;
            }

            if (curr.equals(to)) {
                return getRoad(to, roadMap);
            }
            accessed.add(curr);

            LinkedList<E> successors = successors(curr).orElse(new TwoWayLinkedList<>());
            for (int i = successors.size() - 1; i >= 0; i--) {
                var e = successors.get(i);
                tmpStack.addHead(e);
                if (!accessed.contains(e)) {
                    roadMap.put(e, curr);
                }
            }
        }
    }
}
