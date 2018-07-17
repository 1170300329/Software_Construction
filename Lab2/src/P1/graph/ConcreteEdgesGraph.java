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
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   vertices contains all the vertex,edges contains all the edges.
    // Representation invariant:
    //   vertex in vertices and edges in edges should'd be repeated.
    // 
    // Safety from rep exposure:
    //   All the fields are private and final,Set will be defensive copied and there
    // has no method which can return the list of edges.
    
    
    public void checkRep()
    {
    	boolean flag=false;
    	Set<String> temp=new HashSet<>();
		for(int i=0;i<edges.size();i++) {
			String[] temp1=edges.get(i).toString().split(" ");
			temp.add(temp1[0]);
		}
		if(temp.size()==edges.size())flag=true;
    	assert  flag==true;
    }
    /**
     * @param vertex which will be added to graph,vertex!=null
     * @return if add successfully,return true,else renturn false.
     */
	@Override public boolean add(L vertex) {
        if(!vertices.contains(vertex)) {
        	vertices.add(vertex);
        	checkRep();
        	return true;
        }else {
        	checkRep();
        	return false;
        }
    }
    /**
     * @param source is the source of the edge,target is the target of the edge,weight is the weight of the edge
     * @return the weight of the previous edge 
     */
    @Override public int set(L source, L target, int weight) {
    	Edge<L> temp=new Edge<L>(source, target, weight);
    	int pweight;
    	if(!vertices.contains(source))vertices.add(source);
        if(!vertices.contains(target))vertices.add(target);
    	
    	for(int i=0;i<edges.size();i++){
    		if(temp.myequal(edges.get(i))) {
    			pweight=edges.get(i).getWeight();
    			edges.remove(i);
    			i--;
    			if(weight!=0)edges.add(temp);
    			checkRep();
    			return pweight;
    		}
    	}
    	edges.add(temp);
    	checkRep();
    	return 0;
    }
    /**
     * remove the vertex and the edge connected to the vertex 
     * @param the vertex which will be remove
     */
    @Override public boolean remove(L vertex) {
        if(!vertices.contains(vertex)) {
        	checkRep();
        	return false;
        }else {
        	vertices.remove(vertex);
        	for(int i=0;i<edges.size();i++) {
        		if(edges.get(i).getSource().equals(vertex)||edges.get(i).getTarget().equals(vertex)) {
        			edges.remove(i);
        			i--;
        		}
        	}
        	checkRep();
        	return true;
        }
    }
    /**
     * @return return the set of the vertex in graph
     */
    @Override public Set<L> vertices() {
    	Set<L> copy=new HashSet<>(vertices);
    	checkRep();
        return copy;
    }
    /**
     * @param a vertex as target
     * @return return the map ,key is the source vertex of the target,value is the weight of the edge.
     */
    @Override public Map<L, Integer> sources(L target) {
    	Map<L, Integer> map=new HashMap<>();
        for(int i=0;i<edges.size();i++) {
        	if(edges.get(i).getTarget().equals(target)&&edges.get(i).getWeight()!=0) {
        		map.put(edges.get(i).getSource(),edges.get(i).getWeight());
        	}
        }
        checkRep();
        return map;
    }
    /**
     * @param a vertex as source
     * @return return a map,key is the target vertex of the source,value is the weight of the edge
     */
    @Override public Map<L, Integer> targets(L source) {
    	Map<L, Integer> map=new HashMap<>();
        for(int i=0;i<edges.size();i++) {
        	if(edges.get(i).getSource().equals(source)&&edges.get(i).getWeight()!=0) {
        		map.put(edges.get(i).getTarget(), edges.get(i).getWeight());
        	}
        }
        checkRep();
        return map;
    }
    /**
     * @return return a string which include the information of the graph
     */
    @Override
    public String toString() {
    	   if(vertices.isEmpty()) {
    		   checkRep();
    		   return "empty graph!";
    	   }
    	   if(edges.isEmpty()) {
    		   checkRep();
    		   return "no edges!";
    	   }
    		else {
    			String s = new String();
    			for(int i = 0; i < edges.size(); ++i) {
    				s += edges.get(i).toString();
    			}
    			checkRep();
    			return s;
    		}
    	}   
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
     private final L source;
     private final L target;
     private final int weight;
     // Abstraction function:
     //   represents an edge from source to target,and weight is the value of the edge.
     // Representation invariant:
     //   weight>=0;source!=null;target!=null
     // Safety from rep exposure:
     //	  all fields are private and final
     //   weight is immutable
     public Edge(L souce,L target,int weight){
    	 this.source=souce;
    	 this.target=target;
    	 this.weight=weight;
    	 checkRep();
     }
     /**
      * 
      * @return the source vertex of the vertex
      */
     public L getSource(){
    	 checkRep();
    	 return source;
     }
     /**
      * 
      * @return the target vertex of the vertex 
      */
     public L getTarget() { 
    	 checkRep();
    	 return target;
     }
     /**
      * 
      * @return the weight of the edge
      */
     public int getWeight() {
    	 checkRep();
    	 return weight;
     }
     /**
      * 
      * @param edge
      * @return of the edges have same source and target,return true,else return false
      */
     public boolean myequal(Edge<L> edge) {
    	 if(this.getSource().equals(edge.getSource())&&this.getTarget().equals(edge.getTarget())) {
    		 checkRep();
    		 return true;
    	 }else {
    		 checkRep();
    		 return false;
    	 }
     }
     private void checkRep() {
 		assert source != null;
 		assert target != null;
 		assert weight >= 0;
 	}
     /**
      * return the string which includes the information of the edge.
      */
    @Override
    public String toString() {
    	checkRep();
		return source+"->"+target+" "+weight;
    	
    }
    
}
