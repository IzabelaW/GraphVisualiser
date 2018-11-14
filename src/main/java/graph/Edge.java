package graph;

import javafx.scene.Group;
import javafx.scene.shape.Line;

/**
 * Created by izabelawojciak on 15/10/2018.
 */
public class Edge extends Group {

    private Vertex source;
    private Vertex target;
    private Line line;

    public Edge(Vertex source, Vertex target) {
        this.source = source;
        this.target = target;

        line = new Line();
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getTarget() {
        return target;
    }

    public void setView() {
        line.setStartX(source.getX());
        line.setStartY(source.getY());

        line.setEndX(target.getX());
        line.setEndY(target.getY());
        line.setStrokeWidth(0.2);

        getChildren().add(line);
    }
}
