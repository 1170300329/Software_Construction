package src.IO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import src.factory.GraphPoetFactory;
import src.factory.MovieGraphFactory;
import src.factory.NetworkTopologyFactory;
import src.factory.SocialNetworkFactory;
import src.graph.ConcreteGraph;
import src.graph.GraphPoet;
import src.graph.NetworkTopology;
import src.graph.SocialNetwork;

public class WRerIO implements IO {
  @Override
  public void writer(String filePath, ConcreteGraph g) throws Exception {
    File file = new File(filePath);
    FileWriter writer1 = new FileWriter(file);
    BufferedWriter bwriter = new BufferedWriter(writer1);
    bwriter.write(g.getGraphName() + "\n");
    int num = g.getVertex().size();
    for (int i = 0; i < num; i++) {
      bwriter.write((g.getVertex().get(i).toString() + "\n"));
    }
    int len = g.getEdge().size();
    for (int i = 0; i < len; i++) {
      if (i % 5000 == 0)
        System.out.println("BUFi:" + i);
      bwriter.write((g.getEdge().get(i).toString() + "\n"));
    }
    bwriter.close();
    writer1.close();
  }

  @SuppressWarnings("resource")
  @Override
  public void reader(String filePath, ConcreteGraph g) throws Exception {
    BufferedReader bReader = null;
    File f = new File(filePath);
    FileReader reader = new FileReader(f);
    bReader = new BufferedReader(reader);
    String s = "";
    if (g instanceof GraphPoet) {
      while ((s = bReader.readLine()) != null) {
        GraphPoetFactory.parse(s);
      }
    } else if (g instanceof SocialNetwork) {
      while ((s = bReader.readLine()) != null) {
        SocialNetworkFactory.parse(s);
      }
    } else if (g instanceof NetworkTopology) {
      while ((s = bReader.readLine()) != null) {
        NetworkTopologyFactory.parse(s);
      }
    } else {
      while ((s = bReader.readLine()) != null) {
        MovieGraphFactory.parse(s);
      }
    }
  }

}
