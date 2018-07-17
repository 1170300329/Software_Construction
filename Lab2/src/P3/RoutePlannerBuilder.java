package P3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RoutePlannerBuilder {
	private StopGraph RouteGraph=new StopGraph();
	private Map<Stop, List<Stop>>map=new HashMap<>();//存相同地理位置的站点
	//abstract function:
	//routegraph is the graph of the bus route,each entry 
	//map represents all the stops in the same position 
	//represent invariant:
	//routegraph!=null,map!=null
	//Safety from rep exposure:
	//all the fields are private
	/**
	 * 
	 * @param list is a list of stops 
	 * @param a stop which needs to be determined in the list or not
	 * @return if the list has stops with the same position,return true,else return false 
	 */
	public boolean isContaned(Set<Stop>set,Stop stop) {
		for(Stop s:set) {
			if(s.similarto(stop)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @param filename is the filename where we read data from
	 * @param maxWaitLimit is the max time the client will wait for in each stop
	 * @return a instance of routeplanner
	 * @throws IOException
	 */
	public RoutePlanner build(String filename,int maxWaitLimit) throws IOException {
		File file=new File(filename);
		String s=null;
		FileReader reader = new FileReader(file);
        BufferedReader bReader = new BufferedReader(reader);
        while ((s=bReader.readLine()) != null) {
        	String []temp=s.split(",");
        	int num=Integer.valueOf(temp[1]);
        	ArrayList<Stop>list2=new ArrayList<>();
        	for(int i=0;i<num;i++) {
        		s=bReader.readLine();
        		String []temp1=s.split(",");
        		Stop stop=new Stop(temp[0],temp1[0], Double.valueOf(temp1[1]),Double.valueOf(temp1[2]), Integer.valueOf(temp1[3]));
        		list2.add(stop);
        		if(!isContaned(map.keySet(), stop)) {
        			ArrayList<Stop>list3=new ArrayList<>();
        			list3.add(stop);
        			map.put(stop, list3);
        		}else {
        			//\map.get(stop).add(stop);
        			for(Stop s1:map.keySet()) {
        				if(s1.similarto(stop)) {
        					map.get(s1).add(stop);
        				}
        			}
        		}
        	}
        
        	for(int i=0;i<list2.size()-1;i++) {
        		RouteGraph.set(list2.get(i),list2.get(i+1), list2.get(i+1).getTime()-list2.get(i).getTime());
        	}
        }
        bReader.close();
       
        for(Stop stop:map.keySet()) {
        	if(map.get(stop).size()>1) {
        	for(int i=0;i<map.get(stop).size();i++) {
        				if(!map.get(stop).get(i).getIsDivided()) {
        					Stop stop1=new Stop(map.get(stop).get(i));
            				Stop stop2=new Stop(map.get(stop).get(i));
        					stop1.setId(1);
        					stop2.setId(2);
        					Stop src=RouteGraph.previous(map.get(stop).get(i));
        					//System.out.println("src"+src);
        					Stop tar=RouteGraph.next(map.get(stop).get(i));
        					//System.out.println("dest"+tar);
        					RouteGraph.set(stop1,stop2,0);
        					if(src!=null)RouteGraph.set(src,stop1, RouteGraph.sources(map.get(stop).get(i)).get(src));
        					if(tar!=null)RouteGraph.set(stop2,tar, RouteGraph.targets(map.get(stop).get(i)).get(tar));
        					map.get(stop).get(i).setSon1(stop1);
        					map.get(stop).get(i).setSon2(stop2);
        					map.get(stop).get(i).setIsDivided(true);
        				}
        }
        	}
        }
        for(Stop stop1:map.keySet()) {
        	for(int i=0;i<map.get(stop1).size()-1;i++) {
        		for(int j=i+1;j<map.get(stop1).size();j++) {
        			int x=map.get(stop1).get(i).getTime()-map.get(stop1).get(j).getTime();
        			int y=map.get(stop1).get(j).getTime()-map.get(stop1).get(i).getTime();
        			if(x<=maxWaitLimit&&x>=0) {
        				RouteGraph.set(map.get(stop1).get(j).getSon1(), map.get(stop1).get(i).getSon2(), x);
        			}
        			if(y<=maxWaitLimit&&y>=0) {
        				RouteGraph.set(map.get(stop1).get(i).getSon1(), map.get(stop1).get(j).getSon2(), y);        			}
        		}
        	}
        }
		return new RoutePlanner(RouteGraph,map,maxWaitLimit);
	}
}
