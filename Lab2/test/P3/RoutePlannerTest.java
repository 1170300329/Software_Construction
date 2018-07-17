package P3;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class RoutePlannerTest {
	//Test strategy
	//test the class with two file
	//test the findstopsbysubstring() by using different strings,"AVE" was found in mytest_stop_times.txt 7 times>0
	//"AVER" was found in mytest_stop_times.txt 0 times=0
	//the same stop "A" in inputdata.txt more than one times,but it return 1
	//test computeRouteTest()
	//from "A" to "D",there will be several waitings,
	//from one route to another route
	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
	@Test
	public void findStopsBySubstringTest() throws IOException
	{
		RoutePlannerBuilder routePlannerBuilder=new RoutePlannerBuilder();
		List<Stop>list=routePlannerBuilder.build("src/P3/mytest_stop_times.txt", 1200).findStopsBySubstring("AVE");
		assertEquals(7, list.size());
		List<Stop>list1=routePlannerBuilder.build("src/P3/mytest_stop_times.txt", 1200).findStopsBySubstring("AVER");
		assertEquals(0, list1.size());	
		RoutePlannerBuilder routePlannerBuilder1=new RoutePlannerBuilder();
		List<Stop>list2=routePlannerBuilder1.build("src/P3/inputdata.txt", 1200).findStopsBySubstring("A");
		assertEquals(1, list2.size());	
	}
	@Test
	public void computeRouteTest() throws IOException
	{
		RoutePlannerBuilder routePlannerBuilder=new RoutePlannerBuilder();
		Stop src=new Stop("","A",1,0,0);
		Stop dest=new Stop("", "D", 4, 0, 0);
		int time=320;
		Itinerary ans=routePlannerBuilder.build("src/P3/inputdata.txt", 1200).computeRoute(src, dest, time);
		assertEquals(335,ans.getStartTime());
		assertEquals(445,ans.getEndTime());
		assertEquals(110, ans.getWaitTime());
		assertTrue(ans.getStartLocation().getName().equals(src.getName()));
		assertTrue(ans.getEndLocation().getName().equals(dest.getName()));
	}
}
