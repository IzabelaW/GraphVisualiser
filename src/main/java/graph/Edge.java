package graph;

/**
 * Created by izabelawojciak on 15/10/2018.
 */
public class Edge {

    private Vertex source;
    private Vertex target;

    public Edge(Vertex source, Vertex target) {
        this.source = source;
        this.target = target;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getTarget() {
        return target;
    }
}
