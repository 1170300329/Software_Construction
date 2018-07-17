package helper;

import graph.ConcreteGraph;
import vertex.Vertex;

public class Context {
	private Strategy strategy;
    public Context(Strategy strategy){
	      this.strategy = strategy;
    }
    public double executeStrategy(ConcreteGraph g) throws Exception{
        return strategy.compute(g);
    }
    public double executeStrategy(ConcreteGraph g,Vertex v) throws Exception{
        return strategy.compute(g,v);
    }
    public double executeStrategy(ConcreteGraph g,Vertex v1,Vertex v2) throws Exception{
        return strategy.compute(g,v1,v2);
    }
}
