package zxh.demo.datastructure.graph;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import zxh.demo.datastructure.linked_list.LinkedList;
import zxh.demo.datastructure.linked_list.TwoWayLinkedList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
        return Optional.of(table.get(node));
    }
}
