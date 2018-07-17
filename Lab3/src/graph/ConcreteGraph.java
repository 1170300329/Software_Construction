package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edge.DirectedEdge;
import edge.Edge;
import edge.HyperEdge;
import edge.UndirectedEdge;
import vertex.Vertex;

public class ConcreteGraph implements Graph<Vertex, Edge>{
	private List<Vertex>vertices=new ArrayList<>();
	private List<Edge>edges=new ArrayList<>();
	private String GraphName;
	// Abstraction function:
	// vertices represents the vertexes included in the graph
	// edges represents the edges included in the graph
	// GraphName represents the name of the graph
	// Representation invariant:
	// all the vertexes in the edges should be in the vertices,GraphName!=null
	// Safety from rep exposure:
	// all the fields are private,GraphName is immutable,and other fields are copied when got
	/**
	 * new a ConcreteGraph with a name
	 * @param name
	 */
	public ConcreteGraph(String name) {
		this.setGraphName(name);
	}
	/**
	 * new a ConcreteGraph with vertices,edges and GraphName
	 * @param vertices
	 * @param edges
	 * @param GraphName
	 */
	public ConcreteGraph(List<Vertex>vertices,List<Edge>edges,String GraphName) {
		this.vertices=new ArrayList<>(vertices);
		this.edges=new ArrayList<>(edges);
		this.GraphName=new String(GraphName);
	}
	/**
	 * get all the edges in the graph
	 * @return Edge
	 * @throws Exception 
	 */
	public List<Edge> getEdge() throws Exception{
		List<Edge> list=new ArrayList<>(edges);
		return list;
	}
	/**
	 * get all the vertexes in the graph
	 * @return list
	 * @throws Exception 
	 */
	public List<Vertex> getVertex() throws Exception{
		List<Vertex>list=new ArrayList<>(vertices);
		return list;
	}
	/**
	 * change the weight of the edge by the index and weight
	 * @param index
	 * @param weight
	 */
	public void reWeight(int index,double weight) {
		edges.get(index).setWeight(weight);
	}
	/**
	 * remove the v from the vertices
	 * @param v
	 * @return
	 */
	public boolean verRemove(Vertex v) {
		if(vertices.contains(v)) {
			vertices.remove(v);
			return true;
		}
		return false;
	}
	/**
	 * remove the e from the edges
	 * @param e
	 * @return
	 */
	public boolean edgeRemove(Edge e) {
		if(edges.contains(e)) {
			edges.remove(e);
			return true;
		}
		return false;
	}
	/**
	 * add v into the vertices,v shouldn't have existed in the vertices
	 * @param v is the vertex to add.
	 * @return If add a vertex successfully,return true.
	 * If the vertex added has existed,return false
	 */
	@Override
	public boolean addVertex(Vertex v) {
		for(int i=0;i<vertices.size();i++) {
			if(vertices.get(i).getLabel().equals(v.getLabel())) {
				System.out.println("Sorry,the vertex already existed");
				return false;
			}
		}
		vertices.add(v);
		return true;
	}
	/**
	 * Remove v from the vertices and remove the edges connected to the vertex.
	 * If the vertex existed in a hyperedge,it will check the hyperedge's number of vertexes.
	 * If the  hyperedge's number of vertexes is less than 1,the edge will be removed as well.
	 * @param v is the vertex to remove.
	 * @return If remove successfully,return true,or return false.
	 */
	@Override
	public boolean removeVertex(Vertex v) throws Exception {
		if(vertices.contains(v)) {
			vertices.remove(v);
			for(int i=0;i<edges.size();i++) {
				if(edges.get(i) instanceof HyperEdge) {
						edges.get(i).removeVer(v);
						if(edges.get(i).getList().size()<=0) {
							edges.remove(i);
							i--;
						}
				}else {
					if(edges.get(i).getList().get(0).equals(v)||
							edges.get(i).getList().get(1).equals(v)) {
						edges.remove(i);
						i--;
					}
				}
			}
			return true;
		}else {
			return false;
		}
	}
	/**
	 * return all the vertexes in the graph,which has defensively copied.
	 * @return set contains all the vertex in the graph
	 * @throws Exception 
	 */
	@Override
	public Set<Vertex> vertices() throws Exception {
		Set<Vertex> set=new HashSet<>(getVertex());
		for(int i=0;i<getVertex().size();i++) {
			set.add((Vertex) getVertex().get(i).deepClone());
		}
		return set;
	}
	/**
	 * Get all the source vertexes for the target,and the weight between them.
	 * @param target vertex
	 * @return A map whose key is the source vertex of the target,and it's value is a list of  
	 * weight of the all the edges between the source vertex and target vertex.
	 */
	@Override
	public Map<Vertex, List<Double>> sources(Vertex target) throws Exception {
		Map<Vertex, List<Double>> map =new HashMap<>();
		for(int i=0;i<edges.size();i++) {
			if(edges.get(i) instanceof UndirectedEdge) {
				if(edges.get(i).getList().get(0).getLabel().equals(target.getLabel())) {
					if(!map.keySet().contains(edges.get(i).getList().get(1))) {
						List<Double>list =new ArrayList<>();
						list.add(edges.get(i).getWeight());
						map.put(edges.get(i).getList().get(1), list);
					}else {
						map.get(edges.get(i).getList().get(1)).add(edges.get(i).getWeight());
					}
				}
				if(edges.get(i).getList().get(1).getLabel().equals(target.getLabel())) {
					if(!map.keySet().contains(edges.get(i).getList().get(0))) {
						List<Double>list =new ArrayList<>();
						list.add(edges.get(i).getWeight());
						map.put(edges.get(i).getList().get(0), list);
					}else {
						map.get(edges.get(i).getList().get(0)).add(edges.get(i).getWeight());
					}
				}
			}
			if(edges.get(i) instanceof DirectedEdge) {
				if(edges.get(i).getList().get(1).getLabel().equals(target.getLabel())) {
					if(!map.keySet().contains(edges.get(i).getList().get(0))) {
						List<Double>list =new ArrayList<>();
						list.add(edges.get(i).getWeight());
						map.put(edges.get(i).getList().get(0), list);
					}else {
						map.get(edges.get(i).getList().get(0)).add(edges.get(i).getWeight());
					}
				}
			}
		}
		return map;
	}
	/**
	 * Get all the target vertexes for the source,and the weight between them.
	 * @param source vertex
	 * @return A map whose key is the target vertex of the source,and it's value is a list of  
	 * weight of the all the edges between the source vertex and target vertex.
	 */
	@Override
	public Map<Vertex, List<Double>> targets(Vertex source) throws Exception {
		Map<Vertex, List<Double>> map =new HashMap<>();
		for(int i=0;i<edges.size();i++) {
			if(edges.get(i) instanceof UndirectedEdge) {
				if(edges.get(i).getList().get(0).getLabel().equals(source.getLabel())) {
					if(!map.keySet().contains(edges.get(i).getList().get(1))) {
						List<Double>list =new ArrayList<>();
						list.add(edges.get(i).getWeight());
						map.put(edges.get(i).getList().get(1), list);
					}else {
						map.get(edges.get(i).getList().get(1)).add(edges.get(i).getWeight());
					}
				}
				if(edges.get(i).getList().get(1).getLabel().equals(source.getLabel())) {
					if(!map.keySet().contains(edges.get(i).getList().get(0))) {
						List<Double>list =new ArrayList<>();
						list.add(edges.get(i).getWeight());
						map.put(edges.get(i).getList().get(0), list);
					}else {
						map.get(edges.get(i).getList().get(0)).add(edges.get(i).getWeight());
					}
				}
			}
			if(edges.get(i) instanceof DirectedEdge) {
				if(edges.get(i).getList().get(0).getLabel().equals(source.getLabel())) {
					if(!map.keySet().contains(edges.get(i).getList().get(1))) {
						List<Double>list =new ArrayList<>();
						list.add(edges.get(i).getWeight());
						map.put(edges.get(i).getList().get(1), list);
					}else {
						map.get(edges.get(i).getList().get(1)).add(edges.get(i).getWeight());
					}
				}
			}
		}
		return map;
	}
	/**
	 * add an edge into the graph,the edge shouldn'd have existed 
	 * @param edge which needs to be added,it is not in the graph before added.
	 * @return If add the edge successfully,return true,or return false.
	 */
	@Override
	public boolean addEdge(Edge edge) throws Exception {
		for(int i=0;i<edges.size();i++) {
			if(edges.get(i).getLabel().equals(edge.getLabel())) {
				System.out.println("there are same vertex in the edge");
			}
		}
		for(int k=0;k<edge.getList().size();k++) {
			boolean flag=false;
			if(edge instanceof HyperEdge)System.out.println(edge.getList().get(k));
			for(int j=0;j<vertices.size();j++) {
				if(edge.getList().get(k).getLabel().equals(vertices.get(j).getLabel()))
					flag=true;
			}
			if(flag==false) {
				System.out.println(edge.toString()+"vertex not found in list");
				System.exit(0);
			}
		}
		List<Vertex>temp=new ArrayList<>();
		for(int m=0;m<edge.getList().size();m++) {
			for(int i=0;i<vertices.size();i++) {
				if(edge.getList().get(m).getLabel().equals(vertices.get(i).getLabel())) {
					temp.add(vertices.get(i));
				}
			}
		}
		edge.listRemove();
		edge.addVertices(temp);
		edges.add(edge);
		return true;
	}
	/**
	 * Remove an edge from the graph.
	 * @param edge needs to be removed.
	 * @return If remove successfully ,return true,or return false.
	 */
	@Override
	public boolean removeEdge(Edge edge) throws Exception {
		for(int i=0;i<edges.size();i++) {
			if(edges.get(i).getLabel().equals(edge.getLabel())) {
				edges.remove(i);
				return true;
			}
		}
		return false;
	}
	/**
	 * return a set containing all the edges in the graph.All the edges are copied defensively. 
	 * @throws Exception 
	 * @return set which contains all the edges in the graph.
	 */
	@Override
	public Set<Edge> edges() throws Exception {
		Set<Edge> set=new HashSet<>();
		for(int i=0;i<edges.size();i++) {
			set.add((Edge) edges.get(i).deepClone());
		}
		return set;
	}
	/**
	 * get the GraphName
	 * @return GraphName
	 */
	public String getGraphName() {
		return GraphName;
	}
	/**
	 * set the GraphName
	 * @param graphName
	 */
	public void setGraphName(String graphName) {
		GraphName = graphName;
	}
	/**
	 * override toString() to show detail information for the graphF
	 */
	@Override
	public String toString() {
		String ans="";
		ans=ans+"Name: "+GraphName+"\n";
		ans=ans+"Vertices: "+"\n";
		for(int i=0;i<vertices.size();i++)
		{
			ans=ans+vertices.get(i).toString()+"\n";
		}
		ans=ans+"edges:"+"\n";
		for(int i=0;i<edges.size();i++) {
			ans=ans+edges.get(i).toString()+"\n";
		}
		return ans;
	}
	/**
	 * remove edge which weights is below valueof
	 * @param valueOf
	 */
	public void removeEdgeBelowN(Integer valueOf) {
		
			for(int i=0;i<edges.size();i++) {
				if(edges.get(i).getWeight()<valueOf) {
					edges.remove(i);
					i--;
				}
			}
	}
	
	
}
