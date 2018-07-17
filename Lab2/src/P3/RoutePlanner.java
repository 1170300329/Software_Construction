package P3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class RoutePlanner {
	private StopGraph RouteGraph=new StopGraph();
	private Map<Stop, List<Stop>>map;
	private int maxWaitTime;
	//Abstraction function:
	//RouteGraph represents the graph of the route of bus.map represents the stops in the same place
	//Representation invariant:
	//map!=null,RouteGraph !=null.
	//Safety from rep exposure:
	//all the fields are private
	
	public RoutePlanner(StopGraph routeGraph2, Map<Stop, List<Stop>> map, int maxWaitTime) {
		this.RouteGraph=routeGraph2;
		this.map=map;
		this.setMaxWaitTime(maxWaitTime);
	}
	/**
	 * 
	 * @param list is a list of stops 
	 * @param a stop which needs to be determined in the list or not
	 * @return if the list has stops with the same position,return true,else return false 
	 */
	public boolean isContaned(List<Stop>list,Stop stop) {
		for(Stop s:list) {
			if(s.similarto(stop)) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @param search is a string which needs to be searched 
	 * @return a list of stop which contains the search.
	 */
	public List<Stop> findStopsBySubstring(String search) {
		ArrayList<Stop> list=new ArrayList<>();
		for(Stop s:RouteGraph.vertices()) {
			if(s.getName().contains(search)&&!isContaned(list, s)){
				//System.out.println(s);
				list.add(s);
			}
		}
		return list;
	}
	/**
	 * 
	 * @param src is the stop where the client comes from
	 * @param dest is the stop where the client want to come to
	 * @param time is the time when the client start
	 * @return an Itinerary which includes the information of the shortest route
	 */
	public Itinerary computeRoute(Stop src,Stop dest,int time) {
		src.setId(-1);
		dest.setId(-1);
		Itinerary ans=new Itinerary();
		src.setTime(time);
		for(Stop stop :RouteGraph.vertices()) {
			if(time>stop.getTime()) {
				RouteGraph.remove(stop);
				
//				if(stop.getIsDivided()==true) {
//					RouteGraph.remove(stop.getSon1());
//					RouteGraph.remove(stop.getSon2());
//				}
			}
		}
		//System.out.println(map.keySet());
		//System.out.println(src);
		for(Stop s:map.keySet()) {
			
			//System.out.println(src);
			if(s.similarto(src)) {
				//System.out.println(map.get(s).size());
				if(map.get(s).size()>1) {
				for(Stop t:map.get(s)) {
					if(t.getTime()-src.getTime()<=1200)RouteGraph.set(src, t.getSon1(), t.getTime()-src.getTime());
				}}else {
					if(s.getTime()-src.getTime()<=1200)RouteGraph.set(src, s, s.getTime()-src.getTime());
				}
				break;
			}
		}
		for(Stop s:map.keySet()) {
			if(s.similarto(dest)) {
				if(map.get(s).size()>1) {
				for(Stop t:map.get(s)) {
					RouteGraph.set(t.getSon2(), dest, 0);
				}}else {
					RouteGraph.set(s, dest, 0);
				}
				break;
			}
		}
    	for(Stop stop1:map.keySet()) {
//    		if(map.get(stop1).size()>1) {
//    			System.out.println("#"+stop1);
//    			RouteGraph.remove(stop1);
    		if(map.get(stop1).size()>1) {
    		for(int i=0;i<map.get(stop1).size();i++) {
    			//System.out.println("#"+map.get(stop1).get(i));
    			RouteGraph.remove(map.get(stop1).get(i));
    		}}
    		
    	}
		Stop temp=src;
		temp.setDist(0);
		temp.setVisited(1);
		Set<Stop>visited=new HashSet<Stop>();
		Set<Stop>vertices=new HashSet<Stop>(RouteGraph.vertices());
		visited.add(temp);
		vertices.remove(temp);
		while(!temp.equals(dest)) {
			//System.out.println(temp);
			//if(RouteGraph.targets(temp)!=null) {
			//System.out.println(visited.size());
			for(Stop q:visited) {
				//if(RouteGraph.targets(q).size()==0)System.out.println(q);
			for(Stop p:RouteGraph.targets(q).keySet()){
				if(p.getVisited()==0) {
					//System.out.println("#"+p);
				if(q.getDist()+RouteGraph.targets(q).get(p)<p.getDist()) {
					p.setDist(q.getDist()+RouteGraph.targets(q).get(p));
					p.setParent(q);
					}
				}
			}}
			Stop temp1=new Stop("","",0, 0, 0);
			for(Stop r:visited) {
			for(Stop p:RouteGraph.targets(r).keySet()) {
				//System.out.println(p+"  "+p.getId()+" "+p.getDist());
				if(p.getDist()<temp1.getDist()&&p.getVisited()==0) {
					//System.out.println(p+" "+p.getId());
					temp1=p;
				}
			}
			}
			//System.out.println(temp1);
			temp1.setVisited(1);
			visited.add(temp1);
			vertices.remove(temp1);
			temp=temp1;
			//System.out.println(RouteGraph.targets(temp).size());
			//}
		}
		//System.out.println(temp.equals(dest));
		Stop temp2=dest;
		Stack<Stop> list=new Stack<>();
		while(temp2!=null&&!temp2.equals(src)) {
			list.push(temp2);
			temp2=temp2.getParent();
		}
//		list.push(src);
		//System.out.println(list.size());
		int x=0;
		Stop prev=src;
		ans.setStartLocation(src);
		ans.setEndLocation(dest);
		ans.setInstructions(ans.getInstructions()+"src:"+src.getName()+" "+src.getLatitude()+" "+src.getLongitude()+"\n");
		//ans.setInstructions(ans.getInstructions()+"Wait Time:"+(list.peek().getTime()-time)+" Begin Time:"+list.peek().getTime()+"\n");
		while(list.size()!=1) {
			if(x==0) {ans.setStartTime(list.peek().getTime());x=1;}
			String string=ans.getInstructions();
			if(prev.similarto(list.peek())) {
				int waittime=list.peek().getTime()-prev.getTime();
				if(waittime!=0) {
				string=string+"Wait:"+waittime+" in "+prev.getName()+" for:"+list.peek()+"\n";
				ans.setInstructions(string);
				}
				prev=list.pop();
			}else {
			prev=list.peek();
			string=string+"Arter several minutes: "+list.pop().toString()+"\n";
			ans.setInstructions(string);
			}
		}
		ans.setEndTime(prev.getTime());
		ans.setInstructions(ans.getInstructions()+"dest: "+dest.getName()+" "+"Arrive Time:"+prev.getTime()+"\n");
		return ans;
	}
	
	public int getMaxWaitTime() {
		return maxWaitTime;
	}

	public void setMaxWaitTime(int maxWaitTime) {
		this.maxWaitTime = maxWaitTime;
	}
}
