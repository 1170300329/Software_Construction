package src.IO;

import src.graph.ConcreteGraph;

public interface IO {
  public void writer(String filePath, ConcreteGraph g) throws Exception;

  public void reader(String filePath, ConcreteGraph g) throws Exception;
}
