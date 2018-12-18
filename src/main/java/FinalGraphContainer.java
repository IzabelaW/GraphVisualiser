import graph.Graph;

/**
 * Created by izabelawojciak on 19/11/2018.
 */
public class FinalGraphContainer {

    private Graph finalGfraph;

        private FinalGraphContainer() {
        }

        public static FinalGraphContainer getInstance() {
            return FinalGraphContainerHolder.INSTANCE;
        }

        public void setFinalGraph(Graph finalGraph) {
            this.finalGfraph = finalGraph;
        }

        public Graph getFinalGfraph() {
            return finalGfraph;
        }

        private static class FinalGraphContainerHolder {
            private static final FinalGraphContainer INSTANCE = new FinalGraphContainer();
        }

}
