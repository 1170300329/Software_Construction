package src.IO;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import src.factory.GraphPoetFactory;
import src.factory.MovieGraphFactory;
import src.factory.NetworkTopologyFactory;
import src.factory.SocialNetworkFactory;
import src.graph.ConcreteGraph;
import src.graph.GraphPoet;
import src.graph.NetworkTopology;
import src.graph.SocialNetwork;

public class StreamIO implements IO {

  @Override
  public void writer(String filePath, ConcreteGraph g) throws Exception {
    File file = new File(filePath);
    FileOutputStream fileOutputStream = new FileOutputStream(file);
    fileOutputStream.write((g.getGraphName() + "\n").getBytes());
    int num = g.getVertex().size();
    for (int i = 0; i < num; i++) {
      fileOutputStream.write((g.getVertex().get(i).toString() + "\n").getBytes());
    }
    int len = g.getEdge().size();
    for (int i = 0; i < len; i++) {
      if (i % 5000 == 0)
        System.out.println("STRi:" + i);
      fileOutputStream.write((g.getEdge().get(i).toString() + "\n").getBytes());
    }
    fileOutputStream.close();
  }

  @SuppressWarnings("resource")
  @Override
  public void reader(String filePath, ConcreteGraph g) throws Exception {
    BufferedReader bReader = null;
    FileInputStream inputStream = new FileInputStream(filePath);
    bReader = new BufferedReader(new InputStreamReader(inputStream));
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

  public static void main(String[] args) throws Exception {
    StreamIO io = new StreamIO();
    io.reader("src/src/file4.txt", null);
  }
}
