package src.factory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import src.edge.Edge;
import src.graph.Graph;
import src.vertex.Vertex;

public class GraphFactory {
  public static Graph<Vertex, Edge> createGraph(String filePath, int kind) throws Exception {
    Graph<Vertex, Edge> graph = null;
    File f = new File(filePath);
    FileReader reader = new FileReader(f);
    String s = "";
    BufferedReader bReader = new BufferedReader(reader);
    s = bReader.readLine();
    bReader.close();
    String pattern = "GraphType\\=\\“(.+)\\”";
    Pattern r = Pattern.compile(pattern);
    Matcher m = r.matcher(s);
    if (m.find()) {
      System.out.println(m.group(1));
      if (m.group(1).equals("MovieGraph")) {
        graph = MovieGraphFactory.createGraph(filePath, kind);
      } else if (m.group(1).equals("GraphPoet")) {
        graph = GraphPoetFactory.createGraph(filePath, kind);
      } else if (m.group(1).equals("NetworkTopology")) {
        graph = NetworkTopologyFactory.createGraph(filePath, kind);
      } else if (m.group(1).equals("SocialNetwork")) {
        graph = SocialNetworkFactory.createGraph(filePath, kind);
      }
    }
    return graph;
  }
}
