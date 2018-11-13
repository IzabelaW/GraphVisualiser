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

    public void setView(int numberOfEdges){
        double size = 500.0 * (edges.size() / (2.0 * numberOfEdges));

        System.out.println(edges.size());

        view.setCenterX(x);
        view.setCenterY(y);
        view.setRadius(size);

        setColor();
        getChildren().add(view);
    }

    private void setColor(){

        Color color;
        int degree = edges.size();

        if (degree <= 10)
            color = Color.DARKBLUE;
        else if (degree <= 20)
            color = Color.MEDIUMBLUE;
        else if (degree <= 30)
            color = Color.ROYALBLUE;
        else if (degree <= 40)
            color = Color.DODGERBLUE;
        else if (degree <= 50)
            color = Color.FORESTGREEN;
        else if (degree <= 60)
            color = Color.OLIVEDRAB;
        else if (degree <= 70)
            color = Color.DARKKHAKI;
        else if (degree <= 80)
            color = Color.YELLOWGREEN;
        else if (degree <= 90)
            color = Color.KHAKI;
        else if (degree <= 100)
            color = Color.GOLD;
        else if (degree <= 110)
            color = Color.YELLOW;
        else if (degree <= 120)
            color = Color.LIGHTSALMON;
        else if (degree <= 130)
            color = Color.ORANGE;
        else if (degree <= 140)
            color = Color.DARKORANGE;
        else if (degree <= 150)
            color = Color.ORANGERED;
        else
            color = Color.RED;

        view.setStroke(color);
        view.setFill(color);
    }
}
