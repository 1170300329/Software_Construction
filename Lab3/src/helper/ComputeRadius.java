package helper;

import graph.ConcreteGraph;
import vertex.Vertex;

public class ComputeRadius implements Strategy{

	@Override
	public double compute(ConcreteGraph g) throws Exception {
		ComptuteEccentricity c=new ComptuteEccentricity();
		double ans=Double.MAX_VALUE;
		for(Vertex v:g.vertices()) {
			if(ans>c.compute(g,v)) {
				ans=c.compute(g,v);
			}
		}
		return ans;
	}

	@Override
	public double compute(ConcreteGraph g, Vertex v) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double compute(ConcreteGraph g, Vertex v1, Vertex v2) {
		// TODO Auto-generated method stub
		return 0;
	}

}
