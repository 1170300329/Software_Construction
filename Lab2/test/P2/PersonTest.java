package P2;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {
    // Testing strategy
	//test the class with a new person.
	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
	@Test
	public void getVertexnameTest() {
		Person person=new Person("A");
		assertEquals("A", person.getVertexname());
	}
	@Test
	public void setDisTest() {
		Person person=new Person("A");
		person.setDis(6);
		assertEquals(6, person.getDis());
	}
	@Test
	public void getDisTest() {
		Person person=new Person("A");
		person.setDis(6);
		assertEquals(6, person.getDis());
	}
	@Test
	public void setFlagTest() {
		Person person=new Person("A");
		person.setFlag(1);
		assertEquals(1, person.getFlag());
	}
	@Test
	public void getFlagTest() {
		Person person=new Person("A");
		person.setFlag(1);
		assertEquals(1, person.getFlag());
	}
	@Test
	public void setNumTest() {
		Person person=new Person("A");
		person.setNum(3);;
		assertEquals(3, person.getNum());
	}
	@Test
	public void getNumTest() {
		Person person=new Person("A");
		person.setNum(3);;
		assertEquals(3, person.getNum());
	}
	@Test
	public void toStringTest() {
		Person person=new Person("A");
		assertEquals("A", person.toString());
	}
	@Test
	public void equalsTest() {
		Person person=new Person("A");
		Person person1=new Person("A");
		assertTrue(person.equals(person1));
	}
	@Test
	public void putTest() {
		Person person=new Person("A");
		Person person1=new Person("B");
		person.put(person1, 1);
		Integer aInteger=new Integer(1);
		assertEquals(aInteger, person.getMap().get(person1));

	}
	@Test
	public void listAddTest() {
		Person person=new Person("A");
		Person person1=new Person("B");
		person.listAdd(person1);
		assertTrue(person.getList().contains(person1));
	}
	@Test
	public void getMapTest() {
		Person person=new Person("A");
		Person person1=new Person("B");
		person.put(person1, 1);
		Integer aInteger=new Integer(1);
		assertEquals(aInteger, person.getMap().get(person1));
	}
	@Test
	public void getListTest() {
		Person person=new Person("A");
		Person person1=new Person("B");
		person.listAdd(person1);
		assertTrue(person.getList().contains(person1));
	}
}
