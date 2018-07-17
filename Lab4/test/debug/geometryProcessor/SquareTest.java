package debug.geometryProcessor;

import static org.junit.Assert.*;

import org.junit.Test;

public class SquareTest {

	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
	@Test
	public void testgetArea() {
		Square square=new Square(4, "red", "square1");
		assertTrue(16==square.getArea());
	}

}
