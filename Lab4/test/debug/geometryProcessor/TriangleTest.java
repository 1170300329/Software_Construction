package debug.geometryProcessor;

import static org.junit.Assert.*;

import org.junit.Test;

public class TriangleTest {

	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
	@Test
	public void testgetArea() {
		Triangle triangle=new Triangle(2, "red", "triangle");
		assertTrue( Math.abs( (Math.sqrt(3) / 4)*4 - triangle.getArea()) < .0000001);
	} 

}
