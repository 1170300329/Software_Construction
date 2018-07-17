package helper;

import edge.DirectedEdge;
import edge.Edge;
import edge.UndirectedEdge;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraDistance;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import graph.ConcreteGraph;
import vertex.Vertex;

public class ComputeDistance implements Strategy{

	@Override
	public double compute(ConcreteGraph g) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double compute(ConcreteGraph g, Vertex v) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double compute(ConcreteGraph g, Vertex v1, Vertex v2) throws Exception {
		SparseMultigraph<Vertex, Edge> graph = new SparseMultigraph<>();
		for (Vertex vm : g.vertices()) {
			graph.addVertex(vm);
		}
		for (Edge em : g.edges()) {
			if (em instanceof DirectedEdge) {
				graph.addEdge(em, em.getList().get(0), em.getList().get(1), EdgeType.DIRECTED);
			} else if (em instanceof UndirectedEdge) {
				graph.addEdge(em, em.getList().get(0), em.getList().get(1), EdgeType.UNDIRECTED);
			}
		}
		DijkstraDistance<Vertex, Edge>d=new DijkstraDistance<>(graph);
		if(d.getDistance(v1, v2)!=null)return (double) d.getDistance(v1, v2);
		else return -1;
	}

}
