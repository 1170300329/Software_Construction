package debug.geometryProcessor;

import static org.junit.Assert.*;

import org.junit.Test;

public class CircleTest {

	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
	@Test
	public void testgetArea() {
		Circle circle=new Circle(2, "red", "circle1");
		assertTrue(Math.PI*2*2==circle.getArea());
	}
	@Test
	public void testgetColor() {
		Circle circle=new Circle(2, "red", "circle1");
		assertEquals("red", circle.getColour());
	}
}
