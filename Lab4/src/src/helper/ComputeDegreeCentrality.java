package src.helper;

import src.edge.Edge;
import src.edge.HyperEdge;
import src.graph.ConcreteGraph;
import src.vertex.Vertex;

public class ComputeDegreeCentrality implements Strategy{

	@Override
	public double compute(ConcreteGraph g) throws Exception {
		double ans = 0;
		int VertexNum = g.vertices().size();
		double bottom = VertexNum * VertexNum - 3 * VertexNum + 2;
		double top = 0;
		double p = -1;
		for (Vertex v : g.vertices()) {
			if (compute(g, v) > p) {
				p = compute(g, v);
			}
		}
		for (Vertex v : g.vertices()) {
			top = top + (p - compute(g, v));
		}
		ans = top / bottom;
		return ans;
	}

	@Override
	public double compute(ConcreteGraph g, Vertex v) throws Exception {
		double ans = 0;
		for (Edge e : g.edges()) {
			if (!(e instanceof HyperEdge)) {
				if (e.getList().get(0).getLabel().equals(v.getLabel())) {
					ans++;
				} else if (e.getList().get(1).getLabel().equals(v.getLabel())) {
					ans++;
				}
			}
		}
		return ans;
	}

	@Override
	public double compute(ConcreteGraph g, Vertex v1, Vertex v2) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
