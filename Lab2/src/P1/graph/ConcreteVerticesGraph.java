/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
 
    private final List<Vertex<L>> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   every element in vertices represents a vertex and the vertex linked to it.
    // Representation invariant:
    //   the element in vertices shouldn't be repeated. 
    // Safety from rep exposure
    //   all the fields are private final and there is no method which return vertices.
    
    public void checkRep() {
    	Set<L> temp=new HashSet<>();
    	for(int i=0;i<vertices.size();i++) {
    		temp.add(vertices.get(i).getVertexname());
    	}
    	assert vertices.size()==temp.size();
    }
    /**
     * Add a vertex to this graph.
     * 
     * @param vertex label for the new vertex
     * @return true if this graph did not already include a vertex with the
     *         given label; otherwise false (and this graph is not modified)
     */
	@Override public boolean add(L vertex) {
		Vertex<L> v=new Vertex<>(vertex);
    	for(int i=0;i<vertices.size();i++) {
    		if(vertices.get(i).getVertexname().equals(vertex)) {
    			checkRep();
    			return false;
    		}
    	}
    	vertices.add(v);
    	checkRep();
    	return true;
    }
	/**
     * Add, change, or remove a weighted directed edge in this graph.
     * If weight is nonzero, add an edge or update the weight of that edge;
     * vertices with the given labels are added to the graph if they do not
     * already exist.
     * If weight is zero, remove the edge if it exists (the graph is not
     * otherwise modified).
     * 
     * @param source label of the source vertex
     * @param target label of the target vertex
     * @param weight nonnegative weight of the edge
     * @return the previous weight of the edge, or zero if there was no such
     *         edge
     */
    @Override public int set(L source, L target, int weight) {
    	int pweight=0,f1=0,f2=0;
    	
    	for(int i=0;i<vertices.size();i++) {
    		if(vertices.get(i).getVertexname().equals(source))f1=1;
    		if(vertices.get(i).getVertexname().equals(target))f2=1;
    	}
    	if(f1==0)add(source);
    	if(f2==0)add(target);
        for(int i=0;i<vertices.size();i++) {
        	if(vertices.get(i).getVertexname().equals(source)) {
        		
        		if(vertices.get(i).getMap().containsKey(target))pweight=vertices.get(i).getMap().get(target);
        		if(weight!=0) vertices.get(i).put(target, weight);
        		if(weight==0)vertices.get(i).remove(target);
        		checkRep();
        		return pweight;
        	}
        }
        checkRep();
        return 0;
    }
    /**
     * Remove a vertex from this graph; any edges to or from the vertex are
     * also removed.
     * 
     * @param vertex label of the vertex to remove
     * @return true if this graph included a vertex with the given label;
     *         otherwise false (and this graph is not modified)
     */
    @Override public boolean remove(L vertex) {
    	int f=0;
        for(int i=0;i<vertices.size();i++) {
        	if(vertices.get(i).getMap().containsKey(vertex)) {
        		vertices.get(i).remove(vertex);
        		f=1;
        	}
        	if(vertices.get(i).getVertexname().equals(vertex)) {
        		vertices.remove(i);
        		i--;
        		f=1;
        	}
        }
        checkRep();
        return f==1?true:false;
    }
    /**
     * Get all the vertices in this graph.
     * 
     * @return the set of labels of vertices in this graph
     */
    @Override public Set<L> vertices() {
    	Set<L> set=new HashSet<>();
    	for(int i=0;i<vertices.size();i++) {
    		set.add(vertices.get(i).getVertexname());
    	}
    	checkRep();
        return set;
    }
    /**
     * Get the source vertices with directed edges to a target vertex and the
     * weights of those edges.
     * 
     * @param target a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from that vertex to target, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         the key to target
     */
    @Override public Map<L, Integer> sources(L target) {
       Map<L, Integer>map=new HashMap<>();
       for(int i=0;i<vertices.size();i++) {
    	   if(vertices.get(i).getMap().containsKey(target)) {
    		   map.put(vertices.get(i).getVertexname(), vertices.get(i).getMap().get(target));
    	   }
       }
       checkRep();
       return map;
    }
    /**
     * Get the target vertices with directed edges from a source vertex and the
     * weights of those edges.
     * 
     * @param source a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from source to that vertex, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         source to the key
     */
    @Override public Map<L, Integer> targets(L source) {
      for(int i=0;i<vertices.size();i++) {
    	   if(vertices.get(i).getVertexname().equals(source)) {
    		   return vertices.get(i).getMap();
    	   }
       }
      checkRep();
	  return null;
    }
    /**
     * @return a string which include information of the vertex
     */
    @Override
    public String toString() {
    	if(vertices.isEmpty()) {
    		checkRep();
    		return "The graph is empty!";
    	}
    	String string="";
    	for(int i=0;i<vertices.size();i++) {
    		string=string+vertices.get(i).toString();
    	}
    	checkRep();
    	return string;
    }
    
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    // TODO fields
	private L vertexname;
	private Map<L, Integer>map=new HashMap<>();
    // Abstraction function:
    //   vertexname represents the vertex and the key of the map represents the target nodes,
	// the value of the map represents the distance from the source to target.
    // Representation invariant:
    //   vertexname!=null,
    // Safety from rep exposure:
    //   all the fields are private,getMap has defensive copies.
    
    public void checkRep() {
    	assert vertexname!=null;
    	assert !map.containsKey(vertexname);
    }
    public Vertex(L vertex) {
    	this.vertexname=vertex;
    	checkRep();
    }
    /**
     * 
     * @return name of the vertex
     */
    public L getVertexname() {
    	checkRep();
    	return vertexname;
    }    
    /**
     * remove the vertex from the graph
     * @param vertex
     */
    public void remove(L vertex) {
    	map.remove(vertex);
    	checkRep();
    }
    /**
     * put an edge into the map
     * @param target if the target vertex
     * @param value if the weight of the edge 
     */
    public void put(L target,Integer value) {
    	map.put(target, value);
    	checkRep();
    }
    /**
     * 
     * @return information about the vertex,key is the vertex which the vertex connects to,value
     * is the weight of the edge
     */
    public Map<L,Integer> getMap(){
    	checkRep();
    	return map;
    }
    /**
     * return a string which include the information of the vertex
     */
    @Override
    public String toString() {
    	String string=null;
    	if(map.keySet().isEmpty()) {
    		checkRep();
    		return vertexname.toString()+"\n";
    	}
    	for(L key:map.keySet()) {
    		string=string+vertexname.toString()+"->"+key.toString()+" "+map.get(key)+"\n";
    	}
    	checkRep();
    	return string;
    }
}
