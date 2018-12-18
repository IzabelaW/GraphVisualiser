package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vector;
import graph.Vertex;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by izabelawojciak on 19/11/2018.
 */
public class ForcedDirectedAlgorithm implements Algorithm {

    private Graph graph;
    private boolean kamadaKawaii;
    private int paths[][];
    private static int progress = 0;
    private static final double t = 0.9;
    private double screenHeight;
    private double screenWidth;

    public ForcedDirectedAlgorithm(Graph graph, boolean kamadaKawaii) {
        this.graph = graph;
        this.kamadaKawaii = kamadaKawaii;
        paths = new int[graph.getVertices().size()][graph.getVertices().size()];
    }

    @Override
    public void doLogic() {

        setInitialPositions();
        screenHeight = graph.getScrollPane().getViewportBounds().getHeight();
        screenWidth = graph.getScrollPane().getViewportBounds().getWidth();

        if (kamadaKawaii) {
            kamadaKawaii();
        }
        else {
            fruchtermanReingold();
            centerAndScale();
        }

    }

    @Override
    public Graph getFinalGraph() {
        return graph;
    }

    private void setInitialPositions() {
        double angle = (2.0 * Math.PI) / graph.getVertices().size();
        for (int i = 0; i < graph.getVertices().size(); i++) {
            Vertex vertex = graph.getVertex(i);
            vertex.setX(Math.cos(vertex.getIndex() * angle));
            vertex.setY(Math.sin(vertex.getIndex() * angle));
        }
    }

    private void centerAndScale() {
        double xMin = Double.MAX_VALUE;
        double xMax = Double.MIN_VALUE;
        double yMin = Double.MAX_VALUE;
        double yMax = Double.MIN_VALUE;

        for (Vertex vertex : graph.getVertices()) {
            if (vertex.getX() < xMin) {
                xMin = vertex.getX();
            }
            if (vertex.getX() > xMax) {
                xMax = vertex.getX();
            }
            if (vertex.getY() < yMin) {
                yMin = vertex.getY();
            }
            if (vertex.getY() > yMax) {
                yMax = vertex.getY();
            }

        }

        double currentWidth = xMax - xMin;
        double currentHeight = yMax - yMin;

        double xScale = screenWidth / currentWidth;
        double yScale = screenHeight / currentHeight;

        double scale = 0.9 * (xScale < yScale ? xScale : yScale);

        Vector center = new Vector(xMax + xMin, yMax + yMin);
        center.divideByAScalar(2.0);
        center.multiplyByAScalar(scale);

        for (Vertex vertex : graph.getVertices()) {
            double newX = (vertex.getX() * scale) - center.getX();
            double newY = (vertex.getY() * scale) - center.getY();

            vertex.setX(newX);
            vertex.setY(newY);
        }
    }

    private double changeInTheLayout(Graph previousLayout, Graph currentLayout) {
        double sum = 0;
        boolean visited[] = new boolean[currentLayout.getVertices().size()];

        for (int i = 0; i < currentLayout.getVertices().size(); i++) {
            for (int j = i + 1; j < currentLayout.getVertices().size(); j++) {

                if (currentLayout.getEdgeFromIncidenceMatrix(i, j) != null) {
                    Edge previousEdge = previousLayout.getEdgeFromIncidenceMatrix(i, j);
                    Edge currentEdge = currentLayout.getEdgeFromIncidenceMatrix(i, j);

                    Vertex previousSource = previousEdge.getSource();
                    Vertex previousTarget = previousEdge.getTarget();

                    Vertex currentSource = currentEdge.getSource();
                    Vertex currentTarget = currentEdge.getTarget();

                    if (!visited[currentSource.getIndex()]) {
                        double changeOnSourceX = Math.pow(currentSource.getX() - previousSource.getX(), 2);
                        double changeOnSourceY = Math.pow(currentSource.getY() - previousSource.getY(), 2);

                        visited[currentSource.getIndex()] = true;
                        sum += changeOnSourceX + changeOnSourceY;
                    }

                    if (!visited[currentTarget.getIndex()]) {
                        double changeOnTargetX = Math.pow(currentTarget.getX() - previousTarget.getX(), 2);
                        double changeOnTargetY = Math.pow(currentTarget.getY() - previousTarget.getY(), 2);

                        visited[currentTarget.getIndex()] = true;
                        sum += changeOnTargetX + changeOnTargetY;
                    }
                }

            }
        }

        return Math.sqrt(sum);
    }

    private double updateStepLength(double step, double initialEnergy, double energy) {

        double stepToReturn = step;

        if (energy < initialEnergy) {
            progress += 1;

            if (progress >= 1) {
                progress = 0;
                stepToReturn /= t;
            }
        } else {
            progress = 0;
            stepToReturn *= t;
        }

        return stepToReturn;
    }

    private void calculatePaths() {
        for (Vertex vertex : graph.getVertices()) {
            BFS(vertex);
        }
    }

    private void findDiameter() {
        int maxDistance = 0;

        for (int i = 0; i < paths.length; i++){
            for (int j = i + 1; j < paths.length; j++){
                if (paths[i][j] > maxDistance){
                    maxDistance = paths[i][j];
                }
            }
        }
    }

    private void BFS(Vertex vertex) {
        int numberOfVertices = graph.getVertices().size();
        boolean marked[] = new boolean[numberOfVertices];
        LinkedList<Vertex> queue = new LinkedList<>();

        marked[vertex.getIndex()] = true;
        queue.add(vertex);
        vertex.setLevel(0);

        int level;
        while (queue.size() != 0) {
            Vertex currentVertex = queue.poll();
            for (Edge edge : currentVertex.getEdges()) {
                Vertex adjacentVertex;

                if (currentVertex.equals(edge.getSource())) {
                    adjacentVertex = edge.getTarget();
                } else {
                    adjacentVertex = edge.getSource();
                }

                if (!marked[adjacentVertex.getIndex()]) {
                    marked[adjacentVertex.getIndex()] = true;
                    adjacentVertex.setParent(currentVertex);
                    queue.addLast(adjacentVertex);
                    level = adjacentVertex.getVertexParent().getLevel() + 1;
                    adjacentVertex.setLevel(level);
                    paths[vertex.getIndex()][adjacentVertex.getIndex()] = level;
                    paths[adjacentVertex.getIndex()][vertex.getIndex()] = level;
                }
            }
        }
    }

    private void kamadaKawaii() {

    }

    private void fruchtermanReingold() {
        Vector[] movements = new Vector[graph.getVertices().size()];
        boolean converged = false;
        double k = Math.sqrt((screenWidth * screenHeight) / graph.getVertices().size() );
        double step = 10.0 * Math.sqrt(graph.getVertices().size());
        double energy = Double.POSITIVE_INFINITY;
        double previousEnergy;
        int p = 2;
        int iter = 0;

        for (int i = 0; i < movements.length; i++) {
            movements[i] = new Vector(0,0);
        }

        while (!converged) {
            iter++;
            Graph previousGraph = graph.clone();
            previousEnergy = energy;
            energy = 0;

            for (int i = 0; i < graph.getVertices().size(); i++) {
                Vertex vertex = graph.getVertex(i);
                for (int j = i + 1; j < graph.getVertices().size(); j++) {
                    Vertex differentVertex = graph.getVertex(j);

                    Vector delta = vertex.getVector();
                    delta.subtract(differentVertex.getVector());
                    double distance = delta.norm();

                    if (distance > 1000.0){
                        continue;
                    }

                    double repulsion = Math.pow(k, p + 1) / Math.pow(distance,p);

                    delta.divideByAScalar(distance);
                    delta.multiplyByAScalar(repulsion);

                    movements[vertex.getIndex()].add(delta);
                    movements[differentVertex.getIndex()].subtract(delta);
                }
            }

            for (int i = 0; i < graph.getVertices().size(); i++){
                for (int j = i + 1; j < graph.getVertices().size(); j++){
                    Edge edge = graph.getEdgeFromIncidenceMatrix(i, j);
                    if (edge != null) {
                        Vertex source = edge.getSource();
                        Vertex target = edge.getTarget();

                        Vector delta = source.getVector();
                        delta.subtract(target.getVector());

                        double distance = delta.norm();
                        double attraction = Math.pow(distance, p) / k;

                        delta.divideByAScalar(distance);
                        delta.multiplyByAScalar(attraction);

                        movements[source.getIndex()].subtract(delta);
                        movements[target.getIndex()].add(delta);
                    }
                }
            }


            for (Vertex vertex : graph.getVertices()) {
                Vector movement = movements[vertex.getIndex()];
                double movementNorm = movement.norm();

                if (movementNorm < 1.0) {
                    continue;
                }

                double cappedMovementNorm = Math.min(movementNorm, step);
                movement.divideByAScalar(movementNorm);
                movement.multiplyByAScalar(cappedMovementNorm);

                double newX = vertex.getX() + movement.getX();
                double newY = vertex.getY() + movement.getY();

                vertex.setX(newX);
                vertex.setY(newY);

                energy += Math.pow(movementNorm, 2);

            }

            step = updateStepLength(step, previousEnergy, energy);

            if (iter > 1000) {
                converged = true;
            }
            centerAndScale();
        }

        for (Vector vector : movements){
            System.out.println("X: " + vector.getX() + " Y: " + vector.getY());
        }
        System.out.println("-------------");
    }

//    private void fruchtermanReingold() {
//        double tolerance = 20;
//        double k = 15;
//        double step = 10;
//        double energy = Double.POSITIVE_INFINITY;
//        Graph previousGraph;
//        double previousEnergy;
//        double C = 0.2;
//        boolean converged = false;
//        int p = 2;
//
//        while (!converged) {
//            previousGraph = graph.clone();
//            previousEnergy = energy;
//            energy = 0;
//
//            for (Vertex vertex : graph.getVertices()) {
//
//                double attractiveForce = 0;
//                double repulsiveForce = 0;
//                Vector f = new Vector(0,0);
//                double fNorm;
//
//                for (Edge edge : vertex.getEdges()) {
//                    Vertex adjacentVertex;
//                    if (vertex.equals(edge.getSource())) {
//                        adjacentVertex = edge.getTarget();
//                    } else {
//                        adjacentVertex = edge.getSource();
//                    }
//
//                    Vector delta = vertex.getVector();
//                    delta.subtract(adjacentVertex.getVector());
//                    double distance = delta.norm();
//
//                    attractiveForce = (Math.pow(distance, p) / k);
//                    delta.multiplyByAScalar(attractiveForce / distance);
//                    f.add(delta);
//                }
//
//                for (Vertex differentVertex : graph.getVertices()) {
//                    if (!vertex.equals(differentVertex)) {
//                        Vector delta = vertex.getVector();
//                        delta.subtract(differentVertex.getVector());
//                        double distance = delta.norm();
//
//                        repulsiveForce = -1 * C * (Math.pow(k, p + 1)) / Math.pow(distance,p);
//                        delta.multiplyByAScalar(repulsiveForce / distance);
//                        f.add(delta);
//                    }
//                }
//
//            fNorm = f.norm();
//            f.divideByAScalar(fNorm);
//            f.multiplyByAScalar(step);
//
//            double newX = vertex.getX() + f.getX();
//            double newY = vertex.getY() + f.getY();
//
//            double displayX = Math.min(screenWidth, Math.max((-1 * screenWidth), newX));
//            double displayY = Math.min(screenHeight, Math.max((-1 * screenHeight), newY));
//
//            vertex.setX(displayX);
//            vertex.setY(displayY);
//
//            if (changeInTheLayout(previousGraph, graph) < 0.01) {
//                converged = true;
//            }
//
//            energy += Math.pow(fNorm, 2);
//        }
//        step = updateStepLength(step, previousEnergy, energy);
//        }
//    }
}
