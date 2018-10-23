package tree;

import java.util.ArrayList;

/**
 * Created by izabelawojciak on 19/10/2018.
 */
public class Node {

    private int level;
    private Node parent;
    private ArrayList<Node> children;
    private int numberOfLeafs;
    private double angle;

    public Node(){
        children = new ArrayList<>();
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

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void addChild(Node child) {
        children.add(child);
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setNumberOfLeafs(int numberOfLeafs) {
        this.numberOfLeafs = numberOfLeafs;
    }

    public int countLeafs() {

        int numberOfLeafs;

        if (children.size() == 0) {
            return 1;
        }
        else {
            numberOfLeafs = 0;
            for (Node node : children) {
                numberOfLeafs += node.countLeafs();
            }
            return numberOfLeafs;
        }
    }

}