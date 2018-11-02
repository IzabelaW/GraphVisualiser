package graph;

import java.util.ArrayList;

/**
 * Created by izabelawojciak on 15/10/2018.
 */
public class Vertex {

    private int index;
    private double x;
    private double y;
    private int degree;
    private ArrayList<Edge> edges;
    private int level;
    private Vertex parent;
    private ArrayList<Vertex> children;
    private int numberOfLeafs;
    private double angle;


    public Vertex(int index, double x, double y) {
        this.index = index;
        this.x = x;
        this.y = y;
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
        return degree;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void addEdge(Edge edge){
        edges.add(edge);
        degree++;
    }

    public void addEdgeWithoutIncreasingDegree(Edge edge){
        edges.add(edge);
    }

    public void setIndex(int index) {
        this.index = index;
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

    public ArrayList<Vertex> getChildren() {
        return children;
    }

    public void setNumberOfLeafs(int numberOfLeafs) {
        this.numberOfLeafs = numberOfLeafs;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }
}
