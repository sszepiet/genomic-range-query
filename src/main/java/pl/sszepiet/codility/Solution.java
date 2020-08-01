package pl.sszepiet.codility;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Solution {
    public int[] solution(String S, int[] P, int[] Q) {
        QueryNode[] queryNodes = new QueryNode[S.length()];
        queryNodes[S.length() - 1] = new QueryNode(S.charAt(S.length() - 1), S.length() - 1);
        for (int i = S.length() - 2; i >= 0; i--) {
            queryNodes[i] = new QueryNode(S.charAt(i), i, queryNodes[i + 1]);
        }
        int[] result = new int[P.length];
        for (int i = 0; i < P.length; i++) {
            result[i] = queryNodes[P[i]].getMinimumFactorial(Q[i]);
        }
        return result;
    }

    class QueryNode {
        private final List<FactorialBoundary> boundaries;
        private final FactorialBoundary ownBoundary;

        public QueryNode(char factorialSymbol, int index) {
            this.ownBoundary = new FactorialBoundary(factorialSymbol, index);
            this.boundaries = Collections.emptyList();
        }

        public QueryNode(char factorialSymbol, int index, final QueryNode previousNode) {
            this.ownBoundary = new FactorialBoundary(factorialSymbol, index);
            if (previousNode.ownBoundary.factorialValue == this.ownBoundary.factorialValue) {
                this.boundaries = previousNode.boundaries;
            } else if (previousNode.ownBoundary.factorialValue > this.ownBoundary.factorialValue) {
                this.boundaries = previousNode.boundaries.stream()
                        .filter(b -> this.ownBoundary.factorialValue > b.factorialValue)
                        .collect(Collectors.toList());
            } else {
                this.boundaries = Stream.concat(previousNode.boundaries.stream(), Stream.of(previousNode.ownBoundary))
                .collect(Collectors.toList());
            }
        }

        public int getMinimumFactorial(int destinationIndex) {
            return boundaries.stream()
                    .filter(b -> destinationIndex >= b.boundaryIndex)
                    .findFirst()
                    .map(b -> b.factorialValue)
                    .orElse(this.ownBoundary.factorialValue);
        }

        public String toString() {
            return boundaries.toString();
        }
    }

    class FactorialBoundary {
        int factorialValue;
        int boundaryIndex;

        public FactorialBoundary(char factorialSymbol, int boundaryIndex) {
            switch (factorialSymbol) {
                case 'A': this.factorialValue = 1; break;
                case 'C': this.factorialValue = 2; break;
                case 'G': this.factorialValue = 3; break;
                case 'T': this.factorialValue = 4; break;
            }
            this.boundaryIndex = boundaryIndex;
        }

        public String toString() {
            return "[factorialValue: " + factorialValue + ", boundaryIndex: " + boundaryIndex + "]";
        }
    }
}
