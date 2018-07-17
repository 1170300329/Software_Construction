package P2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import P1.graph.ConcreteVerticesGraph;
import P1.graph.Graph;
import P2.Person;

public class FriendshipGraph {
    protected Graph<Person> graph = new ConcreteVerticesGraph<>();
    private  int vertexCount=0;
    // Abstraction function:
    //  graph represents the relationship among persons.and the vertexCount is the number for every person.
    // Representation invariant:
    //  the person in graph shouldn'd be repeated.
    // graph.vertices().size()==vertexCount
    // Safety from rep exposure:
    //  all the fields are private.
    public void chekRep() {
    	assert graph.vertices().size()==vertexCount;
    }
    /**
	 * add the person to the List of person as a vertex
	 * @param an instance of the class of person
	 */
	public void addVertex(Person name) {
		boolean flag=graph.add(name);
		
		if(flag==true) {
			vertexCount++;
			name.setNum(vertexCount - 1);
			chekRep();
			return;
		}
		else {
			System.out.println("false");
			System.exit(0);
		}
	}
	/**
	 * add the connection between two person
	 * @param two instances of the class of person
	 * @return the ArrayList of the person connected to the first person
	 */
	public ArrayList<Person> addEdge(Person name1, Person name2){
		if(!graph.vertices().contains(name1))addVertex(name1);
		if(!graph.vertices().contains(name2))addVertex(name2);
		graph.set(name1, name2, 1);
		name1.put(name2, 1);
		for(Person p:name1.getMap().keySet()) {
			name1.listAdd(p);
		}
		chekRep();
		return name1.getList();
	}
	/**
	 * get the length of the shortest path between name1 and name2 
	 * @param name1 the first person
	 * @param name2 the second person
	 * @return the length of the shortest path between name1 and name2 
	 */
	public int getDistance(Person name1, Person name2) {
		
		Iterator<Person> iterator=graph.vertices().iterator();
		List<Person> vertices=new ArrayList<>();
		Person temp=new Person(" ");
		for(int i=0;i<graph.vertices().size();i++) {
			vertices.add(temp);
		}
		while(iterator.hasNext()) {
			Person temp1=iterator.next();
			vertices.set(temp1.getNum(), temp1);
		}
		for (int k = 0; k <vertices.size(); k++) {
			vertices.get(k).setDis(0);
			vertices.get(k).setFlag(0);
		}
		if (name1.getVertexname().equals(name2.getVertexname())) {
			chekRep();
			return 0;
		}
		Queue<Person> queue = new LinkedList<Person>();

		int i, j;
		name1.setFlag(1);
		queue.offer(name1);
		while (!queue.isEmpty()) {
			i = queue.element().getNum();
			queue.poll();
			for (j = 0; j < vertices.size(); j++) {

				if (vertices.get(i).getList().contains(vertices.get(j)) && vertices.get(j).getFlag() == 0) {
					vertices.get(j).setFlag(1);
					queue.offer(vertices.get(j));
					vertices.get(j).setDis(vertices.get(i).getDis() + 1);
					if (vertices.get(j).equals(name2)) {
						chekRep();
						return vertices.get(j).getDis();
					}
				}
			}
		}
		chekRep();
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
