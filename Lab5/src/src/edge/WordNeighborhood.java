package src.edge;

public class WordNeighborhood extends DirectedEdge {
  private static final long serialVersionUID = 1L;

  /**
   * new a wordneighborhood with a label and weight
   * 
   * @param label
   * @param weight
   */
  public WordNeighborhood(String label, double weight) {
    super(label, weight);
  }
}
