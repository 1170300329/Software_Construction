package helper;

import edge.DirectedEdge;
import edge.Edge;
import edge.UndirectedEdge;
import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import graph.ConcreteGraph;
import graph.GraphPoet;
import graph.SocialNetwork;
import vertex.Vertex;

public class ComputeBetweennessCentrality implements Strategy{

	@Override
	public double compute(ConcreteGraph g) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double compute(ConcreteGraph g, Vertex v) throws Exception {
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
		BetweennessCentrality<Vertex, Edge> b = new BetweennessCentrality<>(graph);
		if(g instanceof GraphPoet||g instanceof SocialNetwork) {
			return b.getVertexScore(v)/((g.vertices().size()-1)*(g.vertices().size()-2));
		}
		else return b.getVertexScore(v)/(((g.vertices().size()-1)*(g.vertices().size()-2))/2);
		
	}

	@Override
	public double compute(ConcreteGraph g, Vertex v1, Vertex v2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
