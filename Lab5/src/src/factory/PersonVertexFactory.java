package src.factory;

import src.vertex.Person;
import src.vertex.Vertex;

public class PersonVertexFactory extends VertexFactory {

  @Override
  public Vertex createVertex(String label, String[] args) throws Exception {
    Person person = new Person(label);
    try {
      person.fillVertexInfo(args);
    } catch (Exception e) {
      throw e;
    }
    return person;
  }

}
