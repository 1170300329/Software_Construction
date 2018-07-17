package P3;

import java.util.ArrayList;

public class Person {
	private String name;
	private int dis=0;
	private int num;
	private int flag=0;
	protected ArrayList<Person> list=new ArrayList<Person>();
	/**
     * initial a class of person.
     * @param the name of a new class of person
     * 
     */
	public Person(String name) {
		this.name=name;
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
}
