package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class StopGraph{
	 private final List<Vertex<Stop>> vertices = new ArrayList<>();
	    
	    // Abstraction function:
	    //   every element in vertices represents a vertex and the vertex linked to it.
	    // Representation invariant:
	    //   the element in vertices shouldn't be repeated. 
	    // Safety from rep exposure
	    //   all the fields are private final and there is no method which return vertices.
	    
	    public void checkRep() {
	    	Set<Stop> temp=new HashSet<>();
	    	for(int i=0;i<vertices.size();i++) {
	    		temp.add(vertices.get(i).getVertexname());
	    	}
	    	assert vertices.size()==temp.size();
	    }
	    
		public boolean add(Stop vertex) {
			Vertex<Stop> v=new Vertex<>(vertex);
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
	    
	    public int set(Stop source,Stop target, int weight) {
	    	int f1=0,f2=0;
	    	
	    	for(int i=0;i<vertices.size();i++) {
	    		if(vertices.get(i).getVertexname().equals(source))f1=1;
	    		if(vertices.get(i).getVertexname().equals(target))f2=1;
	    	}
	    	if(f1==0)add(source);
	    	if(f2==0)add(target);
	        for(int i=0;i<vertices.size();i++) {
	        	if(vertices.get(i).getVertexname().equals(source)) {
	        		
	        		vertices.get(i).put(target, weight);
	        		checkRep();
	        		return 0;
	        	}
	        }
	        checkRep();
	        return 0;
	    }
	    
	    public boolean remove(Stop vertex) {
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
	    
	    public Set<Stop> vertices() {
	    	Set<Stop> set=new HashSet<>();
	    	for(int i=0;i<vertices.size();i++) {
	    		set.add(vertices.get(i).getVertexname());
	    	}
	    	checkRep();
	        return set;
	    }
	    public Stop previous(Stop target) {
	    	for(int i=0;i<vertices.size();i++) {
		    	   if(vertices.get(i).getMap().keySet().contains(target)) {
		    		  // &&vertices.get(i).getVertexname().getId()==2
		    		   if(vertices.get(i).getVertexname().getId()==2)
		    		   return vertices.get(i).getVertexname();
		    		   if(vertices.get(i).getVertexname().getIsDivided()==false)
		    		   return vertices.get(i).getVertexname();
		    	   }
		       }
	    	return null;
	    }
	    public Stop next(Stop source) {
	    	for(int i=0;i<vertices.size();i++) {
		    	   if(vertices.get(i).getVertexname().equals(source)) {
		    		   //System.out.println("bug");
		    		   for(Stop s:vertices.get(i).getMap().keySet()) {
		    			   if(s.getIsDivided()==true&&s.getId()!=0)
				    		   return s;
				    	   if(s.getIsDivided()==false&&s.getId()==0)
				    		   return s;
		    		   }
		    	   }
		       }
	    	return null;
	    }
	    public Map<Stop, Integer> sources(Stop target) {
	       Map<Stop, Integer>map=new HashMap<>();
	       for(int i=0;i<vertices.size();i++) {
	    	   if(vertices.get(i).getMap().containsKey(target)) {
	    		   map.put(vertices.get(i).getVertexname(), vertices.get(i).getMap().get(target));
	    	   }
	       }
	       checkRep();
	       return map;
	    }
	    
	    public Map<Stop, Integer> targets(Stop source) {
	      for(int i=0;i<vertices.size();i++) {
	    	  
	    	   if(vertices.get(i).getVertexname().equals(source)) {
	    		  // System.out.println("bug"+" "+vertices.get(i).getMap().size());
	    		   return vertices.get(i).getMap();
	    	   }
	       }
	      checkRep();
		  return null;
	    }
	    
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
    public L getVertexname() {
    	checkRep();
    	return vertexname;
    }    
    public void remove(L vertex) {
    	map.remove(vertex);
    	checkRep();
    }
    public void put(L target,Integer value) {
    	map.put(target, value);
    	checkRep();
    }
    public Map<L,Integer> getMap(){
    	checkRep();
    	return map;
    }
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