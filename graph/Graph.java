package il.ac.telhai.ds.graph;

import il.ac.telhai.ds.misc.Friendship;

import java.util.*;

public class Graph<V extends Comparable<V>, E> implements IGraph<V, E>{
    private class Edge{
        private V from;
        private V to;
        private E label;

        public Edge(V from, V to, E label) {
            this.from = from;
            this.to = to;
            this.label = label;
        }
        public V getFrom() {
            return from;
        }

        public void setFrom(V from) {
            this.from = from;
        }

        public V getTo() {
            return to;
        }

        public void setTo(V to) {
            this.to = to;
        }
        public E getLabel() {
            return label;
        }

        public void setLabel(E label) {
            this.label = label;
        }
        public double getWeight() {
            if(label instanceof Number)
            {
                return Double.parseDouble(label.toString());
            }
            else if(label instanceof Weighted)
            {
                return ((Weighted) label).getWeight();
            }
            throw new RuntimeException();
        }
    }

    private SortedMap<V, List<Edge>> vertexToEdge;

    public Graph() {
        this.vertexToEdge = new TreeMap<V, List<Edge>>();
    }

    /**
     * Add a new vertex if none with equal item exists, and return null. Otherwise
     * retain the existing vertex incidents.
     *
     * @param v
     */
    @Override
    public void add(V v) {
        if(!vertexToEdge.containsKey(v))
        {
            vertexToEdge.put(v, new LinkedList<Edge>());
        }
    }

    /**
     * @param u
     * @param v
     * @return the label of the edge (u,v)
     */
    @Override
    public E getEdge(V u, V v) {
        if(vertexToEdge.containsKey(u) && vertexToEdge.containsKey(v))
        {
            List<Edge> LLU = vertexToEdge.get(u);
            for(int i = 0; i < LLU.size(); i++)
            {
                if(LLU.get(i).getTo() == v)
                {
                    return LLU.get(i).getLabel();
                }
            }
        }
        return null;
    }

    /**
     * Add a new edge if none exists between the two vertices Otherwise retain the
     * existing edge and replace the item with the given one. If the vertices u or v
     * do not exist, add them to the graph.
     *
     * @param u
     * @param v
     * @param edgeLabel
     */
    @Override
    public E putEdge(V u, V v, E edgeLabel) {
        if(!vertexToEdge.containsKey(u))
        {
            vertexToEdge.put(u, new LinkedList<>());
        }

        if(!vertexToEdge.containsKey(v))
        {
            vertexToEdge.put(v, new LinkedList<>());
        }

        List<Edge> LLU = vertexToEdge.get(u);
        for(int i = 0; i < LLU.size(); i++)
        {
            if(LLU.get(i).getTo() == v)
            {
                E prevLabel = LLU.get(i).getLabel();
                LLU.get(i).setLabel(edgeLabel);
                return prevLabel;
            }
        }
        List<Edge> LLV = vertexToEdge.get(v);
        for(int i=0; i<LLV.size(); i++)
        {
            if(LLV.get(i).getTo() == v)
            {
                E prevLabel = LLU.get(i).getLabel();
                LLV.get(i).setLabel(edgeLabel);
                return prevLabel;
            }
        }
        Edge e = new Edge(u,v,edgeLabel);
        LLU.add(e);
        LLV.add(e);
        return edgeLabel;
    }
    /**
     * @param v
     * @return If the graph contains the vertex v.
     */
    @Override
    public boolean containsVertex(V v) {
        return vertexToEdge.containsKey(v);
    }

    /**
     * Remove the vertex and its edges from the graph, and return its incidents. If
     * the vertex dosn't exit return null.
     *
     * @param v
     */
    @Override
    public void removeVertex(V v) {
        if(containsVertex(v))
        {
            vertexToEdge.remove(v);
        }
    }

    /**
     * @param u
     * @param v
     * @return The label of the edge between the two vertices. Null if the edge does
     * not exist Throws an exception if one of the vertices does not exist.
     */
    @Override
    public E removeEdge(V u, V v) {
        if(!containsVertex(v) || !containsVertex(u))
        {
            throw new RuntimeException();
        }
        E tempLabel = null;
        List<Edge> LLU = vertexToEdge.get(u);
        List<Edge> LLV = vertexToEdge.get(v);
        for(int i = 0; i < LLU.size(); i++)
        {
            if(LLU.get(i).getTo() == v)
            {
                tempLabel = LLU.get(i).getLabel();
                LLU.remove(i);
            }
        }
        for(int j = 0; j < LLV.size(); j++)
        {
            if(LLV.get(j).getFrom() == u && LLV.get(j).getTo() == v)
            {
                tempLabel = LLV.get(j).getLabel();
                LLV.remove(j);
            }
        }
        return tempLabel;
    }

    /**
     * @param u
     * @param v
     * @return The weight of edge (u,v), if it exists. Otherwise return 0.
     */
    @Override
    public double getWeight(V u, V v) {
        if(!containsVertex(v) || !containsVertex(u))
        {
            throw new RuntimeException();
        }
        List<Edge> LLU = vertexToEdge.get(u);
        for(int i = 0; i < LLU.size(); i++) {
            if((LLU.get(i).getTo() == v)
                    || (LLU.get(i).getFrom() == v && LLU.get(i).getTo() == u))
            {
                return LLU.get(i).getWeight();
            }
        }
        return 0;
    }

    /**
     * @return The concatenation of the vertices separated by commas.
     */
    @SuppressWarnings({"unchecked","rawtypes"})
    @Override
    public String toString() {
        if(vertexToEdge.isEmpty())
        {
            return "";
        }
        Iterator it = vertexToEdge.keySet().iterator();
        String str = "";
        while(it.hasNext()) {
            String vertex = it.next().toString();
            str += vertex;
            if(it.hasNext()) {
                str += ",";
            }
        }
        return str;
    }

    /**
     * @returns The concatenation of the vertices separated by newlines Every vertex
     * is printed with a comma separated list of its incident edges. The
     * list is separated from the vertex with a colon.
     */
    @SuppressWarnings({"unchecked","rawtypes"})
    @Override
    public String toStringExtended() {
        if(vertexToEdge.isEmpty()) {
            return "";
        }
        Iterator it = vertexToEdge.keySet().iterator();
        String str = "";
        while(it.hasNext()) {
            V vertex = (V) it.next();

            String tempVertex = vertex.toString();
            str += tempVertex + ":";
            List<Edge> edges = vertexToEdge.get(vertex);
            for(int i = 0; i < edges.size(); i++) {
                str += "{";
                str += edges.get(i).getFrom().toString();
                str += ",";
                str += edges.get(i).getTo().toString() + "}";
                if(!(edges.get(i).getLabel() instanceof Friendship)) {
                    str += "(" + edges.get(i).getLabel() + ")";
                }
                if(i != edges.size()-1)
                    str+= ",";
            }
            if(it.hasNext()) {
                str += "\n";
            }
        }
        System.out.println(str);
        return str;
    }
    /**
     * @param u
     * @param v
     * @return If the the edge (u,v) exists.
     */
    @Override
    public boolean areAdjacent(V u, V v) {
        if(!vertexToEdge.containsKey(u) || !vertexToEdge.containsKey(v)) {
            return false;
        }
        List<Edge> LLU = vertexToEdge.get(u);
        for(int i = 0; i < LLU.size(); i++) {
            if(LLU.get(i).getTo() == v)
            {
                return true;
            }
        }
        List<Edge> LLV = vertexToEdge.get(v);
        for(int i=0; i<LLV.size(); i++) {
            if(LLV.get(i).getFrom() == v && LLV.get(i).getTo() == u)
            {
                return true;
            }
        }
        return false;
    }
}
