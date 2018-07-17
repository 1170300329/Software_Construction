package factory;

import java.util.List;

import edge.Edge;
import edge.NetworkConnection;
import vertex.Vertex;

public class NetworkConnectionFactory extends EdgeFactory{

	@Override
	public Edge createEdge(String label, double weight, List<Vertex> vertices) {
		NetworkConnection networkConnection=new NetworkConnection(label, weight);
		networkConnection.addVertices(vertices);
		return networkConnection;
	}

}
