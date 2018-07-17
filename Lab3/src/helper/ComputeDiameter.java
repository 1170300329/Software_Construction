package helper;

import graph.ConcreteGraph;
import vertex.Vertex;

public class ComputeDiameter implements Strategy{

	@Override
	public double compute(ConcreteGraph g) throws Exception {
		double ans=Double.MIN_VALUE;
		ComptuteEccentricity c=new ComptuteEccentricity();
		for(Vertex v:g.vertices()) {
			if(ans<c.compute(g,v)) {
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
