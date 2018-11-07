package graph;

import java.util.ArrayList;

/**
 * Created by izabelawojciak on 15/10/2018.
 */
public class Vertex {

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


    public Vertex(int index) {
        this.index = index;
        edges = new ArrayList<>();
        children = new ArrayList<>();
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

    public Vertex getParent() {
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
}
