package P2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;




public class Person {

	private String vertexname;
	private int dis=0;
	private int num;
	private int flag=0;
	private HashMap<Person, Integer>map=new HashMap<>();
	private ArrayList<Person> list=new ArrayList<Person>();
	// Abstraction function:
    //  num is the id for each person,vertexname is the name for each person,the flag represents the person
	// is visited or not,map represents the persons related to the person and the list is the person related to the person
    //dis represents the least distance between the source and current person. 
	// Representation invariant:
    //  there are no repeated element in list,flag=1 or 0,vertexname!=null
    // Safety from rep exposure:
    //  all the fields are private,getMap() and getList() have defensive copies.
	public void checkRep() {
		assert flag==1|flag==0;
		assert vertexname!=null;
		Set<Person> set=new HashSet<>();
		for(int i=0;i<list.size();i++) {
			set.add(list.get(i));
		}
		assert set.size()==list.size();
	}
    public Person(String vertex) {
    	this.vertexname=vertex;
    }
    public String getVertexname() {
    	return vertexname;
    }
    public Map<Person, Integer>getMap()
    {
    	return new HashMap<>(map);
    }
    public ArrayList<Person> getList()
    {
    	return new ArrayList<>(list);
    }
    /**
     * set the distance between the person and the first person
     * the distance will be initialed to 0
     * @param distance between the person and the first person
     * 
     */
	public void setDis(int dis) {
		this.dis=dis;
	}
	/**
     * get the distance between the person and the first person
     * 
     * @return distance between the person and the first person
     * 
     */
	public int getDis() {
		return this.dis;
	}
	/**
     * set the flag of the person
     * if the person is visited,it will be 1,else it will be 0.
     * @param whether the person was visited
     * 
     */
	public void setFlag(int tf) {
		this.flag=tf;
	}
	/**
     * get the flag of the person
     * if the person is visited,it will be 1,else it will be 0.
     * @return whether the person was visited
     * 
     */
	public int getFlag() {
		return flag;
	}
	/**
     * set the number of the person
     * @param number of the person
     */
	public void setNum(int num) {
		this.num=num;
	}
	/**
     * get the number of the person
     * @return number of the person
     */
	public int getNum() {
		return this.num;
	}
	@Override
	public String toString() {
		return vertexname;
	}
	@Override 
	public boolean equals(Object obj) {
		if(obj instanceof Person&&((Person) obj).vertexname.equals(vertexname)) {
			return true;
		}
		return false;
	}
	public void put(Person name2, int i) {
		map.put(name2, i);
		
	}
	public void listAdd(Person p) {
		list.add(p);
	}
}