package src.IO;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import src.factory.GraphPoetFactory;
import src.factory.MovieGraphFactory;
import src.factory.NetworkTopologyFactory;
import src.factory.SocialNetworkFactory;
import src.graph.ConcreteGraph;
import src.graph.GraphPoet;
import src.graph.MovieGraph;
import src.graph.NetworkTopology;
import src.graph.SocialNetwork;

public class NIO implements IO {

  @Override
  public void writer(String filePath, ConcreteGraph g) throws Exception {
    File file = new File(filePath);
    String string;
    FileOutputStream outputStream = new FileOutputStream(file);
    FileChannel channel = outputStream.getChannel();
    ByteBuffer buffer = ByteBuffer.allocate(1024);

    String name = (g.getGraphName() + "\n");
    buffer.put(name.getBytes());
    buffer.flip();
    channel.write(buffer);
    buffer.clear();
    int num = g.getVertex().size();
    for (int i = 0; i < num; i++) {
      string = g.getVertex().get(i).toString() + "\n";
      buffer.put(string.getBytes());
      buffer.flip();
      channel.write(buffer);
      buffer.clear();
    }
    int len = g.getEdge().size();
    for (int i = 0; i < len; i++) {
      if (i % 5000 == 0)
        System.out.println("IOi:" + i);
      string = g.getEdge().get(i).toString() + "\n";
      buffer.put(string.getBytes());
      buffer.flip();
      channel.write(buffer);
      buffer.clear();
    }
    channel.close();
    outputStream.close();
  }

  @Override
  public void reader(String filePath, ConcreteGraph g) throws Exception {
    Path path = Paths.get(filePath);
    List<String> list = Files.readAllLines(path);
    int len = list.size();
    if (g instanceof GraphPoet) {
      for (int i = 0; i < len; i++) {
        if (list.get(i).length() != 0)
          GraphPoetFactory.parse(list.get(i));
      }
    } else if (g instanceof SocialNetwork) {
      for (int i = 0; i < len; i++) {
        if (list.get(i).length() != 0)
          SocialNetworkFactory.parse(list.get(i));
      }
    } else if (g instanceof NetworkTopology) {
      for (int i = 0; i < len; i++) {
        if (list.get(i).length() != 0)
          NetworkTopologyFactory.parse(list.get(i));
      }
    } else if (g instanceof MovieGraph) {
      for (int i = 0; i < len; i++) {
        if (list.get(i).length() != 0)
          MovieGraphFactory.parse(list.get(i));
      }
    }
  }
}
