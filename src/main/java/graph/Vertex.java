package graph;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * Created by izabelawojciak on 15/10/2018.
 */
public class Vertex extends Pane {

    private int index;
    private double x;
    private double y;
    private ArrayList<Edge> edges;
    private int level;
    private Vertex parent;
    private ArrayList<Vertex> children;
    private int numberOfLeafs;
    private double angle;
    private double probability;
    private Circle view;


    public Vertex(int index) {
        this.index = index;
        edges = new ArrayList<>();
        children = new ArrayList<>();
        view = new Circle();
    }


    public void setX(double x) {
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public int getDegree() {
        return edges.size();
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge) {
        edges.add(edge);
    }

    public int getIndex() {
        return index;
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public int getNumberOfLeafs() {
        return numberOfLeafs;
    }

    public Vertex getVertexParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addChild(Vertex child) {
        children.add(child);
    }

    public void setNumberOfLeafs(int numberOfLeafs) {
        this.numberOfLeafs = numberOfLeafs;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public void setView(double size, int step){
        view.setCenterX(x);
        view.setCenterY(y);
        view.setRadius(size);

        setColor(step);
        getChildren().add(view);
    }

    private void setColor(int step){

        Color color;
        int degree = edges.size();

        if (degree <= step)
            color = Color.web("rgb(43,72,113)");
        else if (degree <= 2 * step)
            color = Color.web("rgb(5,186,221)");
        else if (degree <= 3 * step)
            color = Color.web("rgb(186,221,5)");
        else if (degree <= 4 * step)
            color = Color.web("rgb(243,223,53)");
        else if (degree <= 5 * step)
            color = Color.web("rgb(255,180,4)");
        else if (degree <= 6 * step)
            color = Color.web("rgb(255,128,7)");
        else
            color = Color.web("rgb(255,57,2)");

        view.setStroke(color);
        view.setFill(color);
    }

    public Vector getVector() {
        return new Vector(this.x, this.y);
    }
}
