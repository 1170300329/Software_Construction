package src.helper;

import src.edge.Edge;
import src.edge.HyperEdge;
import src.graph.ConcreteGraph;
import src.vertex.Vertex;

public class ComputeOutDegreeCentrality implements Strategy{

	@Override
	public double compute(ConcreteGraph g) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double compute(ConcreteGraph g, Vertex v) throws Exception {
		int ans=0;
		for(Edge e:g.edges()) {
			if(!(e instanceof HyperEdge)) {
			if(e.getList().get(1).getLabel().equals(v.getLabel())) {
				ans++;
				}
			}
		}
		return ans;
	
	}

	@Override
	public double compute(ConcreteGraph g, Vertex v1, Vertex v2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
