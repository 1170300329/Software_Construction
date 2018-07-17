package debug.textProcesser;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Test;

public class textProcesserTest{

	@Test
	public void test() throws FileNotFoundException {
		SearchEngine google = new SearchEngine();
		google.processText("src/src/testText.txt");
		assertEquals(271,google.map.size());
		assertTrue(google.pageCounter==4);
	}

}
