package graph;

import tree.Node;

import java.util.ArrayList;

/**
 * Created by izabelawojciak on 15/10/2018.
 */
public class Vertex extends Node{

    private int index;
    private double x;
    private double y;
    private int degree;
    private int eccentricity;
    private ArrayList<Edge> edges;


    public Vertex(int index, double x, double y) {
        super();
        this.index = index;
        this.x = x;
        this.y = y;
        edges = new ArrayList<>();
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

    public void setIndex(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge);
    }

    public void setEccentricity(int eccentricity) {
        this.eccentricity = eccentricity;
    }

    public int getEccentricity() {
        return eccentricity;
    }
}
