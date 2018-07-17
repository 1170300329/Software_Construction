package P3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class FriendshipGraph {
	private int vertexCount = 0;
	private ArrayList<Person> Vertex = new ArrayList<Person>();

	/**
	 * add the person to the List of person as a vertex
	 * @param an instance of the class of person
	 */
	public void addVertex(Person name) {
		for (int i = 0; i < Vertex.size(); i++) {
			if (name.equals(Vertex.get(i))) {
				System.out.println("sorry,this person existed");
				System.exit(0);
			}
		}
		Vertex.add(name);
		vertexCount++;
		name.setNum(vertexCount - 1);
	}
	/**
	 * add the connection between two person
	 * @param two instances of the class of person
	 * @return the ArrayList of the person connected to the first person
	 */
	public ArrayList<Person> addEdge(Person name1, Person name2) {
		name1.list.add(name2);
		return name1.list;
	}
	/**
	 * get the length of the shortest path between name1 and name2 
	 * @param name1 the first person
	 * @param name2 the second person
	 * @return the length of the shortest path between name1 and name2 
	 */
	public int getDistance(Person name1, Person name2) {
		for (int k = 0; k < Vertex.size(); k++) {
			Vertex.get(k).setDis(0);
			Vertex.get(k).setFlag(0);
		}
		if (name1.equals(name2)) {
			return 0;
		}
		Queue<Person> queue = new LinkedList<Person>();

		int i, j;
		name1.setFlag(1);
		queue.offer(name1);
		while (!queue.isEmpty()) {
			i = queue.element().getNum();
			queue.poll();
			for (j = 0; j < Vertex.size(); j++) {

				if (Vertex.get(i).list.contains(Vertex.get(j)) && Vertex.get(j).getFlag() == 0) {
					Vertex.get(j).setFlag(1);
					queue.offer(Vertex.get(j));
					Vertex.get(j).setDis(Vertex.get(i).getDis() + 1);
					if (Vertex.get(j).equals(name2)) {
						return Vertex.get(j).getDis();
					}
				}
			}

		}
		return -1;
	}

	public static void main(String[] args) {
		FriendshipGraph graph = new FriendshipGraph();
		 Person rachel = new Person("Rachel");
		 Person ross = new Person("Ross");
		 Person ben = new Person("Ben");
		 Person kramer = new Person("Kramer");
		 graph.addVertex(rachel);
		 graph.addVertex(ross);
		 graph.addVertex(ben);
		 
		 graph.addVertex(kramer);
		 graph.addEdge(rachel, ross);
		 graph.addEdge(ross, rachel);
		 graph.addEdge(ross, ben);
		 graph.addEdge(ben, ross);
		
		 System.out.println(graph.getDistance(rachel,ross));
		 //should print 1
		 System.out.println(graph.getDistance(rachel,ben));
		 //should print 2
		 System.out.println(graph.getDistance(rachel,rachel));
		 //should print 0
		 System.out.println(graph.getDistance(rachel,kramer));
		 //should print -1
	}

}
