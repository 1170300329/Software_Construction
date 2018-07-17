package helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edge.CommentTie;
import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import edge.HyperEdge;
import graph.ConcreteGraph;
import graph.GraphPoet;
import graph.MovieGraph;
import graph.NetworkTopology;
import graph.SocialNetwork;
import vertex.Person;
import vertex.Vertex;

public class GraphMetrics2 {
	private static Map<Integer, Vertex>map;//由编号得到名字
	private static Map<Vertex, Integer>map1;//由名字得到编号
	public static double minPath(ConcreteGraph g,Vertex v1,Vertex v2) throws Exception {
		final int INF=Integer.MAX_VALUE;
		double ans=0;
		if(g instanceof GraphPoet) {
			map=new HashMap<>();//由编号得到名字
			map1=new HashMap<>();//由名字得到编号
			int size=g.vertices().size();
			double[][] array=new double[size][size];
			int cnt=0;
			for(Vertex v:g.vertices()) {
				map.put(cnt, v);
				map1.put(v, cnt);
				cnt++;
			}
			for(int i=0;i<array.length;i++) {
				for(int j=0;j<array.length;j++) {
					array[i][j]=INF;
				}
			}
			for(Edge e:g.edges()) {
				array[map1.get(e.getList().get(0))][map1.get(e.getList().get(1))]=1;
			}
			ans=FloydInGraph.findAllPath(array, map1.get(v1), map1.get(v2));
			if(array[map1.get(v1)][map1.get(v2)]==INF&&FloydInGraph.getPath().get(0).size()==2) {
				return -1;
			}
		}else if (g instanceof NetworkTopology) {
			map=new HashMap<>();//由编号得到名字
			map1=new HashMap<>();//由名字得到编号
			int size=g.vertices().size();
			double[][] array=new double[size][size];
			int cnt=0;
			for(Vertex v:g.vertices()) {
				map.put(cnt, v);
				map1.put(v, cnt);
				cnt++;
			}
			for(int i=0;i<array.length;i++) {
				for(int j=0;j<array.length;j++) {
					array[i][j]=INF;
				}
			}
			for(Edge e:g.edges()) {
				array[map1.get(e.getList().get(0))][map1.get(e.getList().get(1))]=1;
				array[map1.get(e.getList().get(1))][map1.get(e.getList().get(0))]=1;
			}
			ans=FloydInGraph.findAllPath(array, map1.get(v1), map1.get(v2));
			if(array[map1.get(v1)][map1.get(v2)]==INF&&FloydInGraph.getPath().get(0).size()==2) {
				return -1;
			}
		}else if(g instanceof SocialNetwork) {
			List<Vertex> vertexs=new ArrayList<>(g.getVertex());
			List<Vertex> vertexsused=new ArrayList<>(g.getVertex());
			List<Edge> edges=new ArrayList<>(g.getEdge());
			List<Edge> edgesused=new ArrayList<>(g.getEdge());
			for(int i=0;i<vertexs.size();i++) {
				for(int j=0;j<vertexs.size();j++) {
					List<Edge>list=new ArrayList<>();
					for(int k=0;k<edges.size();k++) {
						if(edges.get(k).getList().get(0).getLabel().equals(vertexs.get(i).getLabel())&&
						edges.get(k).getList().get(1).getLabel().equals(vertexs.get(j).getLabel())) {
							list.add(edges.get(k));
						}
					}
					//System.out.println(list.size());
					if(list.size()==2) {
						if(list.get(0).getWeight()!=list.get(1).getWeight()) {
							//System.out.println("dsfgdsg");
							list.remove(1);
						}
					}else if(list.size()==3&&list.get(0).getWeight()==list.get(1).getWeight()&&list.get(0).getWeight()!=list.get(2).getWeight()) {
						list.remove(2);
					}else if(list.size()==3&&list.get(0).getWeight()==list.get(2).getWeight()&&list.get(0).getWeight()!=list.get(1).getWeight()) {
						list.remove(1);
					}else if(list.size()==3&&list.get(1).getWeight()==list.get(2).getWeight()&&list.get(0).getWeight()!=list.get(1).getWeight()) {
						list.remove(0);
					}else if(list.size()==3&&list.get(0).getWeight()!=list.get(1).getWeight()&&list.get(2).getWeight()!=list.get(1).getWeight()
							&&list.get(2).getWeight()!=list.get(0).getWeight()) {
						list.remove(2);
						list.remove(1);
					}
					//System.out.println(list.size());
					if(list.size()==2) {
						Vertex temp=new Person(vertexs.get(i).getLabel()+" "+vertexs.get(j).getLabel());
						Edge edge1=null;
						Edge edge2=null;
						if(list.get(0) instanceof FriendTie) {
							edge1=new FriendTie(list.get(0)+"1", 0.5*list.get(0).getWeight());
							edge2=new FriendTie(list.get(0)+"2", 0.5*list.get(0).getWeight());
						}else if(list.get(0) instanceof ForwardTie) {
							edge1=new ForwardTie(list.get(0)+"1", 0.5*list.get(0).getWeight());
							edge2=new ForwardTie(list.get(0)+"2", 0.5*list.get(0).getWeight());
						}else if(list.get(0) instanceof CommentTie) {
							edge1=new CommentTie(list.get(0)+"1", 0.5*list.get(0).getWeight());
							edge2=new CommentTie(list.get(0)+"2", 0.5*list.get(0).getWeight());
						}
						ArrayList<Vertex> list1=new ArrayList<>();
						list1.add(list.get(0).getList().get(0));
						list1.add(temp);
						edge1.addVertices(list1);
						
						ArrayList<Vertex> list2=new ArrayList<>();
						list2.add(temp);
						list2.add(list.get(0).getList().get(1));
						edge2.addVertices(list2);
						
						vertexsused.add(temp);
						edgesused.remove(list.get(0));
						edgesused.add(edge1);
						edgesused.add(edge2);
					}else if(list.size()==3) {
						Vertex temp=new Person(vertexs.get(i).getLabel()+" "+vertexs.get(j).getLabel());
						Vertex temp1=new Person(vertexs.get(i).getLabel()+" "+vertexs.get(j).getLabel()+"1");
						Edge edge1=null;
						Edge edge2=null;
						if(list.get(0) instanceof FriendTie) {
							edge1=new FriendTie(list.get(0)+"1", 0.5*list.get(0).getWeight());
							edge2=new FriendTie(list.get(0)+"2", 0.5*list.get(0).getWeight());
						}else if(list.get(0) instanceof ForwardTie) {
							edge1=new ForwardTie(list.get(0)+"1", 0.5*list.get(0).getWeight());
							edge2=new ForwardTie(list.get(0)+"2", 0.5*list.get(0).getWeight());
						}else if(list.get(0) instanceof CommentTie) {
							edge1=new CommentTie(list.get(0)+"1", 0.5*list.get(0).getWeight());
							edge2=new CommentTie(list.get(0)+"2", 0.5*list.get(0).getWeight());
						}
						ArrayList<Vertex> list1=new ArrayList<>();
						list1.add(list.get(0).getList().get(0));
						list1.add(temp);
						edge1.addVertices(list1);
						
						ArrayList<Vertex> list2=new ArrayList<>();
						list2.add(temp);
						list2.add(list.get(0).getList().get(1));
						edge2.addVertices(list2);
						vertexsused.add(temp);
						edgesused.remove(list.get(0));
						edgesused.add(edge1);
						edgesused.add(edge2);
						
						Edge edge3=null;
						Edge edge4=null;
						if(list.get(1) instanceof FriendTie) {
							edge3=new FriendTie(list.get(1)+"1", 0.5*list.get(1).getWeight());
							edge4=new FriendTie(list.get(1)+"2", 0.5*list.get(1).getWeight());
						}else if(list.get(0) instanceof ForwardTie) {
							edge3=new ForwardTie(list.get(1)+"1", 0.5*list.get(1).getWeight());
							edge4=new ForwardTie(list.get(1)+"2", 0.5*list.get(1).getWeight());
						}else if(list.get(0) instanceof CommentTie) {
							edge3=new CommentTie(list.get(1)+"1", 0.5*list.get(1).getWeight());
							edge4=new CommentTie(list.get(1)+"2", 0.5*list.get(1).getWeight());
						}
						ArrayList<Vertex> list3=new ArrayList<>();
						list3.add(list.get(1).getList().get(0));
						list3.add(temp);
						edge3.addVertices(list3);
						
						ArrayList<Vertex> list4=new ArrayList<>();
						list4.add(temp);
						list4.add(list.get(1).getList().get(1));
						edge4.addVertices(list4);
						
						vertexsused.add(temp1);
						edgesused.remove(list.get(1));
						edgesused.add(edge3);
						edgesused.add(edge4);
					}
				}
			}
			ConcreteGraph graph=new ConcreteGraph(vertexsused,edgesused,g.getGraphName());
//			System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
//			System.out.println(g.toString());
//			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
//			System.out.println(graph.toString());
//			System.out.println("######################################");
			
//			map=new HashMap<>();//由编号得到名字
//			map1=new HashMap<>();//由名字得到编号
//			int size=g.vertices().size();
//			double[][] array=new double[size][size];
//			int cnt=0;
//			for(Vertex v:g.vertices()) {
//				map.put(cnt, v);
//				map1.put(v, cnt);
//				cnt++;
//			}
//			for(int i=0;i<array.length;i++) {
//				for(int j=0;j<array.length;j++) {
//					array[i][j]=INF;
//				}
//			}
//			for(Vertex vr1:g.vertices()) {
//				for(Vertex vr2:g.vertices()) {
//					for(Edge e:g.edges()) {
//						if(e.getList().get(0).getLabel().equals(vr1.getLabel())&&e.getList().get(1).getLabel().equals(vr2.getLabel())) {
//							if(array[map1.get(vr1)][map1.get(vr2)]==INF||array[map1.get(vr1)][map1.get(vr2)]>e.getWeight()) {
//								array[map1.get(vr1)][map1.get(vr2)]=e.getWeight();
//							}
//						}
//					}
//				}
//			}
//			
			
			map=new HashMap<>();//由编号得到名字
			map1=new HashMap<>();//由名字得到编号
			int size=graph.vertices().size();
			double[][] array=new double[size][size];
			int cnt=0;
			for(Vertex v:graph.vertices()) {
				map.put(cnt, v);
				map1.put(v, cnt);
				cnt++;
			}
			for(int i=0;i<array.length;i++) {
				for(int j=0;j<array.length;j++) {
					array[i][j]=INF;
				}
			}
			for(Vertex vr1:graph.vertices()) {
				for(Vertex vr2:graph.vertices()) {
					for(Edge e:graph.edges()) {
						if(e.getList().get(0).getLabel().equals(vr1.getLabel())&&e.getList().get(1).getLabel().equals(vr2.getLabel())) {
							if(array[map1.get(vr1)][map1.get(vr2)]==INF||array[map1.get(vr1)][map1.get(vr2)]>e.getWeight()) {
								array[map1.get(vr1)][map1.get(vr2)]=1;
							}
						}
					}
				}
			}
			ans=FloydInGraph.findAllPath(array, map1.get(v1), map1.get(v2));
			if(array[map1.get(v1)][map1.get(v2)]==INF&&FloydInGraph.getPath().get(0).size()==2) {
				return -1;
			}
		}else if(g instanceof MovieGraph) {
			map=new HashMap<>();//由编号得到名字
			map1=new HashMap<>();//由名字得到编号
			int size=g.vertices().size();
			double[][] array=new double[size][size];
			int cnt=0;
			for(Vertex v:g.vertices()) {
				map.put(cnt, v);
				map1.put(v, cnt);
				cnt++;
			}
			for(int i=0;i<array.length;i++) {
				for(int j=0;j<array.length;j++) {
					array[i][j]=INF;
				}
			}
			for(Edge e:g.edges()) {
				if(!(e instanceof HyperEdge)) {
					array[map1.get(e.getList().get(0))][map1.get(e.getList().get(1))]=1;
					array[map1.get(e.getList().get(1))][map1.get(e.getList().get(0))]=1;
				}
			}
			ans=FloydInGraph.findAllPath(array, map1.get(v1), map1.get(v2));
			if(array[map1.get(v1)][map1.get(v2)]==INF&&FloydInGraph.getPath().get(0).size()==2) {
				return -1;
			}
		}
		return ans;
	}
	public static double degreeCentrality(ConcreteGraph g,Vertex v) throws Exception {
		double ans=0;
		for(Edge e: g.edges()) {
			if(!(e instanceof HyperEdge)) {
			if(e.getList().get(0).getLabel().equals(v.getLabel())) {
				ans++;
			}else if(e.getList().get(1).getLabel().equals(v.getLabel())) {
				ans++;
			}
		}
		}
		return ans;
	}
	public static double degreeCentrality(ConcreteGraph g) throws Exception {
		double ans=0;
		int VertexNum=g.vertices().size();
		double bottom=VertexNum*VertexNum-3*VertexNum+2;
		double top=0;
		double p=-1;
		for(Vertex v:g.vertices()) {
			if(degreeCentrality(g,v)>p) {
				p=degreeCentrality(g,v);
			}
		}
		for(Vertex v:g.vertices()) {
			top=top+(p-degreeCentrality(g,v));
		}
		ans=top/bottom;
		return ans;
	}
	public static double closenessCentrality(ConcreteGraph g,Vertex v) throws Exception{
		double ans=0;
		double temp=0;
		for(Vertex vm:g.vertices()) {
			if(!vm.getLabel().equals(v.getLabel())) {
			temp=minPath(g, vm, v);
			if(temp==-1) {
				ans=ans+0;
			}else {
				ans=ans+1/temp;
			}
			}
		}
		return ans/(g.vertices().size()-1);
	}
	public static double betweennessCentrality(ConcreteGraph g,Vertex v) throws Exception {
		double ans=0;
		for(Vertex v1:g.vertices()) {
			for(Vertex v2:g.vertices()) {
				double top=0;
				double bottom=0;
				if(!v1.getLabel().equals(v2.getLabel())&&!v1.getLabel().equals(v.getLabel())&&!v.getLabel().equals(v2.getLabel())) {
					double temp=minPath(g, v1, v2);
					if(temp!=-1) {
						top=0;
						bottom=FloydInGraph.getPath().size();
						for(int i=0;i<FloydInGraph.getPath().size();i++) {
							if(FloydInGraph.getPath().get(i).contains(map1.get(v))) {
								top++;
							}
						}
					}
				}
				if(bottom!=0)ans=top/bottom;
			}
		}
		if(g instanceof GraphPoet||g instanceof SocialNetwork) {
			return ans/((g.vertices().size()-1)*(g.vertices().size()-2));
		}
		else return ans/(((g.vertices().size()-1)*(g.vertices().size()-2))/2);
	}
	public static double inDegreeCentrality(ConcreteGraph g,Vertex v) throws Exception {
		int ans=0;
		
		for(Edge e:g.edges()) {
			if(!(e instanceof HyperEdge)) {
			if(e.getList().get(0).getLabel().equals(v.getLabel())) {
				ans++;
			}
			}
		}
		
		return ans;
	}
	public static double outDegreeCentrality(ConcreteGraph g,Vertex v) throws Exception {
		int ans=0;
		for(Edge e:g.edges()) {
			if(!(e instanceof HyperEdge)) {
			if(e.getList().get(1).getLabel().equals(v.getLabel())) {
				ans++;
				}
			}
		}
		return ans;
	}
	
	public static double distance(ConcreteGraph g,Vertex start,Vertex end) throws Exception {
		double ans=minPath(g, start, end);
		return ans;
	}
	public static double eccentricity(ConcreteGraph g,Vertex v) throws Exception {
		double ans=0;
		for(Vertex ver:g.vertices()) {
			if(!ver.getLabel().equals(v.getLabel())) {
				if(minPath(g, ver, v)>ans) {
					ans=minPath(g, ver, v);
				}
				if(minPath(g, v, ver)>ans) {
					ans=minPath(g, v, ver);
				}
			}
		}
		return ans;
		
	}
	public static double radius(ConcreteGraph g) throws Exception {
		double ans=Double.MAX_VALUE;
		for(Vertex v:g.vertices()) {
			if(ans>eccentricity(g, v)) {
				ans=eccentricity(g, v);
			}
		}
		return ans;
	}
	public static double diameter(ConcreteGraph g) throws Exception {
		double ans=Double.MIN_VALUE;
		for(Vertex v:g.vertices()) {
			if(ans<eccentricity(g, v)) {
				ans=eccentricity(g, v);
			}
		}
		return ans;
	}
}
