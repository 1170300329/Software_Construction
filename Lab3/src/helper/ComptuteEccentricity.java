package helper;

import graph.ConcreteGraph;
import vertex.Vertex;

public class ComptuteEccentricity implements Strategy{

	@Override
	public double compute(ConcreteGraph g) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double compute(ConcreteGraph g, Vertex v) throws Exception {
		double ans=0;
		ComputeDistance c=new ComputeDistance();
		for(Vertex ver:g.vertices()) {
			if(!ver.getLabel().equals(v.getLabel())) {
				if(c.compute(g, ver, v)>ans) {
					ans=c.compute(g, ver, v);
				}
				if(c.compute(g, v, ver)>ans) {
					ans=c.compute(g, v, ver);
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
