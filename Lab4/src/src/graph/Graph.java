package src.graph;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Graph<L,E> {
	public static <L,E> Graph<L, E> empty() throws Exception{
		@SuppressWarnings("unchecked")
		Graph<L,E> graph=(Graph<L, E>) new ConcreteGraph("");
		return graph;
	}

	public boolean addVertex(L v) throws Exception;
	
	public boolean removeVertex(L v) throws Exception;
	
	public Set<L> vertices() throws Exception;
	
	public Map<L, List<Double>> sources(L target) throws Exception;
	
	public Map<L, List<Double>> targets(L source) throws Exception;
	
	public boolean addEdge(E edge) throws Exception;
	
	public boolean removeEdge(E edge)  throws Exception;
	
	public Set<E> edges() throws Exception;
	
}
