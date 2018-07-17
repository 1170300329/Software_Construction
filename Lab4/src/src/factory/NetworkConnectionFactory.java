package src.factory;

import java.util.List;

import src.edge.Edge;
import src.edge.NetworkConnection;
import src.vertex.Vertex;

public class NetworkConnectionFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, double weight, List<Vertex> vertices) throws Exception {
		NetworkConnection networkConnection=new NetworkConnection(label, weight);
		networkConnection.addVertices(vertices);
		return networkConnection;
	}

}
